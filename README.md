# spring-cloud-note
markdown note during learning spring cloud
test url:
监控端口:http://localhost:3001/actuator/hystrix.stream

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

## rabbitmq
#### 离线安装命令
复制rabbitmq-install 到/root/文件夹 输入
`rpm -ivh *.rpm`