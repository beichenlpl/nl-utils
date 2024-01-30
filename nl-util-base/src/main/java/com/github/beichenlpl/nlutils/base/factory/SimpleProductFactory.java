package com.github.beichenlpl.nlutils.base.factory;

import com.github.beichenlpl.nlutils.base.common.BaseUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author beichenlpl
 * @since 2023/09/06
 */
public class SimpleProductFactory<T> {

    private final Map<String, Class<? extends T>> PRODUCT_MAP = new HashMap<>();

    private final Map<String, String> P_NAME_MAP = new HashMap<>();

    private final Map<String, T> PRODUCT_CACHE = new ConcurrentHashMap<>();

    public SimpleProductFactory() {
    }

    @SafeVarargs
    public SimpleProductFactory(Class<? extends T>... classes) {
        appendAll(classes);
    }

    public SimpleProductFactory<T> append(Class<? extends T> clazz) {
        Product annotation = clazz.getAnnotation(Product.class);
        String key = BaseUtils.isEmpty(annotation.value()) ? clazz.getSimpleName() : annotation.value();
        String name = BaseUtils.isEmpty(annotation.name()) ? clazz.getSimpleName() : annotation.name();
        PRODUCT_MAP.put(key, clazz);
        P_NAME_MAP.put(key, name);
        return this;
    }

    @SafeVarargs
    public final void appendAll(Class<? extends T>... classes) {
        for (Class<? extends T> aClass : classes) {
            append(aClass);
        }
    }

    public T produce(String name, Object... params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (BaseUtils.isNull(name)) {
            throw new IllegalArgumentException("product not found!");
        }

        return PRODUCT_CACHE.containsKey(name) ? PRODUCT_CACHE.get(name) : build(name, params);
    }

    public String getName(String key) {
        return P_NAME_MAP.get(key);
    }

    private T build(String name, Object... params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends T> aClass = PRODUCT_MAP.get(name);
        Constructor<? extends T> constructor = aClass.getConstructor();
        T product = constructor.newInstance(params);
        PRODUCT_CACHE.put(name, product);
        return product;
    }
}
