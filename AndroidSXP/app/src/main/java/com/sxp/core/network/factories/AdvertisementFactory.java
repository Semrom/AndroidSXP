package com.sxp.core.network.factories;

import com.sxp.core.network.api.advertisement.ItemAdvertisementInterface;
import com.sxp.core.network.api.advertisement.PeerAdvertisementInterface;
import com.sxp.core.network.api.advertisement.UserAdvertisementInterface;
import com.sxp.core.network.impl.advertisement.ItemAdvertisement;
import com.sxp.core.network.impl.advertisement.PeerAdvertisement;
import com.sxp.core.network.impl.advertisement.UserAdvertisement;

public class AdvertisementFactory {
    public static ItemAdvertisementInterface createItemAdvertisement() {
        return new ItemAdvertisement();
    }

    public static UserAdvertisementInterface createUserAdvertisement() {
        return new UserAdvertisement();
    }

    public static PeerAdvertisementInterface createPeerAdvertisement() {
        return new PeerAdvertisement();
    }
}