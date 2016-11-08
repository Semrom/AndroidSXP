package com.sxp.core.network.impl.advertisement;

import com.sxp.core.network.api.advertisement.UserAdvertisementInterface;
import com.sxp.core.network.api.annotation.AdvertisementAttribute;
import com.sxp.core.network.impl.AbstractAdvertisement;

public class UserAdvertisement extends AbstractAdvertisement implements UserAdvertisementInterface{

    @AdvertisementAttribute(indexed = true)
    private String nickName;

    @AdvertisementAttribute(indexed = true)
    private String publicKey;

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public String getAdvertisementType() {
        // TODO Auto-generated method stub
        return null;
    }
}