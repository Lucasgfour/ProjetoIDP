-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)

USE bdlsi;

--------------------* Criando todas as tabelas do BD *--------------------

-- Tabela cad_produto

DROP TABLE IF EXISTS `cad_produto`;

CREATE TABLE `cad_produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) DEFAULT NULL,
  `descricao` varchar(45) NOT NULL,
  `preco_custo` double NOT NULL,
  `preco_venda` double NOT NULL,
  `categoria` int(11) DEFAULT NULL,
  `cod_fornecedor` int(11) DEFAULT NULL,
  `quantidade` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`),
  FOREIGN KEY (categoria) REFERENCES cad_categoria (id),
  FOREIGN KEY (cod_fornecedor) REFERENCES cad_fornecedor (id)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

-- Tabela cad_usuario

create table cad_usuario(
    id int not null auto_increment,
    nome varchar(30) not null,
    login varchar(30) not null,
    senha varchar(32) not null,
    administrador tinyint(1),
    PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Tabela vendasProduto

create table vendasProduto(
    id_VendasProdutos int not null auto_increment,
    pedido int not null,
    produto int not null,
    preço double not null,
    quantidade int not null,
    PRIMARY KEY(id_VendasProdutos),
    FOREIGN KEY (pedido) REFERENCES vendas (id_venda)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Tabela vendas

create table vendas(
    id_venda int not null auto_increment,
    data date,
    forma_pagamento varchar(20),
    PRIMARY KEY(id_venda)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;



-- Tabela Cadastro de contas a pagar

CREATE TABLE cad_contaPagar(
	id INT NOT NULL AUTO_INCREMENT,
	descricao VARCHAR(60) NOT NULL,
	valor DOUBLE NOT NULL,
	data DATE NOT NULL,
	status CHAR(1) NOT NULL,
	parcela int NOT NULL,
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Tabela Cadastro de contas a receber

CREATE TABLE cad_contaReceber(
	id INT NOT NULL AUTO_INCREMENT,
	descricao VARCHAR(60) NOT NULL,
	valor DOUBLE NOT NULL,
	data DATE NOT NULL,
	status CHAR(1) NOT NULL,
	parcela int NOT NULL,
	PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;





--------------------* Criando todos os Stored Procedures *--------------------


-- - -- - -- Stored Procedure: Tabela cad_produto -- - -- - -- - 


-- Function responsável por inserir um novo produto.

DELIMITER //
CREATE OR REPLACE FUNCTION fn_inserir_produto(codigo_p VARCHAR(45), descricao_p VARCHAR(50), preco_custo_p DOUBLE, preco_venda_p DOUBLE, categoria_p INT, cod_fornecedor_p INT, quantidade_p INT)
RETURNS INT
BEGIN
	INSERT INTO cad_produto(codigo, descricao, preco_custo, preco_venda, categoria, cod_fornecedor, quantidade)
	VALUES (codigo_p, descricao_p, preco_custo_p, preco_venda_p, categoria_p, cod_fornecedor_p, quantidade_p);
	RETURN LAST_INSERT_ID()
END; //

-- Stored Procedure responsável por excluir um produto.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_produto(id_produto INT)
BEGIN
	DELETE FROM cad_produto WHERE id = id_produto;
END; //

-- Stored Procedure responsável por alterar um produto.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_produto(id_p INT, codigo_p VARCHAR(45), descricao_p VARCHAR(50), preco_custo_p DOUBLE, preco_venda_p DOUBLE, categoria_p INT, cod_fornecedor_p INT, quantidade_p INT)
BEGIN 
	UPDATE cad_produto SET codigo = codigo_p, descricao = descricao_p, preco_custo = preco_custo_p, preco_venda = preco_venda_p, categoria = categoria_p, cod_fornecedor = cod_fornecedor_p, quantidade = quantidade_p WHERE id = id_p;
END; //

-- Stored Procedure responsável por consultar um determinado produto.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_produto(id_produto INT)
BEGIN
	SELECT * FROM cad_produto WHERE id = id_produto;
END; //

-- Stored Procedure responsável por listar todos os produtos.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_produtos()
BEGIN
	SELECT * FROM cad_produto;
END; //


-- - -- - -- Stored Procedure: Tabela cad_usuario -- - -- - -- - 


-- Stored Procedure responsável por inserir um novo usuário.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_inserir_usuario(nome_u VARCHAR(30), login_u VARCHAR(30), senha_u VARCHAR(32),
		 admin_u TINYINT(1))
BEGIN
	INSERT INTO cad_usuario (nome, login, senha, administrador) VALUES (nome_u, login_u, MD5(senha_u), admin_u);
END; //

-- Stored Procedure responsável por excluir um usuario.

DELIMITER // 
CREATE OR REPLACE PROCEDURE sp_excluir_usuario(id_usuario INT)
BEGIN 
	DELETE FROM cad_usuario WHERE id = id_usuario;
END; //

-- Stored Procedure responsável por alterar um usuário.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_usuario(id_u INT, nome_u VARCHAR(30), login_u VARCHAR(30), senha_u VARCHAR(32),
		 admin_u TINYINT(1))
BEGIN
	UPDATE cad_usuario SET nome = nome_u, login = login_u, senha = MD5(senha_u), administrador = admin_u WHERE id = id_u;
END; //

-- Stored Procedure responsável por consultar um determinado usuario.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_usuario(id_usuario INT )
BEGIN 
	SELECT * FROM cad_usuario WHERE id = id_usuario;
END; //

-- Stored Procedure responsável por listar todos os usuarios.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_usuarios()
BEGIN
	SELECT * FROM cad_usuario;
END; //


-- - -- - -- Stored Procedure: Tabela vendas -- - -- - -- - 


-- Stored Procedure responsável por inserir uma nova venda.

DELIMITER //
CREATE OR REPLACE FUNCTION fn_inserir_venda(data_v DATE, pagamento VARCHAR(20))
RETURNS INT
BEGIN
	INSERT INTO vendas (data, forma_pagamento) VALUES (data_v, pagamento);
	RETURN LAST_INSERT_ID();
END; //

-- Stored Procedure responsável por excluir uma venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_venda(id INT)
BEGIN
	DELETE FROM vendas WHERE id_venda = id;
END; //

-- Stored Procedure responsável por alterar uma venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_venda(id INT, data_v DATE, pagamento VARCHAR(20))
BEGIN
	UPDATE vendas SET data = data_v, forma_pagamento = pagamento WHERE id_venda = id;
END; //

-- Stored Procedure responsável por consultar uma determinado venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_venda(id INT)
BEGIN
	SELECT * FROM vendas WHERE id_venda = id;
END; //

-- Stored Procedure responsável por listar todas as vendas.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_vendas()
BEGIN
	SELECT * FROM vendas;
END; //


-- - -- - -- Stored Procedure: Tabela vendasProduto-- - -- - -- -



-- Stored Procedure responsável por inserir um produto da venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_inserir_vendaProduto(pedido_vp INT, produto_vp INT, preco_vp DOUBLE, quantidade_vp INT)
BEGIN
	INSERT INTO vendasProduto (pedido, produto, preço, quantidade) VALUES (pedido_vp, produto_vp, preco_vp, quantidade_vp);
END; //

-- Stored Procedure responsável por excluir um produto da venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_vendaProduto(id INT)
BEGIN
	DELETE FROM vendasProduto WHERE id_VendasProdutos = id;
END; //

-- Stored Procedure responsável por alterar um produto da venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_vendaProduto(id_vp INT, pedido_vp INT, produto_vp INT, preco_vp DOUBLE, quantidade_vp INT)
BEGIN
	UPDATE vendasProduto SET pedido = pedido_vp, produto = produto_vp, preço = preco_vp, quantidade = quantidade_vp WHERE id_VendasProdutos = id_vp;
END; //

-- Stored Procedure responsável por consultar um determinado produto da venda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_vendaProduto(id_vp INT)
BEGIN
	SELECT * FROM vendasProduto WHERE id_VendasProdutos = id_vp;
END; //

-- Stored Procedure responsável por listar todos os produtos da veda.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_vendaProduto()
BEGIN
	SELECT * FROM vendasProduto;
END; //



-- - -- - -- Stored Procedure: Tabela cad_categoria -- - -- - -- -



-- Stored Procedure responsável por inserir uma nova categoria.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_inserir_categoria(nome_c VARCHAR(50), descricao_c VARCHAR(70))
BEGIN
	INSERT INTO cad_categoria (nome, descricao) VALUES (nome_c, descricao_c);
END; //

-- Stored Procedure responsável por excluir uma categoria.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_categoria(id_cat INT)
BEGIN
	DELETE FROM cad_categoria WHERE id = id_cat;
END; //

-- Stored Procedure responsável por alterar uma categoria.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_categoria(id_cat INT, nome_c VARCHAR(50), descricao_c VARCHAR(70))
BEGIN
	UPDATE cad_categoria SET nome = nome_c, descricao = descricao_c WHERE id = id_cat;
END; //

-- Stored Procedure responsável por consultar uma determinada categoria.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_categoria(id_cat INT)
BEGIN
	SELECT * FROM cad_categoria WHERE id = id_cat;
END; //

-- Stored Procedure responsável por listar todas as categorias.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_categorias()
BEGIN
	SELECT * FROM cad_categoria;
END; //


-- - -- - -- Stored Procedure: Tabela cad_fornecedor -- - -- - -- -



-- Stored Procedure responsável por inserir um novo fornecedor.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_inserir_fornecedor(nome_f VARCHAR(50), cnpj_f VARCHAR(15), endereco_f VARCHAR(80),
		 cidade_f VARCHAR(40), telefone_f VARCHAR(20))
BEGIN
	INSERT INTO cad_fornecedor (nome, cnpj, endereco, cidade, telefone)
	VALUES (nome_f, cnpj_f, endereco_f, cidade_f, telefone_f);
END; //


-- Stored Procedure responsável por excluir um fornecedor.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_fornecedor(id_fornecedor INT)
BEGIN
	DELETE FROM cad_fornecedor WHERE id = id_fornecedor;
END; //

-- Stored Procedure responsável por alterar um fornecedor.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_fornecedor(id_forn INT, nome_f VARCHAR(50), cnpj_f VARCHAR(15),
       endereco_f VARCHAR(80), cidade_f VARCHAR(40), telefone_f VARCHAR(20))
BEGIN
	UPDATE cad_fornecedor SET nome = nome_f, cnpj = cnpj_f, endereco = endereco_f, cidade = cidade_f,
	 telefone = telefone_f WHERE id = id_forn;
END; //

-- Stored Procedure responsável por consultar um determinado fornecedor.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_fornecedor(id_forn INT)
BEGIN
	SELECT * FROM cad_fornecedor WHERE id = id_forn;
END; //

-- Stored Procedure responsável por listar todos os fornecedores.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_fornecedores()
BEGIN
	SELECT * FROM cad_fornecedor;
END; //


-- - -- - -- Stored Procedure: Tabela cad_contaPagar -- - -- - -- -



-- Sotred Procedure responsável por um inserir uma nova conta a pagar.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_inserir_contaPagar(descricao_cp VARCHAR(60), valor_cp DOUBLE, data_cp DATE, status_cp     		 CHAR(1), parcela_cp INT(11))
BEGIN
	INSERT INTO cad_contaPagar (descricao, valor, data, status, parcela) 
	VALUES (descricao_cp, valor_cp, data_cp, status_cp, parcela_cp);
END; //

-- Stored Procedure responsável por excluir uma conta a pagar.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_contaPagar(id_cp INT)
BEGIN
	DELETE FROM cad_contaPagar WHERE id = id_cp;
END; //

-- Stored Procedure responsável por alterar uma conta a pagar.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_contaPagar(id_cp INT, descricao_cp VARCHAR(60), valor_cp DOUBLE, data_cp DATE,  			 status_cp CHAR(1), parcela_cp INT(11))
BEGIN
	UPDATE cad_contaPagar SET descricao = descricao_cp, valor = valor_cp, data = data_cp, status = status_cp, 
		parcela = parcela_cp WHERE id = id_cp;
END; //

-- Stored Procedure responsável por consultar uma determinada conta a pagar.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_contaPagar(id_cp INT)
BEGIN
	SELECT * FROM cad_contaPagar WHERE id = id_cp;
END; //

-- Stored Procedure responsável por listar todas as contas a pagar.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_contaPagar()
BEGIN
	SELECT * FROM cad_contaPagar;
END; //



-- - -- - -- Stored Procedure: Tabela cad_contaReceber -- - -- - -- -



-- Stored Procedure responsável por inserir uma nova conta a receber.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_inserir_contaReceber(descricao_cr VARCHAR(60), valor_cr DOUBLE, data_cr DATE,
		 status_cr CHAR(1), parcela_cr INT(11))
BEGIN
	INSERT INTO cad_contaReceber (descricao, valor, data, status, parcela)
	VALUES (descricao_cr, valor_cr, data_cr, status_cr, parcela_cr);
END; //

-- Stored Procedure responsável por excluir uma conta a receber.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_excluir_contaReceber(id_cr INT)
BEGIN
	DELETE FROM cad_contaReceber WHERE id = id_cr;
END; //

-- Stored Procedure responsável por alterar uma conta a receber.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_alterar_contaReceber(id_cr INT, descricao_cr VARCHAR(60), valor_cr DOUBLE, data_cr DATE,
		 status_cr CHAR(1), parcela_cr INT(11))
BEGIN
	UPDATE cad_contaReceber SET descricao = descricao_cr, valor = valor_cr, data = data_cr, status = status_cr,
	parcela = parcela_cr WHERE id = id_cr;
END; //

-- Stored Procedure responsável por consultar uma determinada conta a receber.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_consultar_contaReceber(id_cr INT)
BEGIN
	SELECT * FROM cad_contaReceber WHERE id = id_cr;
END; //

-- Stored Procedure responsável por listar todas as contas a receber.

DELIMITER //
CREATE OR REPLACE PROCEDURE sp_listar_contaReceber()
RETURNS INT
BEGIN
	SELECT * FROM cad_contaReceber;
END; //

-- Function
