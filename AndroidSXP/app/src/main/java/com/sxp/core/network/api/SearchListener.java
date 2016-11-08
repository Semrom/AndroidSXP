package com.sxp.core.network.api;

import java.util.Collection;

import com.sxp.core.network.api.advertisement.Advertisement;

/**
 * Search interface for advertisement
 * @author Julien Prudhomme
 *
 * @param <T> Type of advertisement searched
 */
public interface SearchListener <T extends Advertisement>{
    /**
     * Call to notify one or more object are found
     * @param result the advertisement found.
     */
    public void notify(Collection<T> result);
}

