-- �Ȳ�ѯһ�����ݿ���û��student������У�ɾ��
select * from student;

-- ɾ��ԭ�����ݿ��student��
drop table student;

-- ���û�У������������

-- ѧ������
create table student 
(
   ID  NUMBER(6) not null,  -- ID����
   NAME  VARCHAR2(12) ,     -- ����
   AGE  NUMBER(3) ,         -- ����
   SCORE  NUMBER(5,1) ,     -- �ɼ�
   REALNAME  VARCHAR2(200) ,-- ͼƬ��ʵ����
   PHOTONAME  VARCHAR2(200) ,-- ͼƬ����������
   PHOTOTYPE  VARCHAR2(200) ,-- ͼƬ����
   constraint PK_EMPLOYEE primary key (ID)
);

-- ����������������
create sequence seq_stu;

-- ������ݿ��
