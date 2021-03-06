package com.sxp.core.model.entity;

import java.io.Serializable;
import java.math.BigInteger;

import correct.javax.persistence.Entity;
import correct.javax.validation.constraints.NotNull;
import correct.javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.sxp.core.crypt.api.key.AsymKey;
import com.sxp.core.controller.tools.BigIntegerSerializer;

//@Entity
public class ElGamalKey implements AsymKey<BigInteger>, Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -6531626985325397645L;

    /* @NotNull
    @XmlElement(name="privateKey")
    @JsonSerialize(using=BigIntegerSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING) */
    private BigInteger privateKey;

    /*@NotNull
    @XmlElement(name="publicKey")
    @JsonSerialize(using=BigIntegerSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING) */
    private BigInteger publicKey;

    /*@NotNull
    @XmlElement(name="p")
    @JsonSerialize(using=BigIntegerSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING) */
    private BigInteger p;

    /*@NotNull
    @XmlElement(name="g")
    @JsonSerialize(using=BigIntegerSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING) */
    private BigInteger g;

    @Override
    public BigInteger getPublicKey() {
        return publicKey;
    }
    @Override
    public BigInteger getPrivateKey() {
        return privateKey;
    }
    @Override
    public BigInteger getParam(String param) {
        switch(param) {
            case "p": return p;
            case "g": return g;
            default: throw new RuntimeException("param " + param + "undefined");
        }
    }
    @Override
    public void setPublicKey(BigInteger pbk) {
        publicKey = pbk;
    }
    @Override
    public void setPrivateKey(BigInteger pk) {
        privateKey = pk;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

}
