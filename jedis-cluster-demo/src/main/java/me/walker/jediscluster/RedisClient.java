package me.walker.jediscluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class RedisClient {
    private static JedisCluster jedisCluster = null;

    @Autowired
    private static ApplicationContext applicationContext;

    public static synchronized JedisCluster getJedisCluster(){
        if (jedisCluster == null){
            RedisConfigUtils redisConfig = (RedisConfigUtils) applicationContext.getBean(RedisConstants.REDIS_CONFIG);

            JedisPoolConfig config = new JedisPoolConfig();

            //最大活动个数
            config.setMaxTotal(Integer.parseInt(redisConfig.getMaxTotal()));
            //最大空闲时间
            config.setMaxIdle(Integer.parseInt(redisConfig.getMaxIdle()));
            //连接最大等待时间
            config.setMaxWaitMillis(Integer.parseInt(redisConfig.getMaxWaitMillis()));

            Set<HostAndPort> jedisNodes = new HashSet<HostAndPort>();

            String nodes = redisConfig.getNodes();
            if (nodes == null) {
                return null;
            }

            String[] nodeStrs = nodes.split("\\|");
            for (String nodeStr : nodeStrs) {
                String[] nodeArray = nodeStr.split(":");
                if (nodeArray.length == 2) {
                    String host = nodeArray[0];
                    try {
                        int port = Integer.parseInt(nodeArray[1]);
                        jedisNodes.add(new HostAndPort(host, port));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            jedisCluster = new JedisCluster(jedisNodes, config);
            return jedisCluster;
        }

        return jedisCluster;
    }
}
