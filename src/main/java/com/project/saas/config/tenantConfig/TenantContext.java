package com.project.saas.config.tenantConfig;

/**
 * this is to set the local tread that hold the data about the tenant which will refresh
 * at every new request
 */

public class TenantContext {
    private TenantContext() {
        /* This utility class should not be instantiated */
    }

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setTenant(String tenant) {
        THREAD_LOCAL.set(tenant);
    }

    public static String getTenant() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}