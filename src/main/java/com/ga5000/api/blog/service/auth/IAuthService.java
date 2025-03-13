package com.ga5000.api.blog.service.auth;


import com.ga5000.api.blog.domain.user.User;

public interface IAuthService {
    void registerUserIfNonExistent();
    String getToken();
    User getCurrentUser();
}
