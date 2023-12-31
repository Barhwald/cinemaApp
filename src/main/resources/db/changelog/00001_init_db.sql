
CREATE TABLE IF NOT EXISTS ROOMS
(
    ROOM_ID    SERIAL PRIMARY KEY,
    ROOM_NAME  VARCHAR(10),
    ROOM_SEATS VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS EMPLOYEES
(
    EMPLOYEE_ID SERIAL PRIMARY KEY,
    FIRST_NAME  VARCHAR(100),
    LAST_NAME   VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS MOVIES
(
    MOVIE_ID    SERIAL PRIMARY KEY,
    TITLE       VARCHAR(100),
    DESCRIPTION VARCHAR(256),
    YEAR        VARCHAR(24)
);

CREATE TABLE IF NOT EXISTS PERFORMANCES
(
    PERFORMANCE_ID SERIAL PRIMARY KEY,
    DATE           VARCHAR(24),
    TIME           VARCHAR(24),
    MOVIE_ID       BIGINT UNSIGNED,
    ROOM_ID        BIGINT UNSIGNED,
    FOREIGN KEY (MOVIE_ID) REFERENCES MOVIES (MOVIE_ID),
    FOREIGN KEY (ROOM_ID) REFERENCES ROOMS (ROOM_ID)
);

CREATE TABLE IF NOT EXISTS JOIN_ROOM_EMPLOYEE
(
    ROOM_ID BIGINT UNSIGNED,
    EMPLOYEE_ID BIGINT UNSIGNED,
    PRIMARY KEY (ROOM_ID, EMPLOYEE_ID),
    CONSTRAINT FK_ROOM FOREIGN KEY (ROOM_ID) REFERENCES ROOMS (ROOM_ID),
    CONSTRAINT FK_EMPLOYEE FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEES (EMPLOYEE_ID)
);

CREATE TABLE IF NOT EXISTS ROOMS_AUD
(
    EVENT_ID       INT(11)  NOT NULL AUTO_INCREMENT,
    EVENT_DATE     DATETIME NOT NULL,
    EVENT_TYPE     VARCHAR(10) DEFAULT NULL,
    ROOM_ID        INT(11)  NOT NULL,
    OLD_ROOM_NAME  VARCHAR(10),
    NEW_ROOM_NAME  VARCHAR(10),
    OLD_ROOM_SEATS VARCHAR(10),
    NEW_ROOM_SEATS VARCHAR(10),
    PRIMARY KEY (EVENT_ID)
);

CREATE TABLE IF NOT EXISTS EMPLOYEES_AUD
(
    EVENT_ID       INT(11)  NOT NULL AUTO_INCREMENT,
    EVENT_DATE     DATETIME NOT NULL,
    EVENT_TYPE     VARCHAR(10) DEFAULT NULL,
    EMPLOYEE_ID    INT(11)  NOT NULL,
    OLD_FIRST_NAME VARCHAR(100),
    NEW_FIRST_NAME VARCHAR(100),
    OLD_LAST_NAME  VARCHAR(100),
    NEW_LAST_NAME  VARCHAR(100),
    PRIMARY KEY (EVENT_ID)
);

CREATE TABLE IF NOT EXISTS MOVIES_AUD
(
    EVENT_ID        INT(11)  NOT NULL AUTO_INCREMENT,
    EVENT_DATE      DATETIME NOT NULL,
    EVENT_TYPE      VARCHAR(10) DEFAULT NULL,
    MOVIE_ID        INT(11)  NOT NULL,
    OLD_TITLE       VARCHAR(100),
    NEW_TITLE       VARCHAR(100),
    OLD_DESCRIPTION VARCHAR(256),
    NEW_DESCRIPTION VARCHAR(256),
    OLD_YEAR        VARCHAR(24),
    NEW_YEAR        VARCHAR(24),
    PRIMARY KEY (EVENT_ID)
);

CREATE TABLE IF NOT EXISTS PERFORMANCES_AUD
(
    EVENT_ID       INT(11)  NOT NULL AUTO_INCREMENT,
    EVENT_DATE     DATETIME NOT NULL,
    EVENT_TYPE     VARCHAR(10) DEFAULT NULL,
    PERFORMANCE_ID INT(11)  NOT NULL,
    OLD_DATE       VARCHAR(24),
    NEW_DATE       VARCHAR(24),
    OLD_TIME       VARCHAR(24),
    NEW_TIME       VARCHAR(24),
    OLD_MOVIE_ID   INT(11),
    NEW_MOVIE_ID   INT(11),
    OLD_ROOM_ID    INT(11),
    NEW_ROOM_ID    INT(11),
    PRIMARY KEY (EVENT_ID)
);