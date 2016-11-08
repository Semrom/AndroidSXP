package com.sxp.core.network.impl.peerdroid;

/**
 * Created by
 */
import com.sxp.core.network.api.ItemService;

public class PeerDroidItemService extends PeerDroidService implements ItemService{
    public static final String NAME = "items";
    public PeerDroidItemService() {
        this.name = NAME;
    }
}
