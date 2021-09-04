set search_path = milkyway;

insert into person(name, type, phone, createAt, updateAt) values
    ('ou', 'SUPER', '', '2019-01-01T00:00:00', '2019-01-01T00:00:00'),
    ('chiu', 'SUPER', '', '2019-01-01T00:00:00', '2019-01-01T00:00:00');

insert into pet(personId, name, type, age, createAt, updateAt) values
    (1, 'a', 'DOG', 5, '2020-01-01T00:00:00', '2020-01-01T00:00:00'),
    (1, 'b', 'CAT', 10, '2020-01-01T00:00:00', '2020-01-01T00:00:00');
