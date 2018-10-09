## Summary

- tomcat:一个servlet容器，全盘控制着Servlet的一生，当浏览器发送请求的时候，容器生成HttpServletResponse和HttpServletRequest对象，并且作为参数发送给Servlet
- Servlet类的service()方法是根据请求选择调用doPost()方法还是doGet()方法
- 每从浏览器发送一个请求，tomcat容器就调用service()方法并且为其分配一个线程



### BearDemo

关键词：HTML表单、Servlet、tomcat、JSP、MVC思想

- 例子完成的功能：
  - 从前台使用POST获取表单数据 
  - 后台使用BeerSelect类处理数据
  - 最开始使用out直接print html页面，后面使用JSP生成动态页面
- 从这个例子中初次使用了MVC思想：
  - model：BeerSelect后台处理类
  - view：JSP动态页面
  - control：servlet类 



---

- JDBC_Test ：对于JDBC的使用练习

 - BasicDemo系列
     - **BasicDemo5**：实现简单的注册和登陆功能
     - BasicDemo6：request和response的细节以及用途
     - BasicDemo7：Cookie和Session的使用
     - BasicDemo8 ：JSP使用的细节
     - BasicDemo9：查询数据库中的所有商品，并展示出来
     - **BasicDemo10**：实现登录时验证码和记住密码功能（在BasicDemo5增加功能）
     - BasicDemo11：对于MVC思想的实践（包括对于反射的使用案例）
     - BasicDemo12：银行转账模拟（事务）
     - BasicDemo13：对于JDBC的CRUD操作练习（商品的展示、查询、修改、**分页**展示）

     