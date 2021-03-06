
--TYPES


CREATE TYPE  day_type AS ENUM
    ('work', 'weekend', 'holiday', 'preholiday');

--TABLES

CREATE TABLE IF NOT EXISTS divisions
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name text COLLATE pg_catalog."default",
    parrent bigint,
    depth_level integer DEFAULT 1,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    left_margin integer,
    right_margin integer,
    CONSTRAINT divisions_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS   positions
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name text COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT positions_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    login text COLLATE pg_catalog."default" NOT NULL,
    token text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    fullname text COLLATE pg_catalog."default",
    shortname text COLLATE pg_catalog."default",
    birthday date,
    "position" bigint,
    division bigint,
    image text COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    male character varying(7) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT division_id FOREIGN KEY (division)
        REFERENCES divisions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT position_id FOREIGN KEY ("position")
        REFERENCES positions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE INDEX IF NOT EXISTS fki_division_id
    ON  users USING btree
    (division ASC NULLS LAST);
CREATE INDEX IF NOT EXISTS fki_division_id
    ON  users USING btree
    (division ASC NULLS LAST);

CREATE TABLE IF NOT EXISTS  roles
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name text COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  grants
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT grants_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  user_roles
(
    user_id bigint NOT NULL,
    user_role bigint NOT NULL,
    CONSTRAINT user_role PRIMARY KEY (user_id, user_role),
    CONSTRAINT role_id FOREIGN KEY (user_role)
        REFERENCES  roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES  users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE INDEX IF NOT EXISTS fki_role_id
    ON  user_roles USING btree
    (user_role ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS fki_user_id
    ON  user_roles USING btree
    (user_id ASC NULLS LAST);

CREATE TABLE IF NOT EXISTS  roles_grants
(
    role_id bigint NOT NULL,
    grant_id bigint NOT NULL,
    CONSTRAINT roles_grants_pkey PRIMARY KEY (role_id, grant_id),
    CONSTRAINT grant_id FOREIGN KEY (grant_id)
        REFERENCES  grants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT grentrole_id FOREIGN KEY (role_id)
        REFERENCES  roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE INDEX IF NOT EXISTS fki_grant_id
    ON  roles_grants USING btree
    (grant_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS fki_grentrole_id
    ON  roles_grants USING btree
    (role_id ASC NULLS LAST);

CREATE TABLE IF NOT EXISTS  users_groups
    (
        id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
        name text COLLATE pg_catalog."default" NOT NULL,
        created_at timestamp without time zone,
        updated_at timestamp without time zone,
        CONSTRAINT users_group_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS  users_in_groups
(
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    CONSTRAINT fkg_group_id FOREIGN KEY (group_id)
        REFERENCES  users_groups (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT fkg_user_id FOREIGN KEY (user_id)
        REFERENCES  users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);


CREATE TABLE IF NOT EXISTS address_types
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT address_types_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS contacts
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    representation text COLLATE pg_catalog."default",
    comment text COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    address_type bigint,
    CONSTRAINT contacts_pkey PRIMARY KEY (id),
    CONSTRAINT fkat_address_types FOREIGN KEY (address_type)
        REFERENCES address_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

CREATE INDEX IF NOT EXISTS fki_fkat_address_types
    ON contacts USING btree
    (address_type ASC NULLS LAST);

CREATE TABLE IF NOT EXISTS contacts_owners
(
    user_id bigint NOT NULL,
    contact_id bigint NOT NULL,
    CONSTRAINT fko_contactt_id FOREIGN KEY (contact_id)
        REFERENCES contacts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT fko_user_id FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

CREATE TABLE IF NOT EXISTS   resultsref
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    representation text COLLATE pg_catalog."default",
    CONSTRAINT resultsref_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  work_shedules
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT work_shedule_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  shedules_intervals
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    date date NOT NULL,
    date_type  day_type NOT NULL,
    starttime timestamp without time zone,
    duration interval,
    shedule bigint NOT NULL,
    CONSTRAINT shedules_intervals_pkey PRIMARY KEY (id),
    CONSTRAINT fks_shedule_id FOREIGN KEY (shedule)
        REFERENCES  work_shedules (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

CREATE TABLE IF NOT EXISTS tasks
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    shortdescribe text COLLATE pg_catalog."default" NOT NULL,
    fulldescribe text COLLATE pg_catalog."default",
    author bigint NOT NULL,
    repeatable boolean,
    repeat_period interval,
    inition_date date,
    inition_time time without time zone,
    active boolean,
    duration_of_execute interval NOT NULL,
    common boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT tasks_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS   task_files
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    task bigint NOT NULL,
    representation text COLLATE pg_catalog."default",
    path text COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT task_files_pkey PRIMARY KEY (id),
    CONSTRAINT fkf_task_id FOREIGN KEY (task)
        REFERENCES   tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS task_executors
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    user_id bigint,
    task bigint NOT NULL,
    textresult text COLLATE pg_catalog."default",
    result bigint,
    inprogress boolean,
    done boolean,
    donetime timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT task_executors_pkey PRIMARY KEY (id),
    CONSTRAINT fkt_task_id FOREIGN KEY (task)
        REFERENCES tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fkt_user_id FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS events
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    shortdescribe text COLLATE pg_catalog."default" NOT NULL,
    fulldescribe text COLLATE pg_catalog."default",
    inition_date date NOT NULL,
    inition_time time without time zone NOT NULL,
    repeatable boolean,
    repeat_period interval,
    author bigint NOT NULL,
    duration_of_execute interval,
    common boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT events_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS   event_participants
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    event bigint NOT NULL,
    participant bigint,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT events_detail_pkey PRIMARY KEY (id),
    CONSTRAINT fke_event_id FOREIGN KEY (event)
        REFERENCES   events (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT fke_participant_id FOREIGN KEY (participant)
        REFERENCES   users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

INSERT INTO  address_types (name) OVERRIDING SYSTEM VALUE VALUES ('?????????? ????????????????');
INSERT INTO  address_types (name) OVERRIDING SYSTEM VALUE VALUES ('Email');
INSERT INTO  address_types (name) OVERRIDING SYSTEM VALUE VALUES ('??????????');


INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (1, '??????????????????????????', NULL, 1, 1, 2);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (2, '?????????????????? 1', 1, 2, 3, 8);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (3, '?????????????????? 2', 1, 2, 9, 16);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (4, '?????????????????? 3', 1, 2, 17, 20);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (5, '?????????????? 1_1', 2, 3, 4, 5);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (6, '?????????????? 1_2', 2, 3, 6, 7);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (7, '?????????????? 2_1', 3, 3, 10, 11);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (8, '?????????????? 2_2', 3, 3, 12, 13);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (9, '?????????????? 2_3', 3, 3, 14, 15);
INSERT INTO  divisions (id, name, parrent, depth_level, left_margin, right_margin) OVERRIDING SYSTEM VALUE VALUES (10, '?????????????? 3_1', 4, 3, 18, 19);


INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ('task_read');--1
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'task_edit');--2
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'task_add');--3
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'task_del');--4
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_read');--5
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_add');--6
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_edit');--7
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_del');--8
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_read');--9
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_add');--10
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_edit');--11
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_del');--12
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'role_manage');--13
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_group_read');--14
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_group_add');--15
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_group_edit');--16
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'shedule_read');--17
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'shedule_manage');--18
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'common_task_read');--19
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'common_task_edit');--22
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'common_task_add');--23
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'common_task_del');--24
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'all_task_read');--25
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'personally_task_edit');--26
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'personally_task_add');--27
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'personally_task_del');--28


INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ('??????????????????????????', NULL, NULL);
INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( '????????????????', NULL, NULL);
INSERT INTO  positions ( name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( '??????????', NULL, NULL);
INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( '?????????????????? ??????????????', NULL, NULL);
INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( '??????????????????????????', NULL, NULL);

INSERT INTO  users (login, token, email, fullname, shortname, birthday, male, "position", division, image) VALUES ('??????????????????????????', '$2y$10$63qSE5z1PJRcO7zpaaJ/8.vWYf0qNNgJTUetLHr4VYJnDAjHEPcdS', 'test@test.ru', '??????????????????????????', '???????????? ??.??', '1979-12-12', 'male', 1, 1, NULL);
INSERT INTO  users (login, token, email, fullname, shortname, birthday, male, "position", division, image) VALUES ('????????????????????????', '$2y$10$Wb389Ztb1HaoPGPGyJUtx.yFgNA9Q3BO9KxfAPf/DHbHzd6fuObHW', 'test@test.ru', '????????????????????????', '???????????????? ??.??', '1980-09-21', 'female', 3, 2, NULL);
INSERT INTO  users (login, token, email, fullname, shortname, birthday, male, "position", division, image) VALUES ('??????????????????????', '$2y$10$Wb389Ztb1HaoPGPGyJUtx.yFgNA9Q3BO9KxfAPf/DHbHzd6fuObHW', 'test@test.ru', '??????????????????????', '???????????? ??.??.', '1987-01-25', 'male', 5, 3, NULL);

INSERT INTO  roles (name) OVERRIDING SYSTEM VALUE VALUES ('??????????????????????????');
INSERT INTO  roles (name) OVERRIDING SYSTEM VALUE VALUES ('????????????????????????');
INSERT INTO  roles (name) OVERRIDING SYSTEM VALUE VALUES ('??????????????????????');


INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 1);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 2);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 3);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 4);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 5);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 6);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 7);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 8);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 9);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 10);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 11);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 12);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 13);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 14);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 15);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 16);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 17);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 18);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 19);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 20);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 21);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 22);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 23);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 24);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 25);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 26);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 1);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 2);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 3);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 4);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 5);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 9);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 14);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 17);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 19);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 21);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 24);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 25);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 26);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 1);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 5);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 9);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 14);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 17);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 19);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 23);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 24);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 25);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 26);


