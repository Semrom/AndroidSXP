package com.sxp.core.network.factories;

import com.sxp.core.network.api.Peer;
import com.sxp.core.network.api.service.Service;
import com.sxp.core.network.impl.peerdroid.PeerDroidItemService;
import com.sxp.core.network.impl.peerdroid.PeerDroidItemsSenderService;
import com.sxp.core.network.impl.peerdroid.PeerDroidPeer;

import java.io.IOException;
import java.util.Random;

/**
 * {@link Peer} factory
 * @author Julien Prudhomme
 *
 */
public class PeerFactory {
    /**
     * create the default implementation of {@link Peer}
     * @return a {@link Peer}
     */
    public static Peer createDefaultPeer(String target) {
        switch (target){
            case "android" :
                return createPeerDroidPeer();
            case "desktop":
            case "jxta":
            default:
                return  null; //createJxtaPeer();

        }
    }

    /**
     * Create a the default implementation of {@link Peer} and start it
     * @return an initialized and started {@link Peer}
     */
    public static Peer createDefaultAndStartPeer() {
        //to do set switch case to allow m
        Peer p = createAndStartPeer("android", ".peercache", 9578);
        Service itemService = new PeerDroidItemService();
        itemService.initAndStart(p);
        return p;
    }


    /**
     * Create, initialize, and start a {@link PeerDroidPeer}
     * @return an initialized and started {@link Peer}
     */
    public static Peer createAndStartPeer(String impl, String tmpFolder, int port) {
        Peer peer;
        switch(impl) {
            case "android": peer = createPeerDroidPeer(); break;
            default: throw new RuntimeException(impl + "doesn't exist");
        }
        try {
            peer.start(tmpFolder, port, "tcp://109.15.222.135:9800");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peer;
    }

    public static Peer createDefaultAndStartPeerForTest() {
        Random r = new Random();
        String cache = ".peer" + r.nextInt(10000);
        //int port = 9800 + r.nextInt(100);
        int port = 9800;
        System.out.println("jxta will run on port " + port);
        Peer p = createAndStartPeer("jxta", cache, port);

        Service itemService = new PeerDroidItemService();
        itemService.initAndStart(p);

        Service itemsSender = new PeerDroidItemsSenderService();
        itemsSender.initAndStart(p);
        return p;
    }

    public static PeerDroidPeer createPeerDroidPeer(){
        return new PeerDroidPeer();
    }
}
