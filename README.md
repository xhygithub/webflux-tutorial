## Mono - 基本使用
1、try/catch  error   doOnError

2、zip    zipWith    zipWhen（defaultIfEmpty/switchIfEmpty）

3、flatmap    map    doOnNext
> flatmap：处理接下来还有异步操作的代码
>
> map：处理同步操作，同时需要返回其他类型的Mono
> 
> doOnNext：处理同步操作，不返回任何值，例如set方法

4、Mono<Void>    then    方法返回为Mono.empty()

5、Mono.fiter

6、List<Mono> —> Mono<List>    Flux.fromIterable

7、Mono<Optional>    Optional.empty       Optional.of(null)

## DateFormat 处理

yml文件中配置了
Jackson.date-format = yyyy-MM-dd'T'HH:mm:ss.SSS’Z’  
当使用了@EnableWebFlux或者@EnableWebFlux注解时   
yml中的配置就不会生效
想要全局设置时间格式，就需要自己去重写关于时间格式控制的方法——>
可参考config下的JsonDateFormat文件


## redis - start redis to test

下载redis镜像：
> docker pull redis:5.0

启动redis容器：
> docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-d redis:5.0 redis-server --appendonly yes


enter Redis container：
> docker exec -it redis /bin/bash
> redis-cli //连接redis服务
> get key //执行相关命令查看缓存数据