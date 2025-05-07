package cn.bit.security.filter;

import cn.bit.core.constant.RedisKey;
import cn.bit.core.pojo.dto.security.BitGoUser;
import cn.bit.core.pojo.dto.security.InternalServiceAuthentication;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
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

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private RedisTemplate<String, Object> redisTemplate;

    private void handleJwtException(JwtException e, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String errorMsg = "无效的Token";
        if (e instanceof ExpiredJwtException) {
            errorMsg = "Token已过期";
        } else if (e instanceof MalformedJwtException) {
            errorMsg = "无效的Token格式";
        } else if (e instanceof SignatureException) {
            errorMsg = "无效的Token签名";
        }
        response.getWriter().write("{\"error\": \"" + errorMsg + "\"}");
        log.warn("JWT验证失败: {}", errorMsg);
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(SecurityConstant.HEADER_AUTHORIZATION);
        // 未添加授权的访问直接放行
        if (authorizationHeader == null) {
            chain.doFilter(request, response);
            return;
        }
        if (authorizationHeader.startsWith(SecurityConstant.TAG_INTERNAL)) { // 处理内部服务Token
            // 校验header
            final String sourceHeader = request.getHeader(SecurityConstant.HEADER_SOURCE);
            if (sourceHeader == null || !sourceHeader.startsWith(SecurityConstant.TAG_SERVICE)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Source Header解析错误");
                return;
            }
            // 从header中获取来源
            String source = sourceHeader.substring(SecurityConstant.TAG_SERVICE.length());
            // 从header中获取token
            String internalToken = authorizationHeader.substring(SecurityConstant.TAG_INTERNAL.length());
            // 从token中获取服务名
            String serviceName;
            try {
                serviceName = jwtUtil.extractData(internalToken);
            } catch (JwtException e) {
                handleJwtException(e, response);
                return;
            }
            if (serviceName.equals(source)) {
                // 创建新的内部服务权限并注入安全上下文
                Authentication auth = new InternalServiceAuthentication(source);
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(request, response);
            }
        } else if (authorizationHeader.startsWith(SecurityConstant.TAG_BEARER)) { // 处理外部请求Token
            // 从header中获取token
            String externalToken = authorizationHeader.substring(SecurityConstant.TAG_BEARER.length());
            // 从token中解析用户名
            String username;
            try {
                username = jwtUtil.extractData(externalToken);
            } catch (JwtException e) {
                handleJwtException(e, response);
                return;
            }
            // 与缓存中jwt不一致禁止访问
            String key = String.format(RedisKey.TOKEN_KEY_FORMAT, username);
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value == null || !value.equals(externalToken)) {
                chain.doFilter(request, response);
                return;
            }

            // 加载用户
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            BitGoUser bitGoUser = (BitGoUser) userDetails;

            // 用户被删除或冻结时禁止访问
            if (bitGoUser.getUserBaseInfo().getLockFlag() != 0 || bitGoUser.getUserBaseInfo().getDelFlag() != 0) {
                chain.doFilter(request, response);
                return;
            }

            // 将授权加入安全上下文
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            chain.doFilter(request, response);
        }
    }
}
