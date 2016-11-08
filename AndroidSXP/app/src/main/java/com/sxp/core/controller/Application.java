package com.sxp.core.controller;

import com.sxp.core.model.syncManager.UserSyncManagerImpl;
import com.sxp.core.network.api.Peer;
import com.sxp.core.network.factories.PeerFactory;
import com.sxp.core.rest.api.Authentifier;
import com.sxp.core.rest.factories.AuthentifierFactory;
import com.sxp.core.rest.factories.RestServerFactory;

import java.util.Properties;

/**
 * Main class
 * {@link Application} is a singleton
 * @author Julien Prudhomme
 *
 */

public class Application {
    private static Application instance = null;
    private Peer peer;
    private Authentifier auth;

    public Application(){
        if(instance != null){
            throw new RuntimeException("Application can be instantiate only once !");
        }
        instance = this;
    }

    public static Application getInstance(){
        return instance;
    }

    public void run(){
        setPeer(PeerFactory.createDefaultAndStartPeer());
        setAuth(AuthentifierFactory.createDefaultAuthentifier());
        RestServerFactory.createAndStartDefaultRestServer(8080);
    }

    public void runForTest(int restPort){
        Properties p = System.getProperties();
        p.put("derby.system.home", "./.db-" + restPort + "/");
        new UserSyncManagerImpl(); //just init the db
        setPeer(PeerFactory.createDefaultAndStartPeerForTest());
        setAuth(AuthentifierFactory.createDefaultAuthentifier());
        RestServerFactory.createAndStartDefaultRestServer(restPort);
    }

    public Peer getPeer(){
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public Authentifier getAuth() {
        return auth;
    }

    public void setAuth(Authentifier auth) {
        this.auth = auth;
    }
}
