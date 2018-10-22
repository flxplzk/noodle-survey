insert into app_user (first_name,last_name,password,username,id,created_at,updated_at) values ('Bengt-Lasse','Arngt','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','lasse@arndt.de', -1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into app_user (first_name,last_name,password,username,id,created_at,updated_at) values ('Robert','Peters','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','robert@peters.de', -2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into app_user (first_name,last_name,password,username,id,created_at,updated_at) values ('Sascha','Pererva','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','sascha@pererva.de', -3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into app_user (first_name,last_name,password,username,id,created_at,updated_at) values ('Felix','Plazek','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','felix@plazek.de', -4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into app_user (first_name,last_name,password,username,id,created_at,updated_at) values ('Stefan','Reichert','{bcrypt}$2a$10$7TDfja.Wlhyd1/mYnPm.xeL5ofAmCZF4YZ7T7FqerFBb/KA1l3Uym','stefan@reichert.de', -5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Survey Description',NULL,-1,'OPEN','Test Survey Bengt-Lasse OPEN',-6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Test Survey Description',NULL,-1,'CLOSED','Test Survey Bengt-Lasse CLOSED',-7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-6,CURRENT_TIMESTAMP()+100, -8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-6,CURRENT_TIMESTAMP()+100, -9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-6,CURRENT_TIMESTAMP()+100, -10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-7,CURRENT_TIMESTAMP()+100, -11, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-7,CURRENT_TIMESTAMP()+100, -12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-7,CURRENT_TIMESTAMP()+100, -13, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Survey Description',NULL,-2,'OPEN','Test Survey Robert OPEN',-14, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Test Survey Description',NULL,-2,'CLOSED','Test Survey Robert CLOSED',-15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-13,CURRENT_TIMESTAMP()+100, -16, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-13,CURRENT_TIMESTAMP()+100, -17, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-13,CURRENT_TIMESTAMP()+100, -18, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-14,CURRENT_TIMESTAMP()+100, -19, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-14,CURRENT_TIMESTAMP()+100, -20, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-14,CURRENT_TIMESTAMP()+100, -21, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Survey Description',NULL,-3,'OPEN','Test Survey Sascha OPEN',-22, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Test Survey Description',NULL,-3,'CLOSED','Test Sascha CLOSED',-23, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-22,CURRENT_TIMESTAMP()+100, -24, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-22,CURRENT_TIMESTAMP()+100, -25, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-22,CURRENT_TIMESTAMP()+100, -26, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-23,CURRENT_TIMESTAMP()+100, -27, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-23,CURRENT_TIMESTAMP()+100, -28, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-23,CURRENT_TIMESTAMP()+100, -29, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-23,CURRENT_TIMESTAMP()+100, -30, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Survey Description',NULL,-4,'OPEN','Test Survey Felix OPEN',-31, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Test Survey Description',NULL,-4,'CLOSED','Test Survey Felix CLOSED',-32, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-31,CURRENT_TIMESTAMP()+100, -33, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-31,CURRENT_TIMESTAMP()+100, -34, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-31,CURRENT_TIMESTAMP()+100, -35, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-31,CURRENT_TIMESTAMP()+100, -36, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-32,CURRENT_TIMESTAMP()+100, -37, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-32,CURRENT_TIMESTAMP()+100, -38, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-32,CURRENT_TIMESTAMP()+100, -39, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-32,CURRENT_TIMESTAMP()+100, -40, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Survey Description',NULL,-5,'OPEN','Test Survey Stefan OPEN',-41, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into surveys (description,event_id,initiator_id,survey_status,title,id,created_at,updated_at) values ('Test Survey Description',NULL,-5,'CLOSED','Test Survey Stefan CLOSED',-42, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-41,CURRENT_TIMESTAMP()+100, -43, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-41,CURRENT_TIMESTAMP()+100, -44, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-41,CURRENT_TIMESTAMP()+100, -45, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-41,CURRENT_TIMESTAMP()+100, -46, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-42,CURRENT_TIMESTAMP()+100, -47, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-42,CURRENT_TIMESTAMP()+100, -48, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-42,CURRENT_TIMESTAMP()+100, -49, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into options (survey_id, date_time, id, created_at, updated_at) values (-42,CURRENT_TIMESTAMP()+100, -50, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into participations (id, created_at, updated_at, survey_id, user_id) values (-51, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -6, -2);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-52, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -6, -3);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-53, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -7, -4);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-54, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -7, -5);
insert into participations_options (participation_id, options_id) values (-51, -8);
insert into participations_options (participation_id, options_id) values (-52, -9);
insert into participations_options (participation_id, options_id) values (-53, -12);
insert into participations_options (participation_id, options_id) values (-54, -13);

insert into participations (id, created_at, updated_at, survey_id, user_id) values (-55, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -14, -1);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-56, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -14, -3);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-57, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -15, -4);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-58, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -15, -5);
insert into participations_options (participation_id, options_id) values (-55, -8);
insert into participations_options (participation_id, options_id) values (-56, -9);
insert into participations_options (participation_id, options_id) values (-57, -12);
insert into participations_options (participation_id, options_id) values (-58, -13);

insert into participations (id, created_at, updated_at, survey_id, user_id) values (-59, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -22, -1);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-60, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -22, -2);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-61, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -23, -4);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-62, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -23, -5);
insert into participations_options (participation_id, options_id) values (-59, -8);
insert into participations_options (participation_id, options_id) values (-60, -9);
insert into participations_options (participation_id, options_id) values (-61, -12);
insert into participations_options (participation_id, options_id) values (-62, -13);

insert into participations (id, created_at, updated_at, survey_id, user_id) values (-63, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -31, -1);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-64, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -31, -2);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-65, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -32, -3);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-66, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -32, -5);
insert into participations_options (participation_id, options_id) values (-63, -8);
insert into participations_options (participation_id, options_id) values (-64, -9);
insert into participations_options (participation_id, options_id) values (-65, -12);
insert into participations_options (participation_id, options_id) values (-66, -13);

insert into participations (id, created_at, updated_at, survey_id, user_id) values (-67, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -41, -1);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-68, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -41, -2);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-69, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -42, -3);
insert into participations (id, created_at, updated_at, survey_id, user_id) values (-70, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), -42, -4);
insert into participations_options (participation_id, options_id) values (-67, -8);
insert into participations_options (participation_id, options_id) values (-68, -9);
insert into participations_options (participation_id, options_id) values (-69, -12);
insert into participations_options (participation_id, options_id) values (-70, -13);
