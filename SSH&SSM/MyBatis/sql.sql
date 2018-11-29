create table tb_user(
	id int(11) primary key auto_increment,
	name varchar(20) default null,
	sex varchar(10) default null,
	age int(10) default null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
CREATE TABLE TB_USER2(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	user_name VARCHAR(20),
	user_sex VARCHAR(20),
	user_age INT
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO TB_USER2(user_name,user_sex,user_age) VALUES ('jack','男',22);
INSERT INTO TB_USER2(user_name,user_sex,user_age) VALUES ('rose','女',18);
INSERT INTO TB_USER2(user_name,user_sex,user_age) VALUES ('tom','男',25);
INSERT INTO TB_USER2(user_name,user_sex,user_age) VALUES ('mary','女',28);

-- 
CREATE TABLE TB_CLAZZ(
	id INT PRIMARY KEY AUTO_INCREMENT,
	CODE VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO TB_CLAZZ(CODE) VALUES ('J001');
INSERT INTO TB_CLAZZ(CODE) VALUES ('J002');
INSERT INTO TB_CLAZZ(CODE) VALUES ('J003');

CREATE TABLE TB_STUDENT(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20),
	age INT,
	sex VARCHAR(20),
	clazz_id INT,
	FOREIGN KEY (clazz_id) REFERENCES TB_CLAZZ(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO TB_STUDENT(NAME,sex,age,claZZ_id) VALUES('jack','男',22,1);
INSERT INTO TB_STUDENT(NAME,sex,age,claZZ_id) VALUES('rose','女',18,1);
INSERT INTO TB_STUDENT(NAME,sex,age,claZZ_id) VALUES('tom','男',25,2);
INSERT INTO TB_STUDENT(NAME,sex,age,claZZ_id) VALUES('mary','女',28,2);

-- 第10章 Mybaits中的关联映射
-- 一对一映射
CREATE TABLE tb_card(
	id INT PRIMARY KEY AUTO_INCREMENT,
	CODE VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_card(CODE) VALUES ('432801198009191038');

CREATE TABLE tb_person(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20),
	sex VARCHAR(20),
	age INT,
	card_id INT UNIQUE,
	FOREIGN KEY(card_id) REFERENCES tb_card(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_person(name,sex,age,card_id) VALUES('jack','男',23,1);

-- 一对多映射
CREATE TABLE tb_clazz(
	id INT PRIMARY KEY AUTO_INCREMENT,
	CODE VARCHAR(20),
	NAME VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_clazz (CODE,NAME) VALUES ('J001','淑芬');

CREATE TABLE tb_student(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20),
	SEX VARCHAR(20),
	AGE INT,
	clazz_id INT,
	FOREIGN KEY (clazz_id) REFERENCES tb_clazz(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_student(NAME,SEX,AGE,clazz_id) VALUES('jack','男',22,1);
INSERT INTO tb_student(NAME,SEX,AGE,clazz_id) VALUES('rose','女',18,1);
INSERT INTO tb_student(NAME,SEX,AGE,clazz_id) VALUES('tom','男',25,2);
INSERT INTO tb_student(NAME,SEX,AGE,clazz_id) VALUES('mary','女',28,2);




-- 多对多映射

#用户表
CREATE TABLE TB_USER(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20),
	loginname VARCHAR(20),
	password VARCHAR(20),
	phone VARCHAR(10),
	address VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_USER(username,loginname,password,phone,address) VALUES('杰克','JACK','123456','13920001616','广州')

#创建商品表
CREATE TABLE TB_ARTICLE(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20),
	price DOUBLE,
	remark VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_ARTICLE(name,price,remark) VALUES('疯狂Java讲义',100.0,'a book');
INSERT INTO TB_ARTICLE(name,price,remark) VALUES('疯狂C++讲义',111.0,'a book');
INSERT INTO TB_ARTICLE(name,price,remark) VALUES('疯狂IOS讲义',122.0,'a book');
INSERT INTO TB_ARTICLE(name,price,remark) VALUES('疯狂Android讲义',130.0,'a book');


#创建订单表
CREATE TABLE TB_ORDER(
	id INT PRIMARY KEY AUTO_INCREMENT,
	code VARCHAR(20),
	total DOUBLE,
	user_id INT,
	FOREIGN KEY (user_id) REFERENCES TB_USER(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_ORDER(code,total,user_id) VALUES('4444448888',300.1,1);
INSERT INTO TB_ORDER(code,total,user_id) VALUES('4414545658',400.1,1);

#创建中间表
CREATE TABLE TB_ITEM(
	order_id INT,
	article_id INT,
	amount INT,
	PRIMARY KEY(order_id,article_id),
	FOREIGN KEY(order_id) REFERENCES TB_ORDER(id),
	FOREIGN KEY(article_id) REFERENCES TB_ARTICLE(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_ITEM(order_id,article_id,amount) VALUES(1,1,1);
INSERT INTO TB_ITEM(order_id,article_id,amount) VALUES(1,2,1);
INSERT INTO TB_ITEM(order_id,article_id,amount) VALUES(1,3,2);
INSERT INTO TB_ITEM(order_id,article_id,amount) VALUES(2,1,1);

-- 动态SQL
CREATE TABLE TB_EMPLOYEE(
	id INT(11) PRIMARY KEY AUTO_INCREMENT,
	loginname VARCHAR(20),
	password VARCHAR(20),
	name VARCHAR(20) DEFAULT NULL,
	sex char(2) DEFAULT NULL,
	age INT(11) DEFAULT NULL,
	phone VARCHAR(20),
	sal DOUBLE,
	state VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_EMPLOYEE(loginname,password,name,sex,age,phone,sal,state) VALUES ('JACK','123456','杰克','男',26,'1390201999',9800,'ACTIVE');
INSERT INTO TB_EMPLOYEE(loginname,password,name,sex,age,phone,sal,state) VALUES ('ROSE','123456','罗斯','女',21,'1390201888',6800,'ACTIVE');
INSERT INTO TB_EMPLOYEE(loginname,password,name,sex,age,phone,sal,state) VALUES ('tom','123456','汤姆','男',27,'1390201777',8800,'ACTIVE');
INSERT INTO TB_EMPLOYEE(loginname,password,name,sex,age,phone,sal,state) VALUES ('alice','123456','阿里斯','女',30,'13901021666',10000,'ACTIVE');

-- MyBatis缓存

create table tb_user(
	id int(11) primary key auto_increment,
	name varchar(20) default null,
	sex varchar(10) default null,
	age int(10) default null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_USER(name,sex,age) VALUES('JAKC','男',20);
INSERT INTO TB_USER(name,sex,age) VALUES('ROSE','女',21);
INSERT INTO TB_USER(name,sex,age) VALUES('TOM','男',22);
INSERT INTO TB_USER(name,sex,age) VALUES('ALICE','女',23);
INSERT INTO TB_USER(name,sex,age) VALUES('MOSE','女',23);


-- MyBatis注解开发

create table tb_user(
	id int(11) primary key auto_increment,
	name varchar(20) default null,
	sex varchar(10) default null,
	age int(10) default null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_USER(name,sex,age) VALUES('JAKC','男',20);
INSERT INTO TB_USER(name,sex,age) VALUES('ROSE','女',21);
INSERT INTO TB_USER(name,sex,age) VALUES('TOM','男',22);
INSERT INTO TB_USER(name,sex,age) VALUES('ALICE','女',23);
INSERT INTO TB_USER(name,sex,age) VALUES('MOSE','女',23);


-- Spring4+MyBatis3整合

USE DATABASE mybatis4;
#创建用户表
CREATE TABLE TB_USER(
	id INT PRIMARY KEY AUTO_INCREMENT,
	loginname VARCHAR(20) UNIQUE, #用户名
	password VARCHAR(20), #密码
	username VARCHAR(20), #用户名
	phone VARCHAR(20), #电话
	address VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_USER (loginname,password,username,phone,address) VALUES('jack','123456','杰克','13920001234','广州中山');

CREATE TABLE TB_BOOK(
	id INT(11) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50),
	author VARCHAR(20),
	publicationdate DATE,
	publication VARCHAR(100),
	price DOUBLE,
	image VARCHAR(20),
	remark VARCHAR(20) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO TB_BOOK (id,name,author,publicationdate,publication,price,image,remark) VALUES (1,'疯狂Java讲义','none','2008-01-01','电子工业出版社',40.0,'java.jpg','我就解放快乐圣诞节爱发科');
INSERT INTO TB_BOOK (id,name,author,publicationdate,publication,price,image,remark) VALUES (2,'疯狂Android讲义','none','2010-01-08','电子工业出版社',50.0,'ee.jpg','家法律的建安费');
INSERT INTO TB_BOOK (id,name,author,publicationdate,publication,price,image,remark) VALUES (3,'疯狂IOS讲义','none','2012-10-01','电子工业出版社',60.0,'andrio.jpg','国家劳动节快乐发');
INSERT INTO TB_BOOK (id,name,author,publicationdate,publication,price,image,remark) VALUES (4,'疯狂C','none','2015-06-12','电子工业出版社',70.0,'c.jpg','华科大姐夫');
