INSERT INTO  user_roles (user_id, user_role) VALUES (1, 1);
INSERT INTO  user_roles (user_id, user_role) VALUES (2, 2);
INSERT INTO  user_roles (user_id, user_role) VALUES (3, 3);

INSERT INTO  contacts (representation, address_type, comment, id, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ('????????????, ????????????????????, ????. ???????????????????????? 1205', 3, '?????????? ????????????????????', 1, NULL, NULL);
INSERT INTO  contacts (representation, address_type, comment, id, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ('+7(926) 894-45-45', 1, '??????????????', 2, NULL, NULL);
INSERT INTO  contacts (representation, address_type, comment, id, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ('8 (495) 555-44-33', 1, '????????????????????', 3, NULL, NULL);


INSERT INTO  contacts_owners (user_id, contact_id) OVERRIDING SYSTEM VALUE VALUES (1, 1);
INSERT INTO  contacts_owners (user_id, contact_id) OVERRIDING SYSTEM VALUE VALUES (1, 2);
INSERT INTO  contacts_owners (user_id, contact_id) OVERRIDING SYSTEM VALUE VALUES (2, 3);

INSERT INTO tasks (
shortdescribe, fulldescribe, author, inition_date, inition_time, active, duration_of_execute) VALUES (
'???????????????? ?????????????? ???? ??????????????????????'::text, '???????????????? ?????????????? ???? ???????????????????? ?? 5 ??????????'::text, '2'::bigint, '2021-12-21'::date, '10:00:00'::time without time zone, true::boolean, '3 hours'::interval);
INSERT INTO tasks (
shortdescribe, fulldescribe, author, inition_date, inition_time, active, duration_of_execute) VALUES (
'???????????????? ?????????????? ???? ????????????????????'::text, '???????????????? ?????????????? ???? ???????????????????? ?? 5 ??????????'::text, '2'::bigint, '2021-12-21'::date, '15:00:00'::time without time zone, true::boolean, '3 hours'::interval);
INSERT INTO tasks (
shortdescribe, fulldescribe, author, inition_date, inition_time, active, duration_of_execute) VALUES (
'???????????????? ??????????????????'::text, '???????????????? ?????????????????? ???? ???????????????????? ????????'::text, '2'::bigint, '2021-12-19'::date, '09:00:00'::time without time zone, true::boolean, '4 hours'::interval);


INSERT INTO task_executors (user_id, task) VALUES (3, 1);
INSERT INTO task_executors (user_id, task) VALUES (3, 2);
INSERT INTO task_executors (user_id, task) VALUES (2, 3);


