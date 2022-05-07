package com.fourfourfour.damoa.common.aop.trace;

public interface LogTrace {

    TraceStatus begin(String message, String parameterInfo);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
