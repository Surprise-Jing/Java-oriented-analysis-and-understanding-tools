# Java-oriented-analysis-and-understanding-tools
NJU软件工程大实验——面向Java的分析和理解工具

## 运行环境

测试服务器系统：Ubuntu Server20.04（实验目前暂未完成部署配置）

#### 后端运行环境

Java17及以上版本

MySQL 8.0及以上版本

Springboot 2.7

#### 前端运行环境

Nodejs 16.15.1

Vue.js 2（不建议使用3）

## 运行方式

#### 后端

数据库：

在Mysql数据库中，导入`./backend/src/main/resources/joaut.sql`数据库文件，在`application.yml`中修改数据库配置的用户名和密码信息等相关信息。

后端启动：

运行`src/main/java/com/nju/boot/MainApplication.java`文件，启动Springboot后端，端口可在`application.yml`文件中进行修改。

#### 前端

打开`front-end`文件夹，将`.env.development`文件中的IP地址修改成后端运行的IP地址

安装依赖

```
npm install
```

启动前端

```
npm run dev
```

即可在`localhost:8080`端口访问网页
