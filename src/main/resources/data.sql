SELECT NEXT VALUE FOR Person_SEQ;
SELECT NEXT VALUE FOR Person_SEQ;
SELECT NEXT VALUE FOR Person_SEQ;
SELECT NEXT VALUE FOR Person_SEQ;
SELECT NEXT VALUE FOR Person_SEQ;
SELECT NEXT VALUE FOR Person_SEQ;

INSERT INTO person (`id`, `name`, cpf, birthDate) VALUES
    (1,'Ana Holanda', 88888888888, '2002-07-13'),
    (2, 'Joãozinho', '99999999999', '2012-07-13'),
    (3,'Leandro Assunção', 88888838822, '1988-04-25'),
    (4, 'Ivaldo', 7777777777, '1970-07-13'),
    (5, 'Pedrinho', 7777777721, '2012-07-13'),
    (6, 'Ana Filho', 4577777721, '2011-12-25');

INSERT INTO guardian (`id`, email ,`password`) VALUES 
 (1, 'ana@email.com', '$2a$10$RleiVtfl2ikXq1RYADYTC.rsUoA6eScRlk293ygGXzDjmFWE/yBQq'),
 (3, 'leandro@email.com', '$2a$10$/8XMU9zaTUp3Hbj9II1OxONTx7zvKaWzL7OW30JoikiDkopZqQOGu'),
 (4, 'ivaldo@email.com', '$2a$10$RleiVtfl2ikXq1RYADYTC.rsUoA6eScRlk293ygGXzDjmFWE/yBQq');

SELECT NEXT VALUE FOR "family-group_SEQ";
SELECT NEXT VALUE FOR "family-group_SEQ";
SELECT NEXT VALUE FOR "family-group_SEQ";
INSERT INTO "family-group" (`id`, `name`) VALUES
    (1, 'Família Sampaio'),
    (2, 'Família Holanda'),
    (3, 'Família Boulevar');


INSERT INTO dependent (`id`, familyGroupId) VALUES
    (2, 1),
    (5, 3),
    (6, 2);

-- INSERT INTO guard (daysOfWeek, guardianRole, guardianId, dependentId)