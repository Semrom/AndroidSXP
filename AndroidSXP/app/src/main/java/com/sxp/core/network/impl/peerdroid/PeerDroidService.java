package com.sxp.core.network.impl.peerdroid;

import com.sxp.core.network.api.Messages;
import com.sxp.core.network.api.Peer;
import com.sxp.core.network.api.SearchListener;
import com.sxp.core.network.api.ServiceListener;
import com.sxp.core.network.api.advertisement.Advertisement;
import com.sxp.core.network.api.service.Service;
import com.sxp.core.network.impl.MessagesGeneric;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

import net.jxta.discovery.*;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.Message.ElementIterator;
import net.jxta.endpoint.MessageElement;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;

public class PeerDroidService  implements Service, DiscoveryListener, PipeMsgListener {
    protected PeerGroup pg = null;
    private SearchListener<Advertisement> currentSl;

    protected String peerUri = null;
    protected HashMap<String, ServiceListener> listeners = new HashMap<>();
    protected String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initAndStart(Peer peer) {
        if(!(peer instanceof PeerDroidPeer)) {
            throw new RuntimeException("Need a PeerDroid Peer to run a Jxta service");
        }
        PeerDroidPeer jxtaPeer = (PeerDroidPeer) peer;
        jxtaPeer.addService(this);
        peerUri = peer.getUri();
        createInputPipe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishAdvertisement(Advertisement adv) {
        PeerDroidAdvertisement androidAdv = new PeerDroidAdvertisement((Advertisement) adv);
        try {
            pg.getDiscoveryService().publish(androidAdv.getAdvertisementBridge());
            pg.getDiscoveryService().remotePublish(androidAdv.getAdvertisementBridge());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createInputPipe() {
        try {
            pg.getPipeService().createInputPipe(getAdvertisement(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setPeerGroup(PeerGroup pg) {
        this.pg = pg;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void search(String attribute, String value, SearchListener<?> sl) {
        this.currentSl = (SearchListener<Advertisement>) sl;
        pg.getDiscoveryService().getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, value, 10, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discoveryEvent(DiscoveryEvent event) {
        Enumeration<net.jxta.document.Advertisement> advs = event.getResponse().getAdvertisements();
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        while(advs.hasMoreElements()) {
            AdvertisementBridge adv = (AdvertisementBridge) advs.nextElement();
            Advertisement fadv = (Advertisement)adv.getAdvertisement();
            fadv.setSourceURI("urn:jxta:" + event.getSource().toString().substring(7));

            advertisements.add((Advertisement)adv.getAdvertisement());
        }
        currentSl.notify(advertisements);
    }

    /**
     * Create a simple advertisement for the pipes' class.
     * @return
     */
    protected PipeAdvertisement getAdvertisement() {
        return getPipeAdvertisement(IDFactory
                .newPipeID(pg.getPeerGroupID(), this.getClass().getName().getBytes()), false);
    }

    protected PipeAdvertisement getPipeAdvertisement(PipeID id, boolean is_multicast) {
        PipeAdvertisement adv = (PipeAdvertisement ) AdvertisementFactory.
                newAdvertisement(PipeAdvertisement.getAdvertisementType());
        adv.setPipeID(id);
        if (is_multicast)
            adv.setType(PipeService.PropagateType);
        else
            adv.setType(PipeService.UnicastType);
        adv.setName("Pipe");
        adv.setDescription("...");
        return adv;
    }

    protected Message toJxtaMessage(Messages m) {
        Message msg = new Message();
        for(String s: m.getNames()) {
            msg.addMessageElement(new ByteArrayMessageElement(s, null, m.getMessage(s).getBytes(), null));
        }
        //msg.addMessageElement(new ByteArrayMessageElement("WHO", null, m.getWho().getBytes(), null));
        return msg;
    }

    protected Messages toMessages(Message m) {
        MessagesGeneric msg = new MessagesGeneric();
        ElementIterator it = m.getMessageElements();
        while(it.hasNext()) {
            MessageElement e = (MessageElement)it.next();
            if(e.getElementName().equals("WHO")) {
                msg.setWho(new String(e.getBytes(true)));
            } else {
                msg.addField(e.getElementName(), new String(e.getBytes(true)));
            }
        }
        return msg;
    }

    @Override
    public void sendMessages(Messages messages, String... uris) {
        Message message = toJxtaMessage(messages);
        HashSet<PeerID> to = new HashSet<PeerID>();
        for(String pidUri: uris) {
            try {
                PeerID pid = (PeerID)PeerID.create(new URI(pidUri));
                to.add(pid);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputPipe pipe = pg.getPipeService().createOutputPipe(getAdvertisement(), to, 3000);
            pipe.send(message);
            pipe.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void pipeMsgEvent(PipeMsgEvent event) {
        Messages m = toMessages(event.getMessage());
        if(listeners.get(m.getWho()) != null) {
            listeners.get(m.getWho()).notify(m);
        }
    }

    @Override
    public void addListener(ServiceListener l, String who) {
        listeners.put(who, l);
    }

    @Override
    public void removeListener(String who) {
        listeners.remove(who);
    }
}