package com.binggun.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public interface Callback<T, B> {
    CompletableFuture<T> callback(String b,Consumer<T> callback);
    CompletableFuture<T> callback(String b);
}


