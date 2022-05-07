package com.fourfourfour.damoa.config.aop;

import com.fourfourfour.damoa.common.aop.trace.ThreadLocalLogTrace;
import com.fourfourfour.damoa.common.aop.trace.aspect.LogTraceAspect;
import com.fourfourfour.damoa.common.aop.trace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
