package com.sxp.core.network.impl.peerdroid;

import com.sxp.core.network.api.Node;
import com.sxp.core.network.utils.IpChecker;
import net.jxta.id.IDFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.platform.NetworkManager;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.peergroup.PeerGroup;

import java.io.File;
import java.io.IOException;

public class PeerDroidNode implements Node {
    private boolean initialized = false;
    private NetworkManager networkManager = null;
    private PeerGroup defaultPeerGroup = null;

    @Override
    public void initialize(String cacheFolder, String name, boolean persistent) throws IOException {
        File configFile = new File("." + System.getProperty("file.separator") + cacheFolder);
        networkManager = initializeNetworkManager(configFile, name, persistent);
        initialized = true;
    }

    @Override
    public boolean isInitialized(){
        return initialized;
    }

    @Override
    public void start(int port) throws RuntimeException{
        if(!initialized) {
            throw new RuntimeException("Node must be initalized before start call");
        }

        try {
            NetworkConfigurator configurator = networkManager.getConfigurator();
            configurator.setTcpPort(port);
            configurator.setHttpPort(port + 1);
            PeerGroup pg = networkManager.startNetwork();
            pg.startApp(new String[0]);
            //Switch to rendez vous mode if possible, check every 60 secs
            pg.getRendezVousService().setAutoStart(true,60*1000);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error on config file");
            System.exit(-1);
        } catch (PeerGroupException e) {
            e.printStackTrace();
            System.err.println("error while creating main peer group");
            System.exit(-1);
            //can't continue
        }

        createDefaultGroup();
    }

    @Override
    public boolean isStarted() {
        return isInitialized(); // && networkManager.isStarted();
    }

    @Override
    public void stop(){
        if(networkManager == null) {
            throw new RuntimeException("Serveur was not started !");
        }
        networkManager.stopNetwork();
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    private void createDefaultGroup() {
        try {
            PeerGroup netpeerGroup = networkManager.getNetPeerGroup();

            ModuleImplAdvertisement madv = null;
            try {
                madv = netpeerGroup.getAllPurposePeerGroupImplAdvertisement();
            } catch(Exception e) {
                e.printStackTrace();
            }

            defaultPeerGroup = netpeerGroup.newGroup(
                    this.generatePeerGroupID(netpeerGroup.getPeerGroupID(), "SXP group"),
                    madv, "SXP group", "SXP group"); // todo check last params functionality to enhance , true);
            defaultPeerGroup.startApp(new String[0]);
            defaultPeerGroup.getRendezVousService().setAutoStart(true, 60*1000);
        } catch (PeerGroupException e) {
            System.err.println("impossible to create default group");
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Group created !");
    }

    protected PeerGroup createGroup(final String name) {
        ModuleImplAdvertisement mAdv = null;
        PeerGroup temp = null;
        System.out.println("creating new group ..");
        try {
            mAdv = defaultPeerGroup.getAllPurposePeerGroupImplAdvertisement();
            temp = defaultPeerGroup.newGroup(generatePeerGroupID(defaultPeerGroup.getPeerGroupID(), name), mAdv, name, name); // todo check last params functionality to enhance, true); /* creating & publishing the group */
            getDefaultPeerGroup().getDiscoveryService().remotePublish(temp.getPeerGroupAdvertisement());
            temp.startApp(new String[0]);
            temp.getRendezVousService().setAutoStart(true, 60);
        } catch (Exception e) {
            e.printStackTrace();
        } /* Getting the advertisement of implemented modules */
        return temp;
    }

    protected PeerGroup getDefaultPeerGroup() {
        return this.defaultPeerGroup;
    }

    private PeerGroupID generatePeerGroupID(PeerGroupID parent, String peerGroupName) {
        return IDFactory.newPeerGroupID(PeerGroupID.defaultNetPeerGroupID, peerGroupName.getBytes());
    }

    public String getPeerId() {
        return this.defaultPeerGroup.getPeerID().toURI().toString();
    }

    /**
     * Initialize the network manager
     * @param configFile
     * @param peerName
     * @param persistant
     * @return
     * @throws IOException
     */
    private NetworkManager initializeNetworkManager(File configFile, String peerName, boolean persistant) throws IOException {
        NetworkManager manager = null;
        NetworkConfigurator configurator = null;
        manager = new NetworkManager(NetworkManager.EDGE, peerName, configFile.toURI()); /* Setting network */
        configurator = manager.getConfigurator(); /* Getting configurator for future tweaks */
        configurator.setTcpEnabled(true);
        configurator.setHttpEnabled(true);
        configurator.setTcpIncoming(true);
        configurator.setHttpIncoming(true);
        configurator.setHttpOutgoing(true);
        configurator.setTcpOutgoing(true);
        configurator.setUseMulticast(true);
        configurator.setTcpInterfaceAddress("0.0.0.0");
        configurator.setUseMulticast(true);
        try {
            configurator.setTcpPublicAddress(IpChecker.getIp(), false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        configurator.setHttpInterfaceAddress("0.0.0.0");
        try {
            configurator.setHttpPublicAddress(IpChecker.getIp(), false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        configurator.setTcpEndPort(-1);
        configurator.setTcpStartPort(-1);
        configurator.setName("SXPeerGroup");
        configurator.setDescription("SXP default peer group");
        configurator.setPrincipal("SXP peer group");
        manager.setConfigPersistent(persistant);
        return manager;
    }
}