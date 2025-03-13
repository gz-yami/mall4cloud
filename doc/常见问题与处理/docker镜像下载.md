24年开始国内大规格禁用docker镜像，所以`中间件docker-compse一键安装`文件夹下提供两个docker compose文件，请根据自己的服务器配置来选择

**注意：docker compose的中间件镜像版本经测试过都可正常下载，本地开发、测试也是使用一样的版本，所以当你无法下载镜像时，请重点配查你所使用的镜像源是否还可用**

可用的docker镜像源，自行到网上搜索，选择最新日期可用就可，配置后还是无法下载就继续搜索替换其他docker镜像源

## 1. docker-compose.yaml

这份文件中的镜像地址已经处理过，可以直接下载，不用额外去配置docker镜像源

但是你要改变镜像版本的话，就要自行配置可用的镜像源，且到DockerHub中确认你填写的镜像地址真实存在


### 2. docker-compose-native.yaml

使用这份配置，要更新docker的镜像源，确保可用

且删除原本的`docker-compose.yaml`, 将`docker-compose-native.yaml`重命名为`docker-compose.yaml`

