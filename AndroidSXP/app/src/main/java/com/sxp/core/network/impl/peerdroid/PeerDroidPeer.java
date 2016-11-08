package com.sxp.core.network.impl.peerdroid;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.sxp.core.network.api.Peer;
import com.sxp.core.network.api.service.Service;
import com.sxp.core.network.utils.IpChecker;

public class PeerDroidPeer implements Peer{
    private PeerDroidNode node;
    private HashMap<String, Service> services;

    /**
     * Create a peer compatible with android systems
     */
    public PeerDroidPeer(){
        node = new PeerDroidNode();
        services = new HashMap<>();
    }

    @Override
    public void start(String cache, int port, String ...ips) throws IOException{
        node.initialize(cache, "sxp peer", true);
        node.start(port);
    }

    @Override
    public void stop(){ node.stop(); }

    @Override
    public String getIp(){
        try{
            return IpChecker.getIp();
        }catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Service> getServices() {
        return services.values();
    }

    @Override
    public Service getService(String name) {
        return services.get(name);
    }

    @Override
    public void addService(Service service) {
        PeerDroidService s = (PeerDroidService) service;
        services.put(service.getName(), service);
        s.setPeerGroup(node.createGroup(service.getName()));
    }

    @Override
    public String getUri() {
        return node.getPeerId();
    }

    @Override
    public void bootstrap(String... ips) {

    }
}