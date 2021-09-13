create table board (
  bno number primary key,
  btitle varchar(200) not null,
  bcontent varchar(4000) not null,
  bdate timestamp not null,
  mid varchar(20) references member(mid)
);

create sequence seq_bno 
	minvalue 0 
	start with 0;

insert into board (bno, btitle, bcontent, bdate, mid) 
values (seq_bno.nextval, 'Spring', 'Spring을 이용한 MVC 웹 애플리케이션', sysdate, 'user');
commit;

