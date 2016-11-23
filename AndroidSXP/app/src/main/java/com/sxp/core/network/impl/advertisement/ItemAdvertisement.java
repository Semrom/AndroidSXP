package com.sxp.core.network.impl.advertisement;

import com.sxp.core.network.api.advertisement.ItemAdvertisementInterface;
import com.sxp.core.network.api.annotation.AdvertisementAttribute;
import com.sxp.core.network.api.annotation.ServiceName;
import com.sxp.core.network.impl.AbstractAdvertisement;

/**
 * Advertisement for a peer that host an item
 * @author Julien Prudhomme
 *
 * @param <Sign>
 */
@ServiceName(name = "items")
public class ItemAdvertisement extends AbstractAdvertisement implements ItemAdvertisementInterface{

    @AdvertisementAttribute(indexed = true)
    private String title;

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public String getAdvertisementType() {
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
