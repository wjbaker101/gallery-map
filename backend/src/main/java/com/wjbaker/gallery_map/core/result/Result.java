package com.wjbaker.gallery_map.core.result;

public final class Result extends AbstractResult {

    private Result(boolean isFailure, String failureMessage) {
        super(isFailure, failureMessage);
    }

    public static Result success() {
        return new Result(false, null);
    }

    public static Result failure(final String failureMessage) {
        return new Result(true, failureMessage);
    }

    public static Result fromFailure(final ResultOf<?> result) {
        return new Result(true, result.getFailureMessage());
    }

    public static Result fromFailure(final Result result) {
        return new Result(true, result.getFailureMessage());
    }
}