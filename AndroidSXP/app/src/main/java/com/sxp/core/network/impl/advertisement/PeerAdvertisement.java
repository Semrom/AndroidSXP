package com.sxp.core.network.impl.advertisement;

import com.sxp.core.network.api.advertisement.PeerAdvertisementInterface;
import com.sxp.core.network.api.annotation.AdvertisementAttribute;
import com.sxp.core.network.impl.AbstractAdvertisement;

public class PeerAdvertisement extends AbstractAdvertisement implements PeerAdvertisementInterface{

    @AdvertisementAttribute
    private String publicKey;

    @Override
    public String getName() {
        return "peer";
    }

    @Override
    public String getAdvertisementType() {
        // TODO Auto-generated method stub
        return null;
    }
}