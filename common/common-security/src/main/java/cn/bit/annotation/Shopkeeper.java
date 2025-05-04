package cn.bit.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 管理员或店主
 * </p>
 * Date:2025/04/16 21:15:34
 *
 * @author wlf
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@BitGoUserService.checkAdmin(authentication.principal)")
public @interface Shopkeeper {}
