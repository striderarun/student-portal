CREATE TABLE STUDENT
(
ID INTEGER,
FIRST_NAME VARCHAR(40),
LAST_NAME VARCHAR(40),
GRADE VARCHAR(5),
EMAIL VARCHAR(50),
GUARDIAN_NAME VARCHAR(40),
AGE INTEGER,
BIRTH_DATE TIMESTAMP(6),
PRIMARY KEY (ID)
);

insert into Student values(1, 'Arun', 'Mohan', '10', 'arunmohan@gmail.com','ajay','16','1990-01-27 00:00:00');
insert into Student values(2, 'Ayswarya', 'S', '10', 'aisu@gmail.com','ajay','15','1991-01-27 00:00:00');