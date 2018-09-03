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