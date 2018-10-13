-- 先查询一下数据库有没有student表，如果有，删掉
select * from student;

-- 删除原先数据库的student表
drop table student;

-- 如果没有，创建表和序列

-- 学生表创建
create table student 
(
   ID  NUMBER(6) not null,  -- ID主键
   NAME  VARCHAR2(12) ,     -- 姓名
   AGE  NUMBER(3) ,         -- 年龄
   SCORE  NUMBER(5,1) ,     -- 成绩
   REALNAME  VARCHAR2(200) ,-- 图片真实名称
   PHOTONAME  VARCHAR2(200) ,-- 图片服务器名称
   PHOTOTYPE  VARCHAR2(200) ,-- 图片类型
   constraint PK_EMPLOYEE primary key (ID)
);

-- 创建主键序列自增
create sequence seq_stu;

-- 清空数据库表
