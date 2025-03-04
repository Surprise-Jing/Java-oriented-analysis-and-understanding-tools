# 面向Java的分析与理解工具——API接口文档
[toc]
## 1	环境变量

### 默认环境1
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://172.27.158.191:8888|


## 2	面向Java的分析与理解工具——API接口文档

##### 说明
> 



##### 联系方式
- **联系人：**
- **邮箱：**
- **网址：**//

##### 文档版本
```
V1.0.0
```


## 3	代码度量接口

## 3.1	统计文件内所有函数的代码度量信息

> GET  /metrics
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 3.2	统计函数的所有基本度量信息

> POST  /metrics
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 3.3	计算函数的被调用次数

> POST  /metrics/called
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 3.4	计算函数的调用次数

> POST  /metrics/calling
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 3.5	计算函数的圈复杂度

> POST  /metrics/cyc
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 3.6	计算所有类的最大继承深度

> GET  /metrics/depth
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 3.7	统计文件的行数信息

> GET  /metrics/lines
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| linesOfAll|int32||false||
| linesOfBlanks|int32||false||
| linesOfCode|int32||false||
| linesOfComment|int32||false||

##### 接口描述
> 




## 3.8	统计文件中函数的行数信息

> POST  /metrics/methodlines
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| linesOfAll|int32||false||
| linesOfBlanks|int32||false||
| linesOfCode|int32||false||
| linesOfComment|int32||false||

##### 接口描述
> 




## 3.9	计算函数的入参个数

> POST  /metrics/param
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 4	获取生成图的图片路径

## 4.1	获得语法分析树AST图片

> GET  /save/ast
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 4.2	获得控制流图CFG图片

> GET  /save/cfg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 4.3	获得函数调用图CG图片

> GET  /save/cg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 4.4	获得控制流图CFG图片

> GET  /save/pdg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 5	文件上传接口

## 5.1	获取文件内容

> GET  /file
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 5.2	上传文件

> POST  /file
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uid||uid|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| deleted|boolean||false|是否删除|
| enable|boolean||false|是否禁用链接|
| id|string||false|编号|
| md5|string||false|MD5值|
| name|string||false|文件名|
| type|string||false|文件类型|
| uploadTime|string||false|上传时间|
| url|string||false|下载链接|

##### 接口描述
> 




## 5.3	删除文件

> GET  /file/delete
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
|uid||uid|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 5.4	获取文件基本信息

> GET  /file/info
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| deleted|boolean||false|是否删除|
| enable|boolean||false|是否禁用链接|
| id|string||false|编号|
| md5|string||false|MD5值|
| name|string||false|文件名|
| type|string||false|文件类型|
| uploadTime|string||false|上传时间|
| url|string||false|下载链接|

##### 接口描述
> 




## 5.5	获得用户上传的所有文件

> GET  /file/user
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uid||uid|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 6	程序切片接口

## 6.1	基于数据流方程的切片

> GET  /slicer/dataflow
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
|lineNumber||lineNumber|
|variable||variable|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 6.2	动态切片

> GET  /slicer/dynamic
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
|input||input|
|lineNumber||lineNumber|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 6.3	基于程序依赖图的切片

> GET  /slicer/pdg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
|lineNumber||lineNumber|
|variable||variable|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 7	用户管理接口

## 7.1	根据id获得用户信息

> GET  /user
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false|用户邮箱|
| id|string||false|用户编号|
| password|string||false|用户密码(密文存储)|
| phoneNumber|string||false|用户电话号码|
| status|int32||false|用户状态，0表示正常，1表示被禁|
| updateAt|string||false|最近登录日期|
| username|string||false|用户名称(唯一)|

##### 接口描述
> 




## 7.2	更新用户

> PUT  /user
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false|用户邮箱|
| id|string||false|用户编号|
| password|string||false|用户密码(密文存储)|
| phoneNumber|string||false|用户电话号码|
| status|int32||false|用户状态，0表示正常，1表示被禁|
| updateAt|string||false|最近登录日期|
| username|string||false|用户名称(唯一)|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false|用户邮箱|
| id|string||false|用户编号|
| password|string||false|用户密码(密文存储)|
| phoneNumber|string||false|用户电话号码|
| status|int32||false|用户状态，0表示正常，1表示被禁|
| updateAt|string||false|最近登录日期|
| username|string||false|用户名称(唯一)|

##### 接口描述
> 




## 7.3	根据token获得用户信息

> GET  /user/info
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|token||token|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false|用户邮箱|
| id|string||false|用户编号|
| password|string||false|用户密码(密文存储)|
| phoneNumber|string||false|用户电话号码|
| status|int32||false|用户状态，0表示正常，1表示被禁|
| updateAt|string||false|最近登录日期|
| username|string||false|用户名称(唯一)|

##### 接口描述
> 




## 7.4	用户登录

> POST  /user/login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| password|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 7.5	用户登出

> POST  /user/logout
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 7.6	用户注册

> POST  /user/register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false|用户邮箱|
| id|string||false|用户编号|
| password|string||false|用户密码(密文存储)|
| phoneNumber|string||false|用户电话号码|
| status|int32||false|用户状态，0表示正常，1表示被禁|
| updateAt|string||false|最近登录日期|
| username|string||false|用户名称(唯一)|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| email|string||false|用户邮箱|
| id|string||false|用户编号|
| password|string||false|用户密码(密文存储)|
| phoneNumber|string||false|用户电话号码|
| status|int32||false|用户状态，0表示正常，1表示被禁|
| updateAt|string||false|最近登录日期|
| username|string||false|用户名称(唯一)|

##### 接口描述
> 




## 8	Java文件解析成代码抽象模型接口

## 8.1	获得语法分析树AST

> GET  /graph/ast
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 8.2	获得控制流程图CFG

> POST  /graph/cfg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 8.3	获得函数调用图Call graph

> GET  /graph/cg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 8.4	获得文件的所有方法

> GET  /graph/method
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 8.5	获得程序依赖图PDG

> POST  /graph/pdg
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| result|string||false||

##### 接口描述
> 




## 8.6	获得程序依赖图PDG(PNG图片格式)

> POST  /graph/pdg_png
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| id|int32||false||
| methodName|string||false||
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 



