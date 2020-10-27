# spring-cloud-note
markdown note during learning spring cloud

## hystrix 
### actuator
springboot提供的项目监控工具,提供了多种项目的监控数据,hystrix在actuator中,添加了自己的监控数据
#### 添加actuator
1. 添加actuator依赖
2. yml配置暴露监控信息
	management:
		endpoint:
			web:
				exposure:
					include:	* --暴露所有监控
3. http://xxxxxxxxxxxx/actuator

### fhal