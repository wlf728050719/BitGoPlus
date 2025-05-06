package cn.bit.security.filter;

import cn.bit.core.constant.RedisKey;
import cn.bit.core.pojo.dto.security.BitGoUser;
import cn.bit.core.pojo.dto.security.InternalServiceAuthentication;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private RedisTemplate<String, Object> redisTemplate;

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(SecurityConstant.HEADER_AUTHORIZATION);
        final String sourceHeader = request.getHeader(SecurityConstant.HEADER_SOURCE);
        // 处理内部服务Token
        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstant.TAG_INTERNAL) // 验证鉴权和来源服务header开头
            && sourceHeader != null && sourceHeader.startsWith(SecurityConstant.TAG_SERVICE)) {
            String source = sourceHeader.substring(SecurityConstant.TAG_SERVICE.length());
            String token = authorizationHeader.substring(SecurityConstant.TAG_INTERNAL.length());
            if (jwtUtil.validateInternalToken(token, source)) {
                // 创建新的内部服务权限并注入安全上下文
                Authentication auth = new InternalServiceAuthentication(source);
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(request, response);
                return;
            }
        }
        // 处理外部请求Token
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstant.TAG_BEARER)) { // 验证鉴权header
            jwt = authorizationHeader.substring(7);
            //从token中提取用户名
            username = jwtUtil.extractData(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 与缓存中jwt不一致禁止访问
            String key = String.format(RedisKey.TOKEN_KEY_FORMAT, username);
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value == null || !value.equals(jwt)) {
                chain.doFilter(request, response);
                return;
            }

            //加载用户
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            BitGoUser bitGoUser = (BitGoUser) userDetails;

            // 用户被删除或冻结时禁止访问
            if (bitGoUser.getUserBaseInfo().getLockFlag() != 0 || bitGoUser.getUserBaseInfo().getDelFlag() != 0) {
                chain.doFilter(request, response);
                return;
            }

            if (jwtUtil.validateUserToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
