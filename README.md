# atd-website-backend
![https://img.shields.io/badge/license-MIT-green](https://img.shields.io/badge/license-MIT-green)
![https://github.com/HarsonYoung](https://img.shields.io/badge/Author-Harson-blue)
![https://github.com/atdstudio/atd-website-backend/tree/master](https://img.shields.io/badge/Version-v1.0-brightgreen)

### 项目介绍

​	本项目是为了解决社团中诸多繁琐的任务转移到Web应用上实现自动化。但公网带宽资源较昂贵，为节省成本，文件分发部分采用动态链接，现阶段链接由人工直接管理，稳定性较低。

​	项目地址 [ATD计算机协会官网](https://atd.ac.cn)

### 项目架构

项目基于时下流行的Spring Boot框架，具体架构如图

![](https://c.mipcdn.com/i/s/cdn.gksec.com/2020/05/16/a40ce80c48fa1/stack.jpg)

### 项目特点

1. RESTful API
2. Shiro鉴权实现会员特权功能
3. 集成Redis(Jedis)

### 技术选型

- Spring Boot release 2.2.2
- Mybatis-Plus
- Mysql 8.0
- Shiro 1.4
- Fastjson 1.2
- Minio 3.0

