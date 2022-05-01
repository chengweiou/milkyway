set search_path = milkyway;

insert into person(name, type, phone, email, imgsrc, createAt, updateAt) values
    ('ou', 'SUPER', '9790000000', 'o@o.com', '', '2019-01-01T00:00:00', '2019-01-01T00:00:00'),
    ('chiu', 'SUPER', '9890000000', 'c@c.com', '', '2019-01-01T00:00:00', '2019-01-01T00:00:00');

insert into pet(personId, name, type, age, createAt, updateAt) values
    (1, 'a', 'DOG', 5, '2020-01-01T00:00:00', '2020-01-01T00:00:00'),
    (1, 'b', 'CAT', 10, '2020-01-01T00:00:00', '2020-01-01T00:00:00');
