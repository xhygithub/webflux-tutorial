## Mono - 基本使用（具体参考OrderController）
1、try/catch  error   doOnError
> 针对Mono中对异常处理

2、zip    zipWith    zipWhen（defaultIfEmpty/switchIfEmpty）
> 需要对多个异步操作一起处理可用zip相关方法

3、flatmap    map    doOnNext
> flatmap：处理接下来还有异步操作的代码
>
> map：处理同步操作，同时需要返回其他类型的Mono
> 
> doOnNext：处理同步操作，不返回任何值，例如set方法

4、Mono<Void>    then    方法返回为Mono.empty()
> 当返回值Mono<其他类型>时，方法又需要返回为Mono<Void>形式，可以使用then；
> 或者返回Mono.empty()

5、Mono.filter
> 对Mono类型对值进行过滤，方法类似于stream中filter

6、List<Mono> —> Mono<List>    Flux.fromIterable
> 对List集合中的元素进行异步处理时，需要用Flux.fromIterable

7、Mono<Optional>    Optional.empty       Optional.of(null)
> 当使用webflux框架时，Mono<Optional>在前端显示的会不是详细json内容，需要注册{registerModule(new Jdk8Module())}，
> 可参考config下的JsonDateFormat文件中的相关配置

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