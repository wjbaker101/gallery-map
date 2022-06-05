package com.wjbaker.gallery_map.client._shared;

import com.wjbaker.gallery_map.core.result.ResultOf;

public final class ClientHelper {

    public interface IDoRequestFunction<T> {
        T execute() throws Exception;
    }

    private ClientHelper() {}

    public static <T> ResultOf<T> DoRequest(IDoRequestFunction<T> function) {
        try {
            return ResultOf.of(function.execute());
        }
        catch (final Exception e) {
            return ResultOf.failure("Failed client request. Message: " + e.getMessage());
        }
    }
}