package com.sxp.core.network.impl.peerdroid;

import com.sxp.core.network.api.ItemService;

public class PeerDroidItemService extends PeerDroidService implements ItemService{
    public static final String NAME = "items";
    public PeerDroidItemService() {
        this.name = NAME;
    }
}
