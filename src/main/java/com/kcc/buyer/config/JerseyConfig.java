package com.kcc.buyer.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/kcc")
public class JerseyConfig extends ResourceConfig {
    /**
     * scanning com.kcc.buyer.controller package，Make it recognize jax-rs annotations
     */
    public JerseyConfig() {
        packages("com.kcc.buyer.controller");
        register(MultiPartFeature.class);
    }
}