DROP TABLE IF EXISTS sunrise;
CREATE TABLE sunrise
(
    id              bigint,
    date            date,
    time_of_sunrise varchar(255),
    city_id         bigint
);

DROP TABLE IF EXISTS sunset;
CREATE TABLE sunset
(
    id             bigint,
    date           date,
    time_of_sunset varchar(255),
    city_id        bigint
);

DROP TABLE IF EXISTS city;
CREATE TABLE city
(
    id        bigint,
    country   varchar(255),
    latitude  double precision,
    longitude double precision,
    name      varchar(255),
    state     varchar(255)
);

DROP TABLE IF EXISTS members_roles;
CREATE TABLE members_roles
(
    member_id bigint,
    role_id bigint
);

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id   bigint,
    name varchar(255)
);

DROP TABLE IF EXISTS member;
CREATE TABLE member
(
    id       bigint,
    email    varchar(255),
    name     varchar(255),
    password varchar(255),
    member_id  uuid
)