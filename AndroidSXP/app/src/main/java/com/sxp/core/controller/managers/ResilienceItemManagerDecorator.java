package com.sxp.core.controller.managers;

import com.sxp.core.model.api.Manager;
import com.sxp.core.model.api.ManagerDecorator;
import com.sxp.core.model.entity.Item;
import com.sxp.core.network.api.Peer;

public class ResilienceItemManagerDecorator extends ManagerDecorator<Item>{

    public ResilienceItemManagerDecorator(Manager<Item> em, Peer peer) {
        super(em);
    }
}