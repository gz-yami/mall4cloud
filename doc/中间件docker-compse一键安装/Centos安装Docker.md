## Centos安装 Docker

从 2017 年 3 月开始 docker 在原来的基础上分为两个分支版本: Docker CE 和 Docker EE。

Docker CE 即社区免费版，Docker EE 即企业版，强调安全，但需付费使用。

本文介绍 Docker CE 的安装使用。

移除旧的版本：

```shell
$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
```

安装一些必要的系统工具：

```shell
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

添加软件源信息：

```shell
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

更新 yum 缓存：

```shell
# centos 7
sudo yum makecache fast
# CentOS 8没有fast这个命令
sudo yum makecache
```

安装 Docker-ce：

```shell
sudo yum -y install docker-ce
```

查看已安装docker版本

```shell
docker version
```

启动 Docker 后台服务

```shell
sudo systemctl start docker
```

开机启动

```shell
sudo systemctl enable docker
```


## 镜像加速

鉴于国内网络问题，后续拉取 Docker 镜像十分缓慢，我们可以需要配置加速器来解决。

可以使用阿里云的docker镜像地址：https://7qyk8phi.mirror.aliyuncs.com

新版的 Docker 使用 `/etc/docker/daemon.json`（Linux，没有请新建）。

请在该配置文件中加入：

（没有该文件的话，请先建一个）

```json
{
  "registry-mirrors": ["https://7qyk8phi.mirror.aliyuncs.com"]
}
```

重启docker

```shell
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 检查加速器是否生效

配置加速器之后，如果拉取镜像仍然十分缓慢，请手动检查加速器配置是否生效，在命令行执行 `docker info`，如果从结果中看到了如下内容，说明配置成功。

```shell
Registry Mirrors:
 https://7qyk8phi.mirror.aliyuncs.com/
```

### 下载docker-compose

目前 Docker 官方用 GO 语言 重写 了 Docker Compose，并将其作为了 docker cli 的子命令，称为 Compose V2。

安装到全局
```shell
curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-`uname -s`-`uname -m` > docker-compose
如果上面下载很慢可以用一下命令找到适合本系统的docker-compose url
echo https://github.com/docker/compose/releases/latest/download/docker-compose-`uname -s`-`uname -m`
将输出结果在浏览器打开，这样就会自动下载命名为docker-compose再上传到服务器上

sudo mv docker-compose /usr/libexec/docker/cli-plugins
sudo chmod +x /usr/libexec/docker/cli-plugins/docker-compose
sudo chown root:root /usr/libexec/docker/cli-plugins/docker-compose
```

验证和使用
```shell
$ docker compose version

Docker Compose version v2.x.x
```
如果能正常返回，说明已经可以正常使用。只要将熟悉的 docker-compose 命令替换为 docker compose，即可使用 Docker Compose。

比如`docker-compose up -d`改为`docker compose up -d`

