INSERT INTO USER (id,cpf,nm_name,email) VALUES (1,'111.111.111-11','robertinho','robertinho@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (2,'222.222.222-22','eustaquio','eustaquio@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (3,'333.333.333-33','ana','ana@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (4,'444.444.444-44','ronaldo','ronaldo@Gmail.com');
INSERT INTO USER (id,cpf,nm_name,email) VALUES (5,'555.555.555-55','diogo','diogo@Gmail.com');

INSERT INTO TEMPLATE_DOCUMENTO (id,ativo,modelo,nome) VALUES  (1,true,'<html><head><link href="classpath:/template.css" rel="stylesheet"/></head><body><img src="classpath:/basis.png" /> <p class="classpath:/template.css"> Eu ${user.name}, identificado pelo CPF: ${user.cpf}</p></body></html>','termo');
INSERT INTO TEMPLATE_DOCUMENTO (id,ativo,modelo,nome) VALUES  (2,true,'<html><head><link href="classpath:/template.css" rel="stylesheet"/></head><body><img src="classpath:/basis.png" /> <p class="classpath:/template.css"> <h1>TERMO DE COMPROMISSO DE MANUTENÇÃO DE SIGILO<br></br><hr></hr></h1>
<p>
  A Basis Tecnologia da Informação S.A., sediada em ST SCS
  QUADRA 8 BLOCO B - Lotes, 50/60 - Venâncio shopping-SALAS 824 a 842
  (pares) - Asa Sul, Brasília, DF, CNPJ n.° 11.777.162/0001-57,doravante
  denominado CONTRATANTE, e, de outro lado, o(a) Kely Cristina Feitosa
  dos Santos CPF: 604.829.613-41 e RG 038321752009-7 SSP/MA nos quais
  o FUNCIONÁRIO poderá ter acesso a informações sigilosas dos clientes da
  CONTRATANTE; CONSIDERANDO a necessidade de ajustar as condições
  de revelação destas informações sigilosas, bem como definir as regras para o
  seu uso e proteção;
</p></p>
<div style="text-align: center">
<table style="text-align: center">
  <tr>
    <td>___________________________</td>
    <td>___________________________</td>
  </tr>
  <tr>
    <td>Basis Tecnologia</td>
    <td>${user.name}</td>
  </tr>
</table>
</div>
</body></html>','termo');

