package com.binggun.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CallbackUtil<T, B> implements Callback<T, B> {
    private static final Map<String, CompletableFuture<?>> map = new HashMap<>();

    @Override
    public CompletableFuture<T> callback(String b,Consumer<T> callback) {
        CompletableFuture<T> future = new CompletableFuture<>();
        synchronized (map) {
            map.put(b, future);
        }
        future.thenAccept(callback);
        return future;
    }

    @Override
    public CompletableFuture<T> callback(String b) {
        CompletableFuture<T> future = new CompletableFuture<>();
        synchronized (map) {
            map.put(b, future);
        }
        return future;
    }

    public static <T> void notifyCallback(String uuid, T t) {
        synchronized (map) {
            CompletableFuture<T> future = (CompletableFuture<T>) map.get(uuid);
            if (future != null) {
                future.complete(t);
                map.remove(uuid);
            }
        }
    }
}