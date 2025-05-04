package cn.bit.security.config;

import cn.bit.core.constant.SecurityConstant;
import cn.bit.security.filter.JwtAuthenticationFilter;
import cn.bit.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroserviceSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final RedisTemplate<String, Object> redisTemplate;

    // 推荐使用构造函数注入
    public MicroserviceSecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService,
        RedisTemplate<String, Object> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态会话
            .and()
            // 将JWT过滤器添加到UsernamePasswordAuthenticationFilter之前
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests()
            .antMatchers("/auth/**").permitAll().antMatchers("/user/open/**").permitAll().antMatchers("/api/**")
            .hasRole(SecurityConstant.ROLE_INTERNAL_SERVICE)// 允许认证端点公开访问
            .anyRequest().authenticated(); // 其他所有请求需要认证
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService, redisTemplate);
    }
}
