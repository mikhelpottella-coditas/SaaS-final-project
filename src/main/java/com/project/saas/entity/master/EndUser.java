package com.project.saas.entity.master;

import org.jspecify.annotations.Nullable;

public interface EndUser {
    @Nullable String getPassword();

    String getEmail();
}
