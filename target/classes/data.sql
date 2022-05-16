INSERT INTO USER (id,cpf,nm_name,email) VALUES (1,'111.111.111-11','robertinho','robertinho@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (2,'222.222.222-22','eustaquio','eustaquio@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (3,'333.333.333-33','ana','ana@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (4,'444.444.444-44','ronaldo','ronaldo@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (5,'555.555.555-55','diogo','diogo@Gmail.com');

INSERT INTO TEMPLATE_DOCUMENTO (id,ativo,modelo,nome) VALUES  (1,true,'<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>Title</title></head><body><h1>${user.cpf}</h1></body></html>','termo');
