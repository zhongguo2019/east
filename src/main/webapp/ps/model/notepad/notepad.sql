-----------------------------------------------------
-- Export file for user SJXT                       --
-- Created by Administrator on 2011/9/14, 10:03:24 --
-----------------------------------------------------

spool notepad.log

prompt
prompt Creating table NOTE_PAD
prompt =======================
prompt
create table NOTE_PAD
(
  PKID        NUMBER(16),
  TITLE       VARCHAR2(200),
  CONTENT     VARCHAR2(1000),
  START_DATE  DATE,
  END_DATE    DATE,
  TYPE        NUMBER(1),
  USER_ID     VARCHAR2(10),
  STATUS      NUMBER(1),
  CREATE_TIME DATE,
  EDIT_TIME   DATE,
  EXT_A       NUMBER,
  EXT_B       VARCHAR2(128),
  NOTE_DATE   DATE
)
;
comment on column NOTE_PAD.PKID
  is '���к�';
comment on column NOTE_PAD.TITLE
  is '����';
comment on column NOTE_PAD.CONTENT
  is '����';
comment on column NOTE_PAD.START_DATE
  is '��ʼʱ�� ����ǰ�����д����ֶ�';
comment on column NOTE_PAD.END_DATE
  is '����ʱ�� ����ǰ�����д����ֶ�';
comment on column NOTE_PAD.TYPE
  is '���ͣ�0������¼��1����';
comment on column NOTE_PAD.USER_ID
  is '�û����';
comment on column NOTE_PAD.STATUS
  is '״̬��0����1ȡ��2�������9ɾ����';
comment on column NOTE_PAD.CREATE_TIME
  is '��дʱ��';
comment on column NOTE_PAD.EDIT_TIME
  is '�༭ʱ��';
comment on column NOTE_PAD.EXT_A
  is '�����ֶ�';
comment on column NOTE_PAD.EXT_B
  is '�����ֶ�';
comment on column NOTE_PAD.NOTE_DATE
  is '�ʼ����� ����Ǳ�����д����ֶΡ�';
create unique index NOTEPADPKID on NOTE_PAD (PKID);
create index N_ENDRTDATE on NOTE_PAD (END_DATE);
create index N_STARTDATE on NOTE_PAD (START_DATE);
create index N_USERID on NOTE_PAD (STATUS);

prompt
prompt Creating sequence SEQ_NOTEPAD
prompt =============================
prompt
create sequence SEQ_NOTEPAD
minvalue 1
maxvalue 999999999
start with 61
increment by 1
cache 20;


spool off
