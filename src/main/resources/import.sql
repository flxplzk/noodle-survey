insert into app_user (ACCOUNT_NON_EXPIRED,ACCOUNT_NON_LOCKED,CREDENTIALS_NON_EXPIRED,ENABLED,first_name,last_name,password,username,id) values (true,true,true,true,'Admin','Admin','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','admin@admin.de', -1);
insert into survey (creation_date,description,event_id,initiator_id,survey_status,title,id,SURVEY_TYPE) values (CURRENT_DATE ,'Test Survey Description',NULL,-1,'OPEN','Test Survey Title',-2, 'TIME_RANGE');
insert into app_user (ACCOUNT_NON_EXPIRED,ACCOUNT_NON_LOCKED,CREDENTIALS_NON_EXPIRED,ENABLED,first_name,last_name,password,username,id) values (true,true,true,true,'Robert','Admin','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','robert@admin.de', -3);
insert into survey (creation_date,description,event_id,initiator_id,survey_status,title,id,SURVEY_TYPE) values (CURRENT_DATE ,'Test Survey 2 Description',NULL,-3,'OPEN','Test Survey 2 Title',-4, 'DATE_RANGE');
insert into app_user (ACCOUNT_NON_EXPIRED,ACCOUNT_NON_LOCKED,CREDENTIALS_NON_EXPIRED,ENABLED,first_name,last_name,password,username,id) values (true,true,true,true,'Felix','Admin','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','felix@admin.de', -5);
insert into survey (creation_date,description,event_id,initiator_id,survey_status,title,id,SURVEY_TYPE) values (CURRENT_DATE ,'Test Survey 2 Description',NULL,-1,'OPEN','Test Survey 2 Title',-6, 'TIME');
insert into option (from_datetime, survey_id, to_datetime, id) values (CURRENT_TIMESTAMP()+4,-2,CURRENT_TIMESTAMP()+5,-7);
insert into option (from_datetime, survey_id, to_datetime, id) values (CURRENT_TIMESTAMP()+5,-2,CURRENT_TIMESTAMP()+6,-8);
insert into option (from_datetime, survey_id, to_datetime, id) values (CURRENT_TIMESTAMP()+6,-2,CURRENT_TIMESTAMP()+7,-9);
insert into option (from_datetime, survey_id, to_datetime, id) values (CURRENT_DATE +4,-4,CURRENT_DATE+5,-10);
insert into option (from_datetime, survey_id, to_datetime, id) values (CURRENT_DATE+5,-4,CURRENT_DATE+6,-11);
insert into option (from_datetime, survey_id, to_datetime, id) values (CURRENT_DATE+6,-4,CURRENT_DATE+7,-12);
insert into participation (survey_id, user_id, id) values (-2,-1,-13);
insert into participation (survey_id, user_id, id) values (-2,-3,-14);
insert into participation (survey_id, user_id, id) values (-4,-3,-15);