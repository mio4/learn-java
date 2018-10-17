>从零开始实现一个简单网上商城项目



 - 实现一个在线商城的前台和后端
    - 1. 购物页面
     - 2. 登录注册
     - 3. 后台管理
     - 4. 购物车系统



# 数据表

- 表
  - 用户表
  - 商品表
  - 订单表
  - 分类表
  - order item 订单项



 - 用户表分析 User Table
      - username
      - password
      - id
      - true name 真实姓名
      - email
      - gender 性别
      - phone
      - birthday
      - active code 激活码
      - 用户状态

- 订单 orders  Orders Table
  - 订单id
  - 总金额
  - 订单状态
  - 姓名
  - 地址
  - 电话
  - **用户ID** 外键
- 商品 Product
  - id
  - 商品名称
  - 市场价
  - 商城价
  - 库存
  - **分类id** 外键 
- 分类 Category
  - id
  - 分类名称
- order item
  - 订单项id
  - 订单id
  - 商品id
  - 商品购买数量id



# 设计



- 用户行为

  - 注册
  - 激活：发送邮件
  - 登录
  - 退出

- 技术分析

  - 数据库和表
  - jar包
    - 驱动
    - c3p0
    - dbutils
    - beanutils
    - jstl
    - 发送邮件的jar包
  - 工具类
    - datasourceutils
    - c3p0配置文件
    - uploadutils
    - md5utils 加密
    - mailutils 邮件

- 包结构

  - com.mio4

    - web.servlet
    - service 接口
    - service implementsClass 实现类
    - domain
    - dao 接口
    - dao implementsClass 实现类
    - utils
    - constant
    - filter

    

























































