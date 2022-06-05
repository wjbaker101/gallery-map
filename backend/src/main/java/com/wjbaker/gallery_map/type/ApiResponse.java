package com.wjbaker.gallery_map.type;

import com.wjbaker.gallery_map.core.result.Result;
import com.wjbaker.gallery_map.core.result.ResultOf;
import lombok.Getter;

import java.time.Instant;

public final class ApiResponse<TResult> {

    @Getter
    private final TResult result;

    @Getter
    private final String failureMessage;

    @Getter
    private final Instant responseAt;

    private ApiResponse(final TResult result, final String failureMessage) {
        this.result = result;
        this.failureMessage = failureMessage;
        this.responseAt = Instant.now();
    }

    public static ApiResponse<Void> FromResult(final Result result) {
        if (result.isSuccess()) {
            return new ApiResponse<>(null, null);
        }

        return new ApiResponse<>(null, result.getFailureMessage());
    }

    public static <T> ApiResponse<T> FromResult(final ResultOf<T> result) {
        if (result.isSuccess()) {
            return new ApiResponse<>(result.getContent(), null);
        }

        return new ApiResponse<>(null, result.getFailureMessage());
    }
}