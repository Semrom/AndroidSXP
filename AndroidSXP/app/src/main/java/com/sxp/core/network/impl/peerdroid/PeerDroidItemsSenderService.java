package com.sxp.core.network.impl.peerdroid;

/**
 * Created
 */
import java.util.Collection;

import com.fasterxml.jackson.core.type.TypeReference;

import net.jxta.pipe.PipeMsgEvent;

import com.sxp.core.controller.tools.JsonTools;
import com.sxp.core.model.entity.Item;
import com.sxp.core.model.syncManager.ItemSyncManagerImpl;
import com.sxp.core.network.api.ItemRequestService;
import com.sxp.core.network.api.Messages;
import com.sxp.core.network.impl.MessagesGeneric;
import com.sxp.core.network.impl.messages.RequestItemMessage;


public class PeerDroidItemsSenderService extends PeerDroidService implements ItemRequestService{
    public static final String NAME = "itemsSender";

    public PeerDroidItemsSenderService() {
        this.name = NAME;
    }

    private Messages getResponseMessage(Messages msg) {
        MessagesGeneric m = new MessagesGeneric();

        m.addField("type", "response");
        m.setWho(msg.getWho());
        ItemSyncManagerImpl im = new ItemSyncManagerImpl();
        Collection<Item> items = im.findAllByAttribute("title", msg.getMessage("title"));
        JsonTools<Collection<Item>> json = new JsonTools<>(new TypeReference<Collection<Item>>(){});
        m.addField("items", json.toJson(items));

        return m;
    }

    @Override
    public void sendRequest(String title, String who, String ...peerURIs) {
        RequestItemMessage m = new RequestItemMessage();
        m.setTitle(title);
        m.setWho(who);
        m.setSource(this.peerUri);
        this.sendMessages(m, peerURIs);
    }

    @Override
    public void pipeMsgEvent(PipeMsgEvent event) {
        Messages message = toMessages(event.getMessage());
        if(message.getMessage("type").equals("response")) {
            super.pipeMsgEvent(event);
            return;
        }

        this.sendMessages(getResponseMessage(message), message.getMessage("source"));

    }
}
