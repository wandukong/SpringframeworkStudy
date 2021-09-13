create table account (
  ano number primary key,
  owner varchar(20) not null,
  balance number not null
);

create sequence seq_ano 
	minvalue 0 
	start with 0;

insert into account (ano, owner, balance) values (seq_ano.nextval, '홍길동', 1000000);
insert into account (ano, owner, balance) values (seq_ano.nextval, '스프링', 0);
commit;