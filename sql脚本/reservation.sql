use library_reservation;

create table my_date (
    date timestamp not null comment '为了与预约表左连接',
    primary key(date)
) comment '时间表' ;

insert into my_date values('2018-01-01 08:00:00');
insert into my_date values('2018-01-01 09:00:00');
insert into my_date values('2018-01-01 10:00:00');
insert into my_date values('2018-01-01 11:00:00');
insert into my_date values('2018-01-01 12:00:00');
insert into my_date values('2018-01-01 13:00:00');
insert into my_date values('2018-01-01 14:00:00');
insert into my_date values('2018-01-01 15:00:00');
insert into my_date values('2018-01-01 16:00:00');
insert into my_date values('2018-01-01 17:00:00');
insert into my_date values('2018-01-01 18:00:00');
insert into my_date values('2018-01-01 19:00:00');
insert into my_date values('2018-01-01 20:00:00');
insert into my_date values('2018-01-01 21:00:00');
insert into my_date values('2018-01-01 22:00:00');

create table my_week (
    id tinyint not null comment '0-6表示周日到周六',
    week varchar(32) not null comment '为了与预约表左连接',
    primary KEY(id)
) comment '星期表' ;

insert into my_week values(1,'周一');
insert into my_week values(2,'周二');
insert into my_week values(3,'周三');
insert into my_week values(4,'周四');
insert into my_week values(5,'周五');
insert into my_week values(6,'周六');
insert into my_week values(0,'周日');

create table college (
    college_id int not null comment '学院编号',
    college_name varchar(32) not null comment '学院名称',
    primary key(college_id)
) comment '学院表';

insert into college(college_id,college_name) values(1001,'计算机科学与通信工程学院');
insert into college(college_id,college_name) values(1002,'机械工程学院');
insert into college(college_id,college_name) values(1003,'能源与动力工程学院');
insert into college(college_id,college_name) values(1004,'材料科学与工程学院');
insert into college(college_id,college_name) values(1005,'电气信息工程学院');
insert into college(college_id,college_name) values(1006,'食品与生物工程学院');
insert into college(college_id,college_name) values(1007,'环境与安全工程学院');
insert into college(college_id,college_name) values(1008,'理学院');
insert into college(college_id,college_name) values(1009,'土木工程与力学学院');
insert into college(college_id,college_name) values(1010,'化学化工学院');
insert into college(college_id,college_name) values(1011,'管理学院');
insert into college(college_id,college_name) values(1012,'财经学院');
insert into college(college_id,college_name) values(1013,'马克思主义学院');
insert into college(college_id,college_name) values(1014,'文法学院');
insert into college(college_id,college_name) values(1015,'外国语学院');
insert into college(college_id,college_name) values(1016,'艺术学院');
insert into college(college_id,college_name) values(1017,'教师教育学院');
insert into college(college_id,college_name) values(1018,'临床医学院');
insert into college(college_id,college_name) values(1019,'基础医学与医学技术学院');
insert into college(college_id,college_name) values(1020,'药学院');
insert into college(college_id,college_name) values(1021,'海外教育学院');
insert into college(college_id,college_name) values(1022,'京江学院');
insert into college(college_id,college_name) values(1023,'继续教育学院');
insert into college(college_id,college_name) values(1024,'体育部');

create table degree (
    degree_id int not null comment '学位编号',
    degree_name varchar(32) not null comment '学位名称',
    primary key(degree_id)
) comment '学位表' ;

insert into degree(degree_id,degree_name) values(11,'本科生');
insert into degree(degree_id,degree_name) values(12,'研究生');
insert into degree(degree_id,degree_name) values(13,'博士生');
insert into degree(degree_id,degree_name) values(14,'教师');


create table user (
    user_id varchar(32) not null comment '读者编号',
    user_name varchar(32) not null comment '读者姓名',
    user_password varchar(32) not null comment '登录密码',
    college_id int default '1001' comment '学院编号，默认计算机学院',
    degree_id int default '11' comment '身份编号，默认本科生',
    phone varchar(32) comment '号码',
    email varchar(32) comment '邮箱',
    primary key(user_id),
    foreign key(college_id) references college(college_id) on delete set null,
    foreign key(degree_id) references degree(degree_id) on delete set null
) comment '用户表';

insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602019','朱宇','123456',1001,11);
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602020','赵胜杰','123456',1001,11);
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602021','张腾','123456',1001,11);
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602022','周怡涛','123456',1001,11);

