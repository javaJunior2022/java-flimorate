-- we don't know how to generate root <with-no-name> (class Root) :(
create table INFORMATION_SCHEMA.ENUM_VALUES
(
    OBJECT_CATALOG  CHARACTER VARYING,
    OBJECT_SCHEMA   CHARACTER VARYING,
    OBJECT_NAME     CHARACTER VARYING,
    OBJECT_TYPE     CHARACTER VARYING,
    ENUM_IDENTIFIER CHARACTER VARYING,
    VALUE_NAME      CHARACTER VARYING,
    VALUE_ORDINAL   CHARACTER VARYING
);

create table GENRES
(
    GENRE_ID INTEGER auto_increment,
    NAME     CHARACTER VARYING(100) not null
);

create unique index GENRES_GENRE_ID_UINDEX
    on GENRES (GENRE_ID);

alter table GENRES
    add constraint GENRES_PK
        primary key (GENRE_ID);

create table INFORMATION_SCHEMA.INDEX_COLUMNS
(
    INDEX_CATALOG          CHARACTER VARYING,
    INDEX_SCHEMA           CHARACTER VARYING,
    INDEX_NAME             CHARACTER VARYING,
    TABLE_CATALOG          CHARACTER VARYING,
    TABLE_SCHEMA           CHARACTER VARYING,
    TABLE_NAME             CHARACTER VARYING,
    COLUMN_NAME            CHARACTER VARYING,
    ORDINAL_POSITION       INTEGER,
    ORDERING_SPECIFICATION CHARACTER VARYING,
    NULL_ORDERING          CHARACTER VARYING,
    IS_UNIQUE              BOOLEAN
);

create table INFORMATION_SCHEMA.INFORMATION_SCHEMA_CATALOG_NAME
(
    CATALOG_NAME CHARACTER VARYING
);

create table RATINGS
(
    RATING_ID INTEGER auto_increment,
    NAME      CHARACTER VARYING(50) not null,
    constraint "RATING_id"
        primary key (RATING_ID)
);

create table FILMS
(
    FILM_ID       INTEGER auto_increment,
    NAME          CHARACTER VARYING(100) not null,
    DESCRIPTION   CHARACTER VARYING(500) not null,
    "releaseDate" DATE                   not null,
    DURATION      INTEGER                not null,
    RATING_ID     INTEGER,
    constraint RATING_ID
        foreign key (RATING_ID) references RATINGS
);

create unique index FILMS_FILM_ID_UINDEX
    on FILMS (FILM_ID);

create unique index FILMS_FILM_NAME_UINDEX
    on FILMS (NAME);

alter table FILMS
    add constraint FILM_ID
        primary key (FILM_ID);

create table FILMS_GENRES
(
    FILM_ID  INTEGER,
    GENRE_ID INTEGER,
    constraint FILMS_GENRES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint FILMS_GENRES_GENRES_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRES
);

create unique index RATING_RATING_ID_UINDEX
    on RATINGS (RATING_ID);

create table USERS
(
    USER_ID        INTEGER auto_increment,
    NAME           CHARACTER VARYING(100) not null,
    LOGIN          CHARACTER VARYING(50)  not null,
    EMAIL          CHARACTER VARYING(50)  not null,
    PARENT_USER_ID INTEGER,
    constraint USERS_ID
        primary key (USER_ID),
    constraint USER_ID
        foreign key (USER_ID) references USERS
);

create table FILMS_LIKES
(
    USER_ID INTEGER,
    FILM_ID INTEGER,
    constraint FILM_LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint FILM_LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
);

create table FRIENDS
(
    FROM_USER_ID INTEGER not null,
    TO_USER_ID   INTEGER not null,
    "isAccepted" BOOLEAN not null,
    constraint FRIENDS_USERS_USER_ID_FK
        foreign key (FROM_USER_ID) references USERS,
    constraint FRIENDS_USERS_USER_ID_FK_2
        foreign key (TO_USER_ID) references USERS
);

create unique index USERS_EMAIL_UINDEX
    on USERS (EMAIL);

create unique index USERS_USER_ID_UINDEX
    on USERS (USER_ID);


