prompt PL/SQL Developer import file
prompt Created on 2011年9月14日 by Administrator
set feedback off
set define off
prompt Disabling triggers for NOTE_PAD...
alter table NOTE_PAD disable all triggers;
prompt Deleting NOTE_PAD...
delete from NOTE_PAD;
commit;
prompt Loading NOTE_PAD...
insert into NOTE_PAD (PKID, TITLE, CONTENT, START_DATE, END_DATE, TYPE, USER_ID, STATUS, CREATE_TIME, EDIT_TIME, EXT_A, EXT_B, NOTE_DATE)
values (55, '无标题', '出去3', null, null, 0, 'admin', 1, to_date('14-09-2011 09:44:06', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-09-2011 09:44:06', 'dd-mm-yyyy hh24:mi:ss'), null, null, to_date('13-09-2011 17:26:27', 'dd-mm-yyyy hh24:mi:ss'));
insert into NOTE_PAD (PKID, TITLE, CONTENT, START_DATE, END_DATE, TYPE, USER_ID, STATUS, CREATE_TIME, EDIT_TIME, EXT_A, EXT_B, NOTE_DATE)
values (54, '无标题', '明天放假出去玩', null, null, 0, 'admin', 1, to_date('13-09-2011 17:12:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2011 17:12:17', 'dd-mm-yyyy hh24:mi:ss'), null, null, to_date('13-09-2011 17:12:05', 'dd-mm-yyyy hh24:mi:ss'));
insert into NOTE_PAD (PKID, TITLE, CONTENT, START_DATE, END_DATE, TYPE, USER_ID, STATUS, CREATE_TIME, EDIT_TIME, EXT_A, EXT_B, NOTE_DATE)
values (51, '无标题', '123', null, null, 0, 'admin', 1, to_date('13-09-2011 16:52:57', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2011 16:52:57', 'dd-mm-yyyy hh24:mi:ss'), null, null, to_date('13-09-2011 16:52:48', 'dd-mm-yyyy hh24:mi:ss'));
insert into NOTE_PAD (PKID, TITLE, CONTENT, START_DATE, END_DATE, TYPE, USER_ID, STATUS, CREATE_TIME, EDIT_TIME, EXT_A, EXT_B, NOTE_DATE)
values (50, '无标题', '1232323', to_date('21-09-2011', 'dd-mm-yyyy'), to_date('12-09-2011', 'dd-mm-yyyy'), 1, 'admin', 2, to_date('13-09-2011 16:51:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-09-2011 16:51:09', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
commit;
prompt 4 records loaded
prompt Enabling triggers for NOTE_PAD...
alter table NOTE_PAD enable all triggers;
set feedback on
set define on
prompt Done.
