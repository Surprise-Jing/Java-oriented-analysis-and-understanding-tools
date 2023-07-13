package com.nju.boot.config;

import com.nju.boot.entity.dto.ResponseDto;
import com.nju.boot.filter.AuthorizationFilter;
import com.nju.boot.utils.ResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP验证规则
     *
     * @param http h
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //开启跨域
        http.csrf().disable().cors();

        //允许跨域使用iframe
        http.headers().frameOptions().disable();

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/api/**", "/swagger-ui.html","/webjars/**", "/v2/api-docs", "/swagger-resources/**","/swagger-ui/**",
                        "/**/login","/**/register","/**/logout").permitAll()    //允许全部访问
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest()   //任何其它请求
                .authenticated();

        //身份验证失败
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            ResponseUtils.writeJson(response, new ResponseDto<>(403, "身份认证失败, 请重新登录"));
        });

        http.addFilter(new AuthorizationFilter(authenticationManagerBean()));
    }


    /**
     * SpringSecurity有默认的跨域配置 会无法放行RequestHeader带有"Authorization"请求
     * 防止前端请求api报出cors error
     *
     * @return *
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedHeader("DELETE");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}

