package cn.bit.gateway.router;

import cn.bit.gateway.config.DynamicRouteConfig;
import com.alibaba.cloud.nacos.NacosConfigManager;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Slf4j
@Component
@AllArgsConstructor
public class DynamicRouteLoader {

    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter routeDefinitionWriter;
    private final DynamicRouteConfig dynamicRouteConfig;
    private final Set<String> routeIds = new HashSet<>(); // 当前存在的路由ID集合

    @PostConstruct
    public void initRouteConfigListener() throws NacosException {
        // 项目启动时拉取配置并添加监听器
        String configInfo =
            nacosConfigManager.getConfigService().getConfigAndSignListener(dynamicRouteConfig.getDataId(),
                dynamicRouteConfig.getGroup(), dynamicRouteConfig.getTimeOutMs(), new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        // 配置变更时更新路由
                        updateRouteConfig(configInfo);
                    }
                });

        if (configInfo != null && !configInfo.isEmpty()) {
            updateRouteConfig(configInfo);
        }
    }

    /**
     * 更新路由配置
     * 
     * @param configInfo 从Nacos获取的路由配置JSON字符串
     */
    private void updateRouteConfig(String configInfo) {
        log.info("获取到最新路由配置: {}", configInfo);
        try {
            // 解析JSON配置获取路由定义列表
            List<RouteDefinition> newRouteDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);

            // 新路由ID集合
            Set<String> newRouteIds = new HashSet<>();

            // 1. 处理新增或更新的路由
            for (RouteDefinition routeDefinition : newRouteDefinitions) {
                String routeId = routeDefinition.getId();
                newRouteIds.add(routeId);

                if (routeIds.contains(routeId)) {
                    // 路由已存在 - 更新操作
                    routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                    log.info("[路由更新] ID: {}, 详情: {}", routeId, routeDefinition);
                } else {
                    // 路由不存在 - 新增操作
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                    log.info("[路由添加] ID: {}, 详情: {}", routeId, routeDefinition);
                }
            }

            // 2. 处理需要删除的路由
            Set<String> routesToRemove = new HashSet<>(routeIds);
            routesToRemove.removeAll(newRouteIds); // 找出需要删除的路由ID

            for (String routeId : routesToRemove) {
                routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
                log.info("[路由删除] ID: {}", routeId);
            }

            // 3. 更新当前路由ID集合
            routeIds.clear();
            routeIds.addAll(newRouteIds);

            log.info("路由同步完成. 当前路由数量: {}, 路由ID列表: {}", routeIds.size(), routeIds);
        } catch (Exception e) {
            log.error("路由配置更新异常", e);
        }
    }
}
