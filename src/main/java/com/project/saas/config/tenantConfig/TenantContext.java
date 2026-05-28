package com.project.saas.config.tenantConfig;

public class TenantContext {
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