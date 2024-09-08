INSERT INTO city (id, country, latitude, longitude, name, state)
VALUES (952, 'HU', 47.4979937, 19.0403594, 'Budapest', 'null'),
       (902, 'GB', 51.5073219, -0.1276474, 'London', 'England'),
       (904, 'CA', 42.9832406, -81.243372, 'London', 'Ontario');

INSERT INTO sunrise (id, date, time_of_sunrise, city_id)
VALUES (602, '2024-08-17', '2:33:35 AM', 902),
       (652, '2024-08-18', '2:30:54 AM', 952);

INSERT INTO sunset (id, date, time_of_sunset, city_id)
VALUES (152, '2024-08-17', '2:42:21 PM', 902),
       (202, '2024-08-18', '3:16:40 PM', 952);

INSERT INTO member (id, email, name, password, member_id)
VALUES (753, 'kriszta@gmail.com', 'Kriszta', '$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.',
        '02cd3878-dc3f-40e4-af06-c40b6753b45b');

INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER'),
       (2,'ROLE_ADMIN');

INSERT INTO members_roles (member_id, role_id)
VALUES (753, 1)

