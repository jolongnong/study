create table freemember(
    id varchar2(100) primary key,
    passwd varchar2(100),
    name varchar2(100),
    jumin1 varchar2(100),
    jumin2 varchar2(100),
    email varchar2(100),
    blog varchar2(100),
    reg_date date
);

create table freeboard(
    num number primary key,
    writer varchar2(100),
    subject varchar2(100),
    email varchar2(100),
    content varchar2(4000),
    passwd varchar2(100),
    reg_date date,
    readcount number default 0,
    ip varchar2(100),
    ref number,
    re_step number,
    re_level number
);

create sequence freeboard_seq nocache;
commit;