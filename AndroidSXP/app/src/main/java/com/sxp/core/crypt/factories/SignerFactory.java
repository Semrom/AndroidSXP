package com.sxp.core.crypt.factories;

import com.sxp.core.crypt.api.signatures.Signer;
import com.sxp.core.crypt.impl.signatures.ElGamalSigner;

/**
 * {@linkplain Signer} factory
 * @author Julien Prudhomme
 *
 */
public class SignerFactory {

    public static Signer<?,?> createDefaultSigner() {
        return createElGamalSigner();
    }

    public static ElGamalSigner createElGamalSigner() {
        return new ElGamalSigner();
    }
}
