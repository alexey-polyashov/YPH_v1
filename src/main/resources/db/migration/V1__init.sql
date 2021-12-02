
--TYPES

CREATE TYPE addrestype AS ENUM
    ('email', 'phone', 'address');

CREATE TYPE  day_type AS ENUM
    ('work', 'weekend', 'holiday', 'preholiday');

CREATE TYPE males AS ENUM
    ('female', 'male');

--TABLES

CREATE TABLE IF NOT EXISTS   divisions
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name text COLLATE pg_catalog."default",
    parrent bigint,
    depth_level integer DEFAULT 1,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
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

CREATE TABLE IF NOT EXISTS  users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    login text COLLATE pg_catalog."default" NOT NULL,
    token text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    fullname text COLLATE pg_catalog."default",
    shortname text COLLATE pg_catalog."default",
    birthday date,
    male  males,
    "position" bigint,
    division bigint,
    image text COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT division_id FOREIGN KEY (division)
        REFERENCES  divisions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT position_id FOREIGN KEY ("position")
        REFERENCES  positions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
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
    create_at timestamp without time zone,
    update_at timestamp without time zone,
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

CREATE TABLE IF NOT EXISTS   contacts_owners
(
    user_id bigint,
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    CONSTRAINT contacts_owners_pkey PRIMARY KEY (id),
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES   users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS   contacts
(
    ownerid bigint,
    representation text COLLATE pg_catalog."default",
    address_type   addrestype NOT NULL,
    comment text COLLATE pg_catalog."default",
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT contacts_pkey PRIMARY KEY (id),
    CONSTRAINT owner_id FOREIGN KEY (ownerid)
        REFERENCES   contacts_owners (id) MATCH SIMPLE
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

CREATE TABLE IF NOT EXISTS   tasks
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    shortdescribe text COLLATE pg_catalog."default" NOT NULL,
    fulldescribe text COLLATE pg_catalog."default",
    author bigint NOT NULL,
    owner bigint,
    repeatable boolean,
    repeat_period interval,
    inition_date date,
    inition_time timestamp without time zone,
    textresult text COLLATE pg_catalog."default",
    result bigint,
    active boolean,
    inprogress boolean,
    duration_of_execute interval NOT NULL,
    CONSTRAINT tasks_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS   task_files
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    tasks bigint NOT NULL,
    representation text COLLATE pg_catalog."default",
    path text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT task_files_pkey PRIMARY KEY (id),
    CONSTRAINT fkf_task_id FOREIGN KEY (tasks)
        REFERENCES   tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS   task_executors
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    "user" bigint,
    user_group bigint,
    task bigint NOT NULL,
    CONSTRAINT task_executors_pkey PRIMARY KEY (id),
    CONSTRAINT fkt_task_id FOREIGN KEY (task)
        REFERENCES   tasks (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fkt_user_id FOREIGN KEY ("user")
        REFERENCES   users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fkt_usergroup_id FOREIGN KEY (user_group)
        REFERENCES   users_groups (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS   events
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    shortdescribe text COLLATE pg_catalog."default" NOT NULL,
    fulldescribe text COLLATE pg_catalog."default",
    date date NOT NULL,
    "time" time without time zone NOT NULL,
    repeatable boolean,
    repeat_period interval,
    author bigint NOT NULL,
    owner bigint,
    duration_of_execute interval,
    CONSTRAINT events_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN   events.owner
    IS 'если событие общее то owner = null, иначе должно быть заполнено';

CREATE TABLE IF NOT EXISTS   event_participant
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    event bigint NOT NULL,
    participant bigint,
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

INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (1, 'Администрация', NULL, 1, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (2, 'Факультет 1', 1, 2, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (3, 'Факультет 2', 1, 2, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (4, 'Факультет 3', 1, 2, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (5, 'Кафедра 1_1', 2, 3, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (6, 'Кафедра 1_2', 2, 3, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (7, 'Кафедра 2_1', 3, 3, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (8, 'Кафедра 2_2', 3, 3, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (9, 'Кафедра 2_3', 3, 3, NULL, NULL);
INSERT INTO  divisions (id, name, parrent, depth_level, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (10, 'Кафедра 3_1', 4, 3, NULL, NULL);


INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ('task_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'task_edit');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'task_add');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'task_del');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_add');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_edit');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_del');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_add');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_edit');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'division_del');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'role_manage');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_group_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_group_add');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'user_group_edit');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'shedule_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'shedule_manage');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'common_task_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'common_task_manage');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'own_task_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'own_task_edit');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'own_task_add');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'own_task_del');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'self_task_read');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'self_task_edit');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'self_task_add');
INSERT INTO  grants (name) OVERRIDING SYSTEM VALUE VALUES ( 'self_task_del');


INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ('Администратор', NULL, NULL);
INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( 'Директор', NULL, NULL);
INSERT INTO  positions ( name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( 'Декан', NULL, NULL);
INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( 'Начальник кафедры', NULL, NULL);
INSERT INTO  positions (name, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES ( 'Преподаватель', NULL, NULL);

INSERT INTO  users (login, token, email, fullname, shortname, birthday, male, "position", division, image, created_at, updated_at) VALUES ('Администратор', '$2y$10$63qSE5z1PJRcO7zpaaJ/8.vWYf0qNNgJTUetLHr4VYJnDAjHEPcdS', 'test@test.ru', 'Администратор', 'Администратор', NULL, NULL, 1, 1, NULL, NULL, NULL);
INSERT INTO  users (login, token, email, fullname, shortname, birthday, male, "position", division, image, created_at, updated_at) VALUES ('Руководитель', '$2y$10$Wb389Ztb1HaoPGPGyJUtx.yFgNA9Q3BO9KxfAPf/DHbHzd6fuObHW', 'test@test.ru', 'Руководитель', 'Руководитель', NULL, NULL, 3, 2, NULL, NULL, NULL);
INSERT INTO  users (login, token, email, fullname, shortname, birthday, male, "position", division, image, created_at, updated_at) VALUES ('Исполнитель', '$2y$10$Wb389Ztb1HaoPGPGyJUtx.yFgNA9Q3BO9KxfAPf/DHbHzd6fuObHW', 'test@test.ru', 'Исполнитель', 'Исполнитель', NULL, NULL, 5, 3, NULL, NULL, NULL);

INSERT INTO  roles (name, create_at, update_at) OVERRIDING SYSTEM VALUE VALUES ('Администратор', NULL, NULL);
INSERT INTO  roles (name, create_at, update_at) OVERRIDING SYSTEM VALUE VALUES ('Руководитель', NULL, NULL);
INSERT INTO  roles (name, create_at, update_at) OVERRIDING SYSTEM VALUE VALUES ('Исполнитель', NULL, NULL);


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
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 5);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 9);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 14);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 17);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 19);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 1);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 5);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 9);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 14);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 17);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 19);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 19);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (1, 20);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 22);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 23);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 24);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (2, 21);
INSERT INTO  roles_grants (role_id, grant_id) VALUES (3, 25);


INSERT INTO  user_roles (user_id, user_role) VALUES (1, 1);
INSERT INTO  user_roles (user_id, user_role) VALUES (2, 2);
INSERT INTO  user_roles (user_id, user_role) VALUES (3, 3);


INSERT INTO  contacts_owners (user_id, id) OVERRIDING SYSTEM VALUE VALUES (1, 1);
INSERT INTO  contacts_owners (user_id, id) OVERRIDING SYSTEM VALUE VALUES (2, 2);

INSERT INTO  contacts (ownerid, representation, address_type, comment, id, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (1, 'Москва, Зеленоград, пр. Панфиловский 1205', 'address', 'адрес проживания', 1, NULL, NULL);
INSERT INTO  contacts (ownerid, representation, address_type, comment, id, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (1, '+7(926) 894-45-45', 'phone', 'рабочий', 2, NULL, NULL);
INSERT INTO  contacts (ownerid, representation, address_type, comment, id, created_at, updated_at) OVERRIDING SYSTEM VALUE VALUES (2, '8 (495) 555-44-33', 'phone', 'внутренний', 3, NULL, NULL);




