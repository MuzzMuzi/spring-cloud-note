package test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        //配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //redis服务地址列表
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("192.168.64.150",7000));
        shards.add(new JedisShardInfo("192.168.64.150",7001));
        shards.add(new JedisShardInfo("192.168.64.150",7002));
        //创建jedis分片客户端对象
        ShardedJedisPool pool = new ShardedJedisPool(config, shards);
        ShardedJedis j = pool.getResource();
        //添加1000个键值对数据,分散存放到三个redis服务器
        for (int i = 0;i<1000;i++){
            j.set("key"+i,"value"+i);
        }
        //关闭连接
        j.close();
        pool.close();
    }
}
