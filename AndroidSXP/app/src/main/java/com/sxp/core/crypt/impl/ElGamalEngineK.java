package com.sxp.core.crypt.impl;

import java.math.BigInteger;

public class ElGamalEngineK extends ElGamalEngine{
    protected BigInteger k;

    public BigInteger getLastK() {
        return k;
    }

}