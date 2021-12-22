





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