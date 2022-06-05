package com.wjbaker.gallery_map.api.auth;

import com.wjbaker.gallery_map.database.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class RequiresAdminHandlerInterceptor implements HandlerInterceptor {

    private final LoginTokenService loginTokenService;
    private final IUserRepository userRepository;

    public RequiresAdminHandlerInterceptor(
        @Autowired final LoginTokenService loginTokenService,
        @Autowired final IUserRepository userRepository) {

        this.loginTokenService = loginTokenService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler) {

        if (!(handler instanceof HandlerMethod handlerMethod))
            return true;

        var filter = handlerMethod.getMethod().getAnnotation(RequiresAdmin.class);
        if (filter == null)
            return true;

        var loginToken = request.getHeader("Authorization");
        if (loginToken == null)
            return failure(response);

        var checkResult = this.loginTokenService.check(loginToken.replace("Bearer ", ""));
        if (checkResult.isFailure())
            return failure(response);

        var userOptional = this.userRepository.findByUsername(checkResult.getContent());
        if (userOptional.isEmpty())
            return failure(response);

        var user = userOptional.get();
        if (!user.isAdmin())
            return failure(response);

        request.setAttribute("user", user);

        return true;
    }

    private static boolean failure(final HttpServletResponse response) {
        response.setStatus(401);
        return false;
    }
}