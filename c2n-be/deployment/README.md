# 部署说明

## 1. 数据库

    MySQL 5.7.29

    存储引擎：INNODB
    字符集编码：utf8mb4


| 数据库名    | 用户名  | 密码   |
|---------|------| ------ |
| brewery | root | 123456 |


## 2. redis




应用列表：

| 服务名                  | 依赖服务 | 验证地址                   | 开放端口  |
|----------------------|------|------------------------|-------|
| portal-api           |      | http://localhost:8081/ | 30001 |
| MYSQL（5.7.29）        |      |                        | 3306  |
| REDIS（5.0.7）         |      |                        | 6379  |


## 本地构建镜像：

### portal-api
    
    cd portal-api
    mvn package -Dmaven.test.skip=true
    ./docker-build.sh


### 启动服务

    
