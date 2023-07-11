package com.nju.boot.filter;

import com.nju.boot.entity.dto.ResponseDto;
import com.nju.boot.utils.JwtTokenUtils;
import com.nju.boot.utils.ResponseUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //从Request Header 取出Token
        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        //System.out.println(token);

        //Token为空放行
        //如果接下来进入的URL不是公共的地址SpringSecurity会返回403的错误
        if (token == null || "null".equals(token) || "undefined".equals(token)) {
            chain.doFilter(request, response);
            return;
        }

        //判断JWT Token是否过期
        if (JwtTokenUtils.isExpiration(token)) {
            ResponseUtils.writeJson(response,
                    new ResponseDto<>(403, "令牌已过期, 请重新登录"));
            return;
        }

        //解析JWT获取用户信息
        String username = JwtTokenUtils.getUsername(token);

        //向SpringSecurity的Context中加入认证信息
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
        super.doFilterInternal(request, response, chain);
    }

}
