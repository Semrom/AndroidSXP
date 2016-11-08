package com.sxp.core.model.syncManager;

import com.sxp.core.model.api.ItemSyncManager;
import com.sxp.core.model.entity.Item;

public class ItemSyncManagerImpl extends AbstractSyncManager<Item> implements ItemSyncManager {

    public ItemSyncManagerImpl() {
        super();
        this.initialisation("persistence", Item.class);
    }
}