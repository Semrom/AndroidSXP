package com.sxp.core.network.factories;

import com.sxp.core.network.api.ItemRequestService;
import com.sxp.core.network.impl.peerdroid.PeerDroidItemsSenderService;

public class ServiceFactory {
    public ItemRequestService createItemRequestService() {
        return new PeerDroidItemsSenderService();
    }
}