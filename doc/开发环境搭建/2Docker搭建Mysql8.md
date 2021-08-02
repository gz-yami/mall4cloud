### Docker搭建Mysql8

```shell
# 创建 sonarqube 工作目录，映射目录都放在这里
mkdir -p /mydata/mysql

# 编辑mysql配置 如下
vi /mydata/mysql/my.cnf
```

将以下内容复制到配置文件中：[Linux中在控制台下<u>Shift+Insert</u>或<u>单击鼠标右键</u>为粘贴键]

```shell
[mysqld]
# 设置3306端口
port=3306
# 服务端使用的字符集默认为utf8mb4
character-set-server=utf8mb4
# 服务端使用的排序规则
collation-server=utf8mb4_general_ci
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 默认使用“mysql_native_password”插件认证
# mysql 8.0 需要设置 mysql_native_password
default_authentication_plugin=mysql_native_password
# 关闭 only_full_group_by
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION

# binlog 日志存放路径
log-bin=mysql-binlog
# 日志中记录每一行数据被修改的形式
binlog-format=ROW
# 当前机器的服务 ID, 如果为集群时不能重复，不要和 canal 的 slaveId 重复
server_id=1

[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8mb4
[client]
# 设置mysql客户端连接服务端时默认使用的端口
port=3306
```

Esc键后输入冒号，也即":"(不需要双引号)，之后键入wq即保存退出

```shell
# 创建映射目录，查看日志
mkdir /mydata/mysql/data && \
mkdir /mydata/mysql/conf && \
mkdir /mydata/mysql/logs && \
mkdir /mydata/mysql/mysql-files

# 设置为 777 权限
chmod 777 /mydata/mysql/data && \
chmod 777 /mydata/mysql/conf && \
chmod 777 /mydata/mysql/logs && \
chmod 777 /mydata/mysql/mysql-files

# 下载mysql8
docker pull mysql:8

# 启动容器
docker run -p 3306:3306 --name mysql --restart=always \
    -v /mydata/mysql/conf:/etc/mysql \
    -v /mydata/mysql/logs:/var/log/mysql \
    -v /mydata/mysql/data:/var/lib/mysql \
    -v /mydata/mysql/mysql-files:/var/lib/mysql-files \
    -v /mydata/mysql/my.cnf:/etc/my.cnf \
    -e MYSQL_ROOT_PASSWORD=root \
    -e TZ=Asia/Shanghai \
    -d mysql:8 
```

#### 进入容器

```shell
docker exec -it mysql bash

# 连接mysql,-p后输入密码
mysql -uroot -p
root

# 查看用户信息
select user,host,authentication_string from mysql.user;

# 设置权限（为root分配权限，以便可以远程连接）
grant all PRIVILEGES on *.* to root@'%' WITH GRANT OPTION;

# 更新密码算法，便于使用sqlyog连接
ALTER user 'root'@'%' IDENTIFIED BY 'root' PASSWORD EXPIRE NEVER;
ALTER user 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;
```

输入exit退出容器

使用数据库连接工具连接上mysql数据库后，执行`db/mall4cloud-all.sql`文件，确保之后服务运行正常。