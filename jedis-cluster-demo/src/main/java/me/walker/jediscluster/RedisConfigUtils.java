package me.walker.jediscluster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration(RedisConstants.REDIS_CONFIG)
public class RedisConfigUtils {

    @Value("${Redis.maxWaitMillis}")
    private String maxWaitMillis;

    @Value("${Redis.maxTotal}")
    private String maxTotal;

    @Value("${Redis.maxIdle}")
    private String maxIdle;

    @Value("${Redis.timeout}")
    private String timeout;

    @Value("${Redis.maxRedirections}")
    private String maxRedirections;

    @Value("${Redis.nodes}")
    private String nodes;

    public String getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(String maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(String maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getMaxRedirections() {
        return maxRedirections;
    }

    public void setMaxRedirections(String maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }
}