insert into user(user_id,user_name,user_password,college_id,degree_id,phone,email) values('3140602023','陆健','123456',1001,11,'18852852189','1370256381@qq.com');
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602024','张家辉','123456',1001,11);
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602025','朱俊豪','123456',1001,11);
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602026','王润','123456',1001,11);

insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602027','张三','123456',1002,11);    
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602028','李四','123456',1003,11);    
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602029','王五','123456',1004,11);    
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602030','陈六','123456',1005,11);    
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602031','阿七','123456',1006,11);    
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3140602032','陆八','123456',1007,11);   


insert into user(user_id,user_name,user_password,college_id,degree_id) values('3130602032','老九','123456',1008,12);    
insert into user(user_id,user_name,user_password,college_id,degree_id) values('3130602033','小十','123456',1009,13);


create table user_info (
    user_id varchar(32) not null comment '读者编号',
    reserve_status tinyint(2) default '0' comment '预约状态',
    sign_in_status tinyint(2) default '0' comment '签到状态',
    sign_out_status tinyint(2) default '0' comment '签离状态',
    primary key(user_id)
) comment '读者信息表' ;
 
create table administrator (
    admin_id varchar(32) not null comment '管理员编号',
    admin_name varchar(64) not null comment '管理员姓名',
    admin_account varchar(64) not null comment '管理员账号',
    admin_password varchar(64) not null comment '管理员密码',
    primary key(admin_id)
) comment '管理员表' ; 

create table floor (
    floor_id varchar(32) not null comment '楼层编号',
    floor_name varchar(64) not null comment '楼层名称',
    primary key(floor_id)
) comment '楼层表' ; 

insert into floor(floor_id, floor_name) values('1','新图书馆一楼');
insert into floor(floor_id, floor_name) values('2','新图书馆二楼');
insert into floor(floor_id, floor_name) values('3','新图书馆三楼');
insert into floor(floor_id, floor_name) values('4','新图书馆四楼');
insert into floor(floor_id, floor_name) values('5','新图书馆五楼');
insert into floor(floor_id, floor_name) values('6','新图书馆六楼');

create table reading_room (
    reading_room_id varchar(32) not null comment '阅览室编号',
    reading_room_name varchar(64) not null comment '阅览室名称',
    floor_id varchar(32) comment '阅览室所属楼层',
    seat_count int not null comment '座位数量',
    primary key(reading_room_id),
    foreign key(floor_id) references floor(floor_id) on delete set null
) comment '阅览室表' ;

ALTER TABLE reading_room ALTER COLUMN seat_count set DEFAULT '0';

insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('1-01','新书借阅','1',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('1-02','报刊借阅','1',100);

insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('2-01','自然科学图书借阅(A区)','2',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('2-02','自然科学图书借阅(B区)','2',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('2-03','电子阅览','2',100);

insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('3-01','社会科学图书借阅(A区)','3',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('3-02','社会科学图书借阅(B区)','3',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('3-03','多媒体阅览','3',100);

insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('4-01','社会科学图书借阅(C区)','4',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('4-02','外文图书借阅','4',100);

insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('5-01','学术期刊阅览','5',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('5-02','工科特色文献阅览','5',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('5-03','江大文库','5',100);

insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('6-01','工具书阅览','6',100);
insert into reading_room(reading_room_id, reading_room_name, floor_id, seat_count) values('6-02','古籍阅览','6',100);


create table seat_status (
    seat_status tinyint(2) not null comment '座位使用状态',
    seat_status_name varchar(32) comment '座位使用状态描述',
    primary key(seat_status)
) comment '座位状态表' ;

insert into seat_status values(0,'空闲');
insert into seat_status values(1,'被预约');
insert into seat_status values(2,'在用');
insert into seat_status values(3,'暂离');
insert into seat_status values(4,'不可用');

create table seat (
    seat_id varchar(32) not null comment '座位编号',
    seat_status tinyint(2) default '0' comment '使用状态',
    reading_room_id varchar(32) comment '座位所属阅览室',
    primary key(seat_id),
    foreign key(reading_room_id) references reading_room(reading_room_id) on delete set null
) comment '座位表' ;

insert into seat(seat_id, reading_room_id) values('1-01-001','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-002','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-003','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-004','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-005','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-006','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-007','1-01');
insert into seat(seat_id, reading_room_id) values('1-01-008','1-01');
update reading_room set seat_count = 8 where reading_room_id = '1-01';
--insert into seat(seat_id, reading_room_id) values('101-1001-10002','101-1001');
--insert into seat(seat_id, reading_room_id) values('101-1001-10003','101-1002');
--insert into seat(seat_id, reading_room_id) values('101-1001-10004','101-1002');
--insert into seat(seat_id, reading_room_id) values('101-1001-10005','101-1004');
--insert into seat(seat_id, reading_room_id) values('101-1001-10006','101-1004');
--insert into seat(seat_id, reading_room_id) values('101-1001-10007','101-1005');
--insert into seat(seat_id, reading_room_id) values('101-1001-10008','101-1005');

create table reservation (
    id int not null auto_increment comment '自增主键',
    user_id varchar(32) not null comment '读者编号',
    seat_id varchar(32) not null comment '座位编号',
    reserve_time timestamp not null default current_timestamp comment '预约时间',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key(id)
) comment '预约表' ;

insert into reservation(user_id, seat_id) values('3140602023','101-1001-10001');

create table sign_in (
    id int not null auto_increment comment '自增主键',
    user_id varchar(32) not null comment '读者编号',
    seat_id varchar(32) not null comment '座位编号',
    reserve_time timestamp comment '预约时间',
    sign_in_time timestamp not null default current_timestamp comment '签到时间',
    sign_in_type tinyint(2) comment '签到类别',
    primary key(id),
    foreign key(sign_in_type) references sign_in_type(sign_in_type_id)
) comment '签到表' ;

create table sign_in_type (
    sign_in_type_id tinyint(2) comment '签到类别',
    sign_in_type_name varchar(32) comment '类别名称',
    primary key(sign_in_type_id)
) comment '签到类别表' ;

insert into sign_in_type values(0,"未签到") ;
insert into sign_in_type values(1,"首次签到") ;
insert into sign_in_type values(2,"当天临时签离后签到") ;


insert into sign_in(user_id, seat_id, reserve_time, sign_in_time, sign_in_type) values('3140602023','101-1001-10001','2017-12-26 10:11:09','2017-12-26 10:12:12',1);

create table sign_out (
    id int not null auto_increment comment '自增主键',
    user_id varchar(32) not null comment '读者编号',
    seat_id varchar(32) not null comment '座位编号',
    reserve_time timestamp comment '预约时间',
    sign_out_type tinyint(2) comment '签离类别',
    sign_out_time timestamp not null default current_timestamp comment '签离时间',
    primary key(id)
) comment '签离表' ;

create table sign_out_type (
    sign_out_type_id tinyint(2) comment '签离类别',
    sign_out_type_name varchar(32) comment '类别名称',
    primary key(sign_out_type_id)
) comment '签离类别' ;

insert into sign_out_type values(0,'未签离');
insert into sign_out_type values(1,'临时签离');
insert into sign_out_type values(2,'终了签离');

insert into sign_out(user_id, seat_id, reserve_time, sign_out_type, sign_out_time) values('3140602023', '101-1001-10001','2017-12-26 10:11:09',1,'2017-12-26 11:11:11');

create table inobservance_type (
    inobservance_type_id int not null comment '违规类别编号',
    inobservance_type_name varchar(32) not null comment '违规类别名称',
    primary key(inobservance_type_id)
) comment '违规类别表' ;

insert into inobservance_type(inobservance_type_id, inobservance_type_name) values(1,'未签到');
insert into inobservance_type(inobservance_type_id, inobservance_type_name) values(2,'未签离');
insert into inobservance_type(inobservance_type_id, inobservance_type_name) values(3,'临时离开未归');
insert into inobservance_type(inobservance_type_id, inobservance_type_name) values(4,'临时离开未签离');



create table inobservance (
    id int not null auto_increment comment '自增主键',
    user_id varchar(32) not null comment '读者编号',
    seat_id varchar(32) not null comment '座位编号',
    reserve_time timestamp comment '预约时间',
    inobservance_type_id int not null comment '违规类别',
    create_time timestamp not null default current_timestamp comment '生成记录时间',
    primary key(id)
) comment '违规记录表' ;

insert into inobservance(user_id, seat_id, reserve_time, inobservance_type_id, create_time) values('3140602023','101-1001-10001','2017-12-26 10:11:09',3,'2017-12-26 12:12:12');

create table blacklist (
    id int not null auto_increment comment '自增主键',
    user_id varchar(32) not null comment '读者编号',
    inobservance_type_id int not null comment '违规类别',
    count int not null default '1' comment '违规次数',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key(id)
) comment '黑名单表' ;

insert into blacklist(user_id, inobservance_type_id, count,create_time) values('3140602023',3,1,'2017-12-26 12:12:12');

