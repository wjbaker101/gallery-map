package com.wjbaker.gallery_map.core.result;

import lombok.Getter;

public sealed class AbstractResult permits Result, ResultOf {

    @Getter
    protected final boolean isFailure;

    @Getter
    protected final String failureMessage;

    protected AbstractResult(final boolean isFailure, final String failureMessage) {
        this.isFailure = isFailure;
        this.failureMessage = failureMessage;
    }

    public boolean isSuccess() {
        return !this.isFailure();
    }
}