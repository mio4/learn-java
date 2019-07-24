



### Linux常用命令



#### awk

简单来说awk就是把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分再进行各种分析处理。

```cmd
awk '{pattern + action}' {filenames}
```

##### 1. 显示最近登录的5个用户名

![](pics/awk.png)



`BEGIN`和`END`可以确定开始执行的命令和结束执行的命令

`awk 'BEGIN{print "aaa"} {print $1} file.txt'`

`awk '{print $1} END{print "bbb"} file.txt'`

#### grep

`ps -ef | grep php`

`history | grep root`

##### 1. 查询文件/文件夹下特定的字符串

`grep 'string' fileName`

`grep 'string' dirPath/*`

#### sed

sed命令是一个很强大的文本编辑器，可以对来自文件、以及标准输入的文本进行编辑。

执行时，sed会从文件或者标准输入中读取一行，将其复制到缓冲区，对文本编辑完成之后，读取下一行直到所有的文本行都编辑完毕。

所以sed命令处理时只会改变缓冲区中文本的副本，如果想要直接编辑原文件，可以使用-i选项或者将结果重定向到新的文件中。

`sed [options] commands [inputfile...]`

##### 1. 输出文本的1~5行

`sed -n '1,5 p' test.txt` 

- -p：print打印
- -n：取消默认输出



#### vim

##### 1. 查询某个字符串出现的次数

`:%s/字符串/&/gn`



- df
  - 显示磁盘使用情况
- top
  - top命令是Linux下常用的性能分析工具，能够实时显示系统中各个进程的资源占用状况，类似于Windows的任务管理器。



## Shell

##### 1. 接受传入参数

```shell
#!/bin/bash
echo "执行的文件名：$0"; //第一个参数是文件名
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";
```

##### 2. 变量初始化&赋值&打印

```shell
a="123";
echo $a;
```

##### 3. 判断条件

```shell
#!/bin/bash
str1=liushen;
str2=liuting;
if [ $str1 = $str2 ]
  then 
  	echo equal;
  	echo equal2;
  else 
  	echo not equal;
  	echo not equal2;
fi
```

##### 4. 循环处理

```shell

```

##### 5. 字符串拼接

```shell

```

##### 6. 执行字符串形式的命令

```shell

```





```
1. 接受传入的参数
2. 判断参数，选择需要执行的命令

./run.sh all
./run.sh testFile testMethod
e.g.
./run.sh DomainConfigTest.php testSetAndGetCacheTTL
```











