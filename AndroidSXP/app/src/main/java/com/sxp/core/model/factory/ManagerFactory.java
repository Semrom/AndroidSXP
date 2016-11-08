package com.sxp.core.model.factory;

import com.sxp.core.controller.managers.NetworkItemManagerDecorator;
import com.sxp.core.controller.managers.ResilienceItemManagerDecorator;
import com.sxp.core.model.api.Manager;
import com.sxp.core.model.entity.Item;
import com.sxp.core.model.manager.ManagerAdapter;
import com.sxp.core.model.syncManager.ItemSyncManagerImpl;
import com.sxp.core.network.api.Peer;

public class ManagerFactory {
    public static Manager<Item> createNetworkResilianceItemManager(Peer peer, String who) {
        ManagerAdapter<Item> adapter = new ManagerAdapter<Item>(new ItemSyncManagerImpl());
        NetworkItemManagerDecorator networkD = new NetworkItemManagerDecorator(adapter, peer, who);
        ResilienceItemManagerDecorator resiNetworkD = new ResilienceItemManagerDecorator(networkD, peer);
        return resiNetworkD;
    }
}