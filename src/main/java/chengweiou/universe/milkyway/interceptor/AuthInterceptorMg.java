package chengweiou.universe.milkyway.interceptor;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.model.entity.person.PersonType;
import com.google.gson.Gson;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInterceptorMg implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accountJson = request.getHeader("loginAccount");
        if (accountJson == null) return unauth(response);
        Account loginAccount = new Gson().fromJson(accountJson, Account.class);
        PersonType personType = PersonType.valueOf(loginAccount.getExtra());
        if (personType == PersonType.SUPER || personType == PersonType.EMPLOYEE) return true;
        boolean additionalAuth = checkAdditionalAuth(personType, handler);
        if (additionalAuth) return true;
        return unauth(response);
    }

    private boolean unauth(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(new Gson().toJson(Rest.fail(BasicRestCode.UNAUTH)));
        return false;
    }


    private boolean checkAdditionalAuth(PersonType personType, Object handler) {
        Auth auth = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
        if (auth == null) auth = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(Auth.class);
        return auth != null && personType == auth.value();
    }
}
