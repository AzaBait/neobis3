-- src/main/resources/data.sql

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (email, name, surname, password, phone) VALUES ('azz@mail.com', 'azza', 'azzaev', 'azza', '+9965214863');
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE email = 'azz@mail.com'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));
