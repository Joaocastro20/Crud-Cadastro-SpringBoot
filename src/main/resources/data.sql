INSERT INTO USER (id,cpf,nm_name,email) VALUES (1,'111.111.111-11','robertinho','robertinho@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (2,'222.222.222-22','eustaquio','eustaquio@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (3,'333.333.333-33','ana','ana@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (4,'444.444.444-44','ronaldo','ronaldo@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (5,'555.555.555-55','diogo','diogo@Gmail.com');

INSERT INTO TEMPLATE_DOCUMENTO (id,ativo,modelo,nome) VALUES  (1,true,'<html><head><link href="classpath:/template.css" rel="stylesheet"/></head><body><img src="classpath:/basis.png" /> <p class="classpath:/template.css"> Eu, identificado pelo CPF: ${user.cpf}</p></body></html>','termo');
