package com.sxp.core.correct.javax.persistence;

import android.content.Entity;

/**
 * Created by thomas on 09/11/2016.
 */

public class EntityManager {
    public Query createQuery(String str) {
        return new Query();
    }

    public Object find(Class<?> theClass, String id) {
        return new Object();
    }

    public Object getTransaction() {
        return new Object();
    }
}
