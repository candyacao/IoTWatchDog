# IoTWatchDog

物联网环境监测数据中心

## Usage

- docker-compose up -d
- 链接docker，进行数据库表结构创建。这个步骤可以在项目初始化的时候进行配置

## Docker oracle db server message
```
oracle server message：
hostname: localhost
port: 49161
sid: xe
username: system
password: oracle
For example, connect to database with sqlplus:

sqlplus system/oracle@//localhost:49161/xe
Password for SYS & SYSTEM

oracle
Login by SSH

ssh root@localhost -p 49160
password: admin
Login to web administrator on a browser:

http://localhost:49162/apex
```

## Resources

- [docker oracle xe 10g](https://hub.docker.com/r/dkfi/docker-oracle-xe-10g/)
