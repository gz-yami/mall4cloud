### Docker安装Redis

docker拉取最新镜像

```shell
docker pull redis
```

启动redis

```shell
docker run --name redis --restart=always -p 6379:6379 \
-d redis
```

