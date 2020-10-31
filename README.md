# spring-cloud-note



markdown note during learning spring cloud


## Ribbon
ribbon 提供了负载均衡和重试功能, 它底层是使用 RestTemplate 进行 Rest api 调用
### RestTemplate

#### HttpClient
##### RPC 远程过程调用 

## hystrix 

### actuator
springboot提供的项目监控工具,提供了多种项目的监控数据,hystrix在actuator中,添加了自己的监控数据
#### 添加actuator
1. 添加actuator依赖
2. yml配置暴露监控信息
    management:
    	endpoints:
    		web:
    			exposure:
    				include:	* --暴露所有监控
3. http://xxxxxxxxxxxx/actuator



## rabbitmq
#### 离线安装命令
复制rabbitmq-install 到/root/文件夹 输入
`rpm -ivh *.rpm`

## Feign
#### Feign的远程调用

声明式接口客户端,

1. 导入依赖:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
2. 

#### Feign集成Ribbon
负载均衡, 重试
1. Feign默认启用了Ribbon的重试参数配置: 对所有服务都有效
	ribbon:
		MaxAutoRetries:	0
		MaxAutoRetriesNextServer:	1
		ReadTimeout:	1000
2. 对item-service 单独配置:
	item-service:
		ribbon:
			MaxAutoRetries:	1
			MaxAutoRetriesNextServer:	2
			ReadTimeout:	1000
#### Fegin集成Hystrix
默认不启用Hystrix, Fegin不推荐启用Hystrix

如果有特殊需求要启用Hystrix, 首先做基础配置
1. 添加Hystrix依赖
2. 添加@EnableCircuitBreaker注解到启动类
3. 添加yml文件的配置

##### 降级
在声明式客户端接口的注解中,指定一个降级类,这个降级类需要实现FeignClient的接口,并添加`@Component`注解
```java
@FeginClient(name="item-service",fallback=降级类.class)
public interface ItemFeignClient(){
		...
}
```

#### Zuul 集成Ribbon

zuul不推荐启用重试,默认支持负载均衡,没有重试

要想启用重试,得添加`spring-retry`依赖

zuul.retryable=true











