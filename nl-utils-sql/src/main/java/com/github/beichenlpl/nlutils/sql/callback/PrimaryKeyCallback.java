package com.github.beichenlpl.nlutils.sql.callback;

import java.io.Serializable;

/**
 * @author lpl
 * @version 1.0
 * @since 2023.12.25
 */
public class PrimaryKeyCallback {

    private final Class<? extends Serializable> clazz;

    private Serializable primaryKey;

    public PrimaryKeyCallback(Class<? extends Serializable> clazz) {
        this.clazz = clazz;
    }

    public Serializable getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Serializable primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Class<? extends Serializable> getClazz() {
        return clazz;
    }
}
