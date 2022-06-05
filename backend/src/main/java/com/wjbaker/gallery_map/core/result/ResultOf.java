package com.wjbaker.gallery_map.core.result;

public final class ResultOf<TResult> extends AbstractResult {

    private final TResult content;

    private ResultOf(final boolean isFailure, final String failureMessage, final TResult result) {
        super(isFailure, failureMessage);
        this.content = result;
    }

    public static <T> ResultOf<T> failure(final String failureMessage) {
        return new ResultOf<>(true, failureMessage, null);
    }

    public static <T> ResultOf<T> fromFailure(final ResultOf<?> result) {
        return new ResultOf<>(true, result.getFailureMessage(), null);
    }

    public static <T> ResultOf<T> fromFailure(final Result result) {
        return new ResultOf<>(true, result.getFailureMessage(), null);
    }

    public static <T> ResultOf<T> of(final T result) {
        return new ResultOf<>(false, null, result);
    }

    public TResult getContent() {
        if (this.isFailure())
            throw new RuntimeException("Cannot get content of failed result. Failure message: " + this.getFailureMessage());

        return this.content;
    }

    public TResult getContentOrDefault(final TResult defaultContent) {
        if (this.isFailure())
            return defaultContent;

        return this.content;
    }
}