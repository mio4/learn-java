# Java

## Part1

阿里校招研发工程师岗位考察的基础可以大致总结如下：
1，操作系统。尤其是内存/线程/进程方面
2，计算机网络协议，重点关注 TCP/UDP/HTTP。
3，数据结构与算法。我本人也没搞过acm,，目测考察得算法不是ACM那种级别。这一块儿系统学习后，后面再多刷刷大公司的笔试题就ok了。
4，数据库，这一部分倒没怎么问我。不过资料显示数据库通常考察以下几点 1） mysql存储引擎中索引的实现机制；2）.数据库事务的几种粒度；3）.行锁，表锁；乐观锁，悲观锁
5，设计模式，熟悉常用的几种设计模式就行。
6，Java语言基础。熟悉java语言基础，了解JVM、内存模型，重点考察 面向对象、集合框架（继承关系，实现原理）、异常体系、IO体系、并发集合、多线程。熟悉多线程编程/并发/线程安全明显可以加分。
7，J2EE，熟悉Spring/Spring MVC , ORM 什么的吧。看过源码，了解原理之类的也明显可以加分。
8，Linux，熟悉基本的linux命令就行吧  - - 
9，自己做的项目。
10，其他发散性的问题。拼人品了。





## Part 2

1. Java后台开发方向的同学千万千万不要把眼光仅仅局限于三层架构和后台框架, 而要把目光转向/分布式系统/大数据处理/多线程编程/数据库性能调优/编译原理等知识, 尤其是**分布式系统**，非常重要， 尽管确实有点难以在项目中使用, 但了解总比没了解好(因为面试可能会问啊, 说多都是眼泪, 真的) 前沿技术那么多, 赶得上时髦才是硬道理
2. **实际上学习后台开发未必要从Java开始**, 使用python或者NodeJs感觉入门更快一些(脚本语言拘束较少), Java入门web开发相对而言很重量级...但是自从有了spring boot后其实感觉没差, 或者学后台开发从spring boot开始也是可以的, 未必从坑死无数人的SSH(Struts2, Spring, Hibernate)下手
3. 后悔9月初懒得刷笔试题, 其实刷笔试题是能对掌握计算机基础知识有很大的帮助. 勿走前车之鉴, 我9月份投的所有企业只有网易是过了笔试的..
4. 在线编程题请锁定[leetcode](https://link.zhihu.com/?target=https%3A//leetcode.com/)或者[lintcode](https://link.zhihu.com/?target=http%3A//www.lintcode.com/zh-cn/)(lintcode有时候需要翻墙, 不过好处是题目是中文版)
5. 对简历上的每一个字负责, 而且最好简洁明朗, 拒绝啰嗦, 让面试官的所有问题都针对你的简历进行提问, 否则他要是用多年的工作经验碾压你就是分分钟的事了
6. 确实要看很多很多书, 但是当你懂得了看书的技巧后, 其实阅读的速度确实会越来越快的
7. "**\*所有你在书上看到的东西, 都是基础***."(出自某面试官) 做项目才是吸收知识的最好途径...**作为工科类的学生, 面试官更注重的是你做出了什么, 而不仅是你知道什么...**
8. 对于程序员而言简历上的项目经验是非常重要的, 几乎所有面试官都会拿项目经验开始问你问题. 对于你简历的所有项目经验, 它们用到了哪些后台框架, 不仅要知其然, 还要知其所以然. 不要偷懒, 源码得读起来, 一问三不知的时候就真的醉了. 这方面推荐多看博客多看课外书籍
9. 不要因为懂得越多就越是持有谦卑的态度, 在面试官面前, 就是表现自己最好的一面, 做最好的自己, 就是干. 坚持就是胜利, 尽管它来之不易

下面列举一些我觉得对我来说非常有用的书单和网站, 还有推荐使用的工具, 仅供参考, 希望能帮助到跟我一样找工作的兄弟姐妹们, 还有尚未定位自己的师弟师妹. 以下所有书籍(网站)建议阅读, **不一定要读完, 但关键和核心得抓住**

**一、计算机基础**

**《深入理解计算机系统》**

著名的CSAPP, 这本书没有作为教材让我觉得很惊讶, 从c语言到汇编语言到硬件再到操作系统, 写得非常好. 虽然是本非常厚的砖头书, 而且看英文的效果比看中文更好, 但是是一本能帮助深入理解计算机系统的书. 基本上把这本书吃透面试操作系统的大部分问题都不是问题

**《算法导论(第三版)》**

被很多acmer coder奉为学算法的经典之作, 尽管不太适合初学者, 因为它这本书很多内容只提供了伪代码, 而没有具体实现. 但是我建议还是从这本书学数据结构和算法最好, 因为日后的编程语言对实现而言实际上并没有特别大的障碍, 只是适合与不适合的选择罢了, 而把想法转换成编程语言才是对算法知识的考验. 如果不想太过深入的话可以忽略掉 第四部分(高级设计和分析技术) 第五部分(高级数据结构) 和 第七部分(算法问题选编), 你会发现书其实比你想象中薄很多噢!

**acm-cheet-sheet**

如果你觉得看伪代码转换成编程语言这样的学习方式很吃力, 没关系, 这个pdf链接完全可以满足你的需求, 里面把很多常用的算法实现了(c/c++语言), 坚持临摹(注意不是死记硬背, 是临摹!)绝对能应对大多数公司的笔试编程题, 附上[pdf下载链接](https://link.zhihu.com/?target=https%3A//github.com/soulmachine/acm-cheat-sheet)

**《STL源码剖析》**

如果你是经常用c++刷算法题的同学, 那么一定经常用STL的各种集合, vector, set, stack, queue等等..它们的实现原理, 在源码面前, 了无秘密

**《Linux命令行与shell脚本编程大全》**

亚马逊书店五星推荐噢! 里面大概讲述了很多linux系统的使用和命令行等. linux是Java后台开发人员必知必会的操作系统, 而命令行是使用linux系统必须要面对的 对于不想装linux系统的同学其实可以考虑使用腾讯云学习一些linux相关的命令行知识(没错, 不需要图形界面), 学生价一个月才一块钱, 跟月饼厂一个月十块钱比起来也太划算了吧! 而且用linux也蛮酷的啊（.....）再推荐一个神器: [GitHub - robbyrussell/oh-my-zsh](https://link.zhihu.com/?target=https%3A//github.com/robbyrussell/oh-my-zsh) 具体的好处可以看这则知乎: [mac 装了 oh my zsh 后比用 bash 具体好在哪儿？ - 软件](https://www.zhihu.com/question/29977255)

[@defcon](http://www.zhihu.com/people/e9a438b7f4d40f37bd640f7b8c10537e)

 的解答.



另外关于linux命令行的详细使用, 有一个网站也特别好用: [Linux命令大全(手册)_Linux常用命令行实例详解_Linux命令学习手册](https://link.zhihu.com/?target=http%3A//man.linuxde.net/). 将相关命令的功能, 配置项和使用案例讲得非常详尽.

**VIM**

vim编辑器相对其他编辑器来说确实难用了一些, 入门的学习成本也很高, 不过在linux系统中使用vim可以说是必须掌握的技能. 记得我大一的时候程序设计课的TA就开始安利我们使用vim了, 不过当时真的是太愚钝, 根本就学不会(说得好像现在就学会了一样...)

网上关于vim的教程很多, 不过有的感觉太专业(比如自定义功能之类的), 我个人认为只要你安装了vim后, 命令行输入vimtutor, 把那个教程过一遍基本就可以了....入门的难点主要是理解vim的三种模式: 编辑/命令/视图...理解了这个后就顺风顺水了

![img](https://pic4.zhimg.com/v2-27d5b6fb6f6a859b728336aa094bbbb3_b.png)![img](https://pic4.zhimg.com/80/v2-27d5b6fb6f6a859b728336aa094bbbb3_hd.png)

至于自定义功能的话, 交给网上的其他大牛来完成吧233333, 这里强烈推荐 [GitHub - spf13/spf13-vim: The ultimate vim distribution](https://link.zhihu.com/?target=https%3A//github.com/spf13/spf13-vim) . 效果如下,  比以往的效果好了太多:

![img](https://pic2.zhimg.com/v2-6c956d1d0b67241bf2733acc2281ac09_b.png)![img](https://pic2.zhimg.com/80/v2-6c956d1d0b67241bf2733acc2281ac09_hd.png)

**《计算机网络: 自顶向下方法》**

软件学院的计算机网络教材, 里面将计算机网络从顶层到底层逐章分析了一遍, 非常适合初学者阅读, 不过最好能够结合一些实验来辅助理解, 因为里面的讲解确实蛮抽象的

**《图解HTTP》**

日本人著的介绍HTTP协议的书, 对理解HTTP协议的一些细节有非常大的帮助, 插画也很多(日本人的行文风格, 感觉就像看漫画一样), 很容易就理解了

**《TCP/IP详解卷一》**

觉得上面两本讲解网络的还不够深入?我很钦佩这本书的作者, 能把枯燥的知识讲得那么那么那么细致, 非常强烈推荐这一本, 看完相应章节后大概能够明白为什么TCP/IP要这么设计了. 面试的时候经常问到三次握手和四次挥手, 还有各种状态的转移, TIME_WAIT的时间为什么是2*MSL...

**《UNIX网络编程卷一: 套接字联网API(第三版)》**

中文版快800页, 不过我只看了一些章节, 这本书也是把TCP/IP的细节讲得很深很深, 此外还有非常重要的基本套接字编程, 就是写网络程序的时候那些bind, accept, listen, send, receive函数之类的, 内容非常多, 但是这些是理解多路复用模型所需要掌握的...select/poll/epoll这些系统调用解决了什么问题? 事件机制能不能理解? 就看这本书的前六章了

**《数据库管理系统(原理与设计)》**

这个也是web开发中离不开的东西, 必须划重点学会的是ER图/SQL语句/存储数据(磁盘|文件|RAID|缓冲池等)/三大范式/索引以及相应的数据结构/事务相关的所有概念, 尤其重点学习SQL, 附上学习[链接](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/122) .之后学会使用mysql workbench来进行数据库建模/逆向工程生成建表语句/根据SQL生成JAVA实体类等就不赘述了, 开发过程中百度谷歌一下就知道啦, 然后如果习惯在windows下开发的同学推荐利用navicat这个好东西, 当然如果说想训练自己写sql语句的能力那纯粹用命令行也无所谓...

**html/css/javascript**

这个是做web开发基础中的基础, 个人感觉学习难度不大, 但是建议一口气学完并尝试利用它们做一个简单的个人简历, 否则学习曲线会比较断断续续, 不够一气呵成, 具体教程可以参考 [html/css](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/9)[javascript入门](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/36)[javascript进阶](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/10)[Javascript深入浅出](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/277)

**emmet cheat sheet**

做前端的应该都知道这个玩意的了，简单地说就是能让开发更加方便吧，解放生产力的一个工具，至少大于号和小于号不用再按来按去了...

比如说要打出下面的代码测试列表效果之类的：

![img](https://pic3.zhimg.com/v2-4d25f8b4a6e81694a562622fbd6bcb42_b.png)![img](https://pic3.zhimg.com/80/v2-4d25f8b4a6e81694a562622fbd6bcb42_hd.png)

借助emmet插件只需要这么写：

.list-block.list-block-search.searchbar-found>ul>(li.item-content>.item-inner>.item-title{$})*6

然后按tab键就能生成了....

更多的用法详情请见：[Cheat Sheet](https://link.zhihu.com/?target=http%3A//docs.emmet.io/cheat-sheet/)

**jQuery/Bootstrap/AngularJs**

关于javascript, 其实纯粹使用原生javascript开发网站的话很麻烦, 所以就萌生出了这么一些框架: jQuery解决了浏览器兼容性的问题(虽然据说要过时了), Bootstrap可以很无脑地开发出看起来蛮好看的网页(其实还有很重要方面是响应式前端); 而AngularJS主要是有一个很重要的思想:MVVM, 随后崛起的vue.js也如出一辙而上手更快...对于Java Web开发程序员而言建议刚开始有选择性地进行学习, 可能真正涉猎深了就可以随意转换了. ps: 我的前端基本都是在[慕课网](https://link.zhihu.com/?target=http%3A//www.imooc.com/)学成的, 我觉得这个网站真的业界良心. 去官网搜索吧!

**JS的原型链与闭包**

可能利用上面框架开发的时候会遇到一些很棘手的问题不知道如何解决或理解, 那么建议先看看王福朋的博客: [《深入理解javascript原型和闭包系列》](https://link.zhihu.com/?target=http%3A//www.cnblogs.com/wangfupeng1988/p/4001284.html), 我觉得看完理解了以后定位js的bug应该会更容易一些

**SublimeText**

一个对于前端开发工程师来说非常强大的编辑器, 可以提供各种各样的自动补全(必会emmet插件语法), 附上教程 [快乐的sublime编辑器](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/333) 和 [前端开发工具技巧介绍—Sublime篇](https://link.zhihu.com/?target=http%3A//www.imooc.com/learn/40). 自从用了SublimeText, 我再也不畏惧前端编程了...如果觉得教程看得不过瘾, 知乎上这几个帖子可以让你领略SublimeText的[奇淫技巧](https://www.zhihu.com/question/24896283?rf=19976788)

**WebStorm**

Jetbrain旗下的[前端IDE](https://link.zhihu.com/?target=https%3A//www.jetbrains.com/webstorm/), 我觉得相比SublimeText它更好的一点就是不需要你自己去装插件就已经有了很多方便的功能, 上手比SublimeText快

**VSCODE**

微软开源的编辑器, 知乎上也有比较详细的[讨论](https://www.zhihu.com/question/29984607). 个人认为好看/高雅/好用, 如果习惯SublimeText入手vscode并不难, 反正我现在已经转战VSCODE了, 就因为它可以设置背景为萌萝莉喔, 是不是蛮可爱的, 感觉打代码更有精神了

![img](https://pic1.zhimg.com/v2-32263c2dfa7936932d7559e2ed2af0e4_b.png)![img](https://pic1.zhimg.com/80/v2-32263c2dfa7936932d7559e2ed2af0e4_hd.png)

**Github**

将[github](https://link.zhihu.com/?target=https%3A//github.com/)列为基础可以足以说明它的重要性, 其实主要是理解版本控制与分支协作的概念, 不过使用这个网站之前得先学git, [廖雪峰的博客](https://link.zhihu.com/?target=http%3A//www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)就介绍得非常到位. 建议刚入门github的时候可以将github当成成云仓库(你甚至可以把东京热的种子放在github里哈哈哈), 熟悉一些基本命令. 到后面团队作业的时候强制使用github进行协同合作, 会渐渐发现和理解github的好处的. 除此之外github还是一个搜索开源优质项目的好网站（下图源于[怎样使用 GitHub？ - GitHub](https://www.zhihu.com/question/20070065)

[@珊姗是个小太阳](http://www.zhihu.com/people/7d4a764b2ad111a075310adbd191af33)

  的答案）



**Markdown**

markdown也是很相见恨晚的东西, 我觉得markdown类似于html, 但是比html更简单简洁, 对于喜欢做网上笔记或者个人博客的同学而言是值得推荐的工具. 半小时应该就能上手了, 教程在此[Markdown——入门指南](https://link.zhihu.com/?target=http%3A//www.jianshu.com/p/1e402922ee32/)...附上vscode的markdown笔记截图(分栏看预览效果, 感觉高大上了呢), 你会爱上markdown的(虽然我个人笔记整理得很乱- - 等我闲下来再好好整理)

![img](https://pic2.zhimg.com/v2-b77aea41ae61c1c13f47601e856ff829_b.png)![img](https://pic2.zhimg.com/80/v2-b77aea41ae61c1c13f47601e856ff829_hd.png)

**二、Java**

**《疯狂Java讲义》**

个人认为Java入门最好的书籍, 虽然真的很厚, 不过里面重复的话会写很多遍(每天坚持看一点, 一次性读完, 印象也会很深刻的)...对于这本书而言, **除了图形界面编程以外, 其他内容最好认真读完并实践一遍**, 会对理解Java Web有不少的帮助

**《Effective Java》**

建议有一定项目经验的人才去看这本书, 里面列举了很多开发必须注意的条目. 说实话这本书真的不那么好看, 但是你会发现在某些笔试题上会出现这本书的很多条目

**Intellij IDEA**

学习Java怎么能不知道有这么个牛逼轰轰的IDE! 可以说彻底提升了我的开发效率, 简直相见恨晚, 在所有教程中我觉得极客学院做得最为认真, 附上学习教程（之前推荐的是极客学院的网址，但是现在那个网址的图片好像都看不了了- -）[https://github.com/judasn/IntelliJ-IDEA-Tutorial](https://link.zhihu.com/?target=https%3A//wx2.qq.com/cgi-bin/mmwebwx-bin/webwxcheckurl%3Frequrl%3Dhttps%253A%252F%252Fgithub.com%252Fjudasn%252FIntelliJ-IDEA-Tutorial%26skey%3D%2540crypt_bd96e8e8_50bc7d4475f251b8024744aaca7feea6%26deviceid%3De650565376945758%26pass_ticket%3Dundefined%26opcode%3D2%26scene%3D1%26username%3D%407a8515bb235d5304528174d528402960f5c3159fe31bc8b13ce3a3185d877d18)

![img](https://pic3.zhimg.com/v2-1b0724b08eeec6d5f42ee99379674f56_b.png)![img](https://pic3.zhimg.com/80/v2-1b0724b08eeec6d5f42ee99379674f56_hd.png)

**Maven**

为什么我把Maven放在这里? 因为它蛮重要的(其实用gradle也可以). 一个解决java web项目开发中所需要的依赖问题, 同样论工具的使用教程, 极客学院最给力, 附上[教程地址](https://link.zhihu.com/?target=http%3A//wiki.jikexueyuan.com/project/maven/pom.html)

**《轻量级Java EE企业应用实战》**

(阅读此书时建议把基础部分的全部搞定,)这本书跟《疯狂Java讲义》是配套的, 里面先讲解Servlet/Jsp(Java Server Page)等技术, 然后就是SSH(Struts/Spring/Hibernate)等后台框架的各种使用, 其实说白了就是把这些技术的官方文档给翻译一遍给你而已. 有人觉得struts2都过时了还有没有学习的必要? 我认为理解一下也不会花太长时间, 因为学习任何框架的重点也不仅仅是用而已. 初学者把重点放在一些思想上的东西(尽管很虚, 但是后续的框架基本都会延续这些思想), 比如控制翻转IoC|面向切面编程AOP|对象关系映射ORM|模型-视图-控制器MVC等, 把这些弄懂后之后的SpringMVC, mybatis等后台框架上手会很快的. 这些东西真的太太太太太太基础了, 搞不懂就要考虑转行了(= . =) 另外看这本书入门Java web的时候不要对作者那种用记事本编程的方式太较真, 会debug到天昏地暗的, 使用Intellij Idea就好了(如果不懂使用Intellij Idea可以先去慕课网或极客学院或csdn等网站看看别人是怎么操作的即可). 还有想说的就是, 请务必学会使用maven, 手工导jar包学习Java Web的方式简直就是弱智行为, 而我居然坚持了三个月..最后, 把这本书当成工具书比较合适: 初次阅读理解就好没必要动手实践, 等到需要的时候再翻阅查询

**《Spring Boot实战》**

还在用xml配置各种依赖注入/数据源/事务管理器? out啦! 随着微应用的流行, 以注解替代xml配置的开发方式将越来越流行. 作者汪云飞没有讲太多废话, 内容基本都是说Spring Boot的某个特性然后举个例子实战, 有利用SSH开发过简单项目的同学可以尝尝先(我去实习的岗位早就开始用了, 觉得蛮新颖的) 实际上学习任何一个框架的时候要先了解这个框架解决了什么问题, 只有理解了这个后才能更有针对性也更有效率地去进行学习

**《Head First 设计模式》**

隆重推荐这本神书, 很有趣. 刚开始读觉得很深, 但是和《轻量级Java EE企业应用实战》一起看, 思考那些设计模式存在的意义, 会对软件架构方面的知识豁然开朗...

**《Java并发编程实践》**

又是个歪果仁写的书, 里面详细介绍了Java并发工具包java.util.concurrent的各种工具以及很多的并发编程实践之道, 是并发编程的入门之作.

**《Java并发编程的艺术》**

国人写的书, 也是写Java并发编程的, 与上一本相比个人认为比较凝练/干净/易懂

**三、Java进阶**

**《架构探险: 从零开始写Java Web框架》**

作者叫黄勇, 一本非常让我拍手叫绝的书....首推! 绝对干货! 如果你不想看spring源码也没有太大关系, 这本书带你一步步地开发出一个类似spring mvc的简单框架, 并且逐步地增加需求和完善, 理解了每个细节对于理解Java Web的开发是很有帮助的

**《深入分析Java Web技术内幕》**

这本书我觉得是必看的, 可以说是对我帮助最大的一本技术书籍, 覆盖了Java Web很多方面的知识, 比如计算机网络|Tomcat结构|Spring架构|SpringMVC原理|模板引擎实现原理等, 绝对很有收货, 足够有深度也很有难度

**《Spring源码深度剖析》**

一本比较详细的书, 其实对于阅读源代码, 跟着作者的思路读, 再结合自己的断点调试进行学习的方式是最好的

**《深入理解Java虚拟机-(JVM高级特性与最佳实践)》**

想知道Java虚拟机为什么会帮你自动收集垃圾而不需要你管理资源释放吗? 想知道Java虚拟机的内存区域是如何划分的吗? 想知道Java虚拟机是如何唯一确定一个Java实现类, 并且如何加载类的吗? 想知道JVM凭什么能够将远程服务器发送的网络字节加载到JVM内存, 从而实现远程过程调用的吗? 这本书你值得拥有!(感谢这本书让我回答出RPC的实现原理)

**500 lines or less**

500行代码! 尽管跟Java没有太大关系, 强烈推荐, 里面的程序主要是一些玩具轮子, 都是用python实现的, 学习这个主要是理解别的程序员在开发软件的时候所做的决策/思路/取舍, 虽然我也没看完, 但是觉得这个资源很赞!!! [官方网址](https://link.zhihu.com/?target=http%3A//aosabook.org/en/index.html)

![img](https://pic3.zhimg.com/v2-ed9fcc9e321b323444a162168e780e0e_b.png)![img](https://pic3.zhimg.com/80/v2-ed9fcc9e321b323444a162168e780e0e_hd.png)

**《MySQL技术内幕-InnoDB存储引擎》**

其实讲道理这种书应该给DBA看才对, 不知道为什么Java Web开发也要看- - 有次面试就问到说对mysql数据库的性能调优有什么看法, 所以就列在上面. 不过说真的有点晦涩难懂, 如果想简单粗暴地应付面试的话就看[这个博客](https://link.zhihu.com/?target=http%3A//blog.codinglabs.org/articles/theory-of-mysql-index.html) 这篇博客应该是我目前看到的对于mysql索引的解释最最通俗易懂的了

**四、分布式系统**

**《大型网站技术架构: 核心原理与案例分析》**

李智慧著, 确实是很好的入门书籍. 实际上如果有操作系统和Web项目后台开发的基础话阅读起来不会特别困难, 主要内容是介绍当今企业应对大型网站高并发请求的种种策略

**《分布式Java应用-基础与实践》**

这本书比较深, 介绍分布式Java应用和相应的应用场景, 什么远程过程调用(RPC)呀, 基于服务的体系架构呀(SOA)等, 同时也讲了很多java比较底层的知识, 最后介绍构建高可用/可伸缩系统的工程经验

**《大型分布式网站架构: 设计与实践》**

与上一本书类似, 不过个人觉得更偏向于"介绍"互联网安全架构和分布式系统的各种组件(比如分布式缓存|消息队列|搜索引擎等), 目前我只接触了Redis, 相应的组件实在太多了- - 此外对系统监控和数据分析等也做了相应介绍, 这些我感觉比较偏向运维岗的工作人员

**《Redis实战》**

Redis的基本用法和在生产环境的应用, 值得拥有, 虽然书籍是用python, 不过[官方github也有放java的源码](https://link.zhihu.com/?target=https%3A//github.com/josiahcarlson/redis-in-action)

**《Redis设计与实现》**

Redis的源码解读, 怎么说呢, 反正比spring源码好读太多了....而且作者读得很用心, 整理出了带注释版本的redis源码, [去读个痛快吧少年](https://link.zhihu.com/?target=https%3A//github.com/huangz1990/redis-3.0-annotated)

**五、博客与网站**

**博客**

这里再着重推荐两个大神的博客, 都是歪果仁

- [Jenkov](https://link.zhihu.com/?target=http%3A//tutorials.jenkov.com/): 丹麦资深Java开发人员, 从事软件开发十多年, 如果你觉得李刚的教材看腻了, 可以看看他写的教程, 非常适合初学者, 发现知乎很多Java程序员都是在这里学并发编程的
- [Baeldung](https://link.zhihu.com/?target=http%3A//www.baeldung.com/): 这系列教程是我觉得讲spring的用法讲得最好最全的.. 尤其是讲spring security讲得真的很详细...

**网站**

此外还有一些常用的学习网站:

- [慕课网](https://link.zhihu.com/?target=http%3A//www.imooc.com/): 首推, 自己真心感谢这个网站
- [实验楼](https://link.zhihu.com/?target=https%3A//www.shiyanlou.com/): 地位和慕课网相当, 让我觉得编程有趣的一个学习网站
- [优达学城](https://link.zhihu.com/?target=https%3A//www.udacity.com/): 一个相见恨晚的网站，里面的视频图文并茂，非常生动好理解，只是可能需要翻墙。另外针对后端有一个课程蛮不错的：[后端入门](https://link.zhihu.com/?target=https%3A//cn.udacity.com/course/intro-to-backend--ud171)。很基础不过讲得很好，适合小白。
- [天码营](https://link.zhihu.com/?target=https%3A//course.tianmaying.com/): 里面有很多Java项目值得临摹
- [掘 金](https://link.zhihu.com/?target=http%3A//gold.xitu.io/): 一个致力于为广大程序员推送文章的app(PC端也能看), 干货不少
- [coursera](https://link.zhihu.com/?target=https%3A//zh.coursera.org/): 这个不多解释了, 如果想跟国际接轨(英语授课)的话这个网站很适合你, 里面有很多全世界优质的课程, 知乎上也有总结得很详细的课程列表, 适用于任何人([Coursera 上有哪些课程值得推荐？ - MOOCs](https://www.zhihu.com/question/22436320))
- [Quora](https://link.zhihu.com/?target=https%3A//www.quora.com/): 知乎的鼻祖, 但是抖机灵的人比较少, 外国牛人也是很多的, 上了才知道

**有一些很好的开源java web项目值得借鉴和参考，多看别人写的代码才发现：这个功能原来可以这么实现，原来有这种api，原来还有这种jar包（第一次知道）...（也希望各位能推荐给我一些好玩的开源java web项目进行学习）**

[jcalaz/jcalaBlog](https://link.zhihu.com/?target=https%3A//github.com/jcalaz/jcalaBlog) 

[spring-io/sagan](https://link.zhihu.com/?target=https%3A//github.com/spring-io/sagan) 

[Raysmond/SpringBlog](https://link.zhihu.com/?target=https%3A//github.com/Raysmond/SpringBlog)