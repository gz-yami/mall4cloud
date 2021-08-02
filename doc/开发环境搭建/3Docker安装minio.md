### Docker安装minio

数据存储目录：/mydata/minio/data

```shell
mkdir -p /mydata/minio/data

# 设置为 666， 可读可写，但不能执行
chmod 666 /mydata/minio/data
```

1、docker拉取最新镜像

```shell
docker pull minio/minio
```

2、运行minio

```shell
docker run -p 9000:9000 \
  --name minio --restart=always \
  -v /mydata/minio/data:/data \
  -e "MINIO_ROOT_USER=AKIAIOSFODN342XAMPLE" \
  -e "MINIO_ROOT_PASSWORD=wJalrXUtnFEMI/K7MD81G/bPxRfiCYEXAMPLEKEY" \
  -d minio/minio server /data
```


3、访问minio管理页面

```shell
http://localhost:9000/

账号：AKIAIOSFODN342XAMPLE

密码：wJalrXUtnFEMI/K7MD81G/bPxRfiCYEXAMPLEKEY
```

4、访问minio管理页面，右下角有个加号，创建存储区域(create bucker) 名为`mall4cloud`

![image-20210616090042502](../img/开发环境搭建文档/minio创建桶.png)

若要前端可以读取图片，需要设置权限

![image-20210629112059876](../img/开发环境搭建文档/minio配置.png)

![](../img/开发环境搭建文档/minio配置-2.png)