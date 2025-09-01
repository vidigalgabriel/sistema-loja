CREATE DATABASE lojaTech;
USE lojaTech;


CREATE TABLE usuarios (
  id INT PRIMARY KEY AUTO_INCREMENT,
  usuario VARCHAR(50) NOT NULL UNIQUE,
  senha VARCHAR(50) NOT NULL,
  perfil VARCHAR(30) NOT NULL
);

CREATE TABLE clientes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(80) NOT NULL,
  cpf VARCHAR(20) NOT NULL,
  telefone VARCHAR(25) NOT NULL,
  email VARCHAR(40) NOT NULL
);

CREATE TABLE produtos (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(80) NOT NULL,
  quantidade INT NOT NULL,
  valor DOUBLE NOT NULL
);

CREATE TABLE funcionarios (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  cargo VARCHAR(20) NOT NULL,
  cpf VARCHAR(40) NOT NULL,
  telefone VARCHAR(30) NOT NULL
);

CREATE TABLE vendas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(20),
  data_venda DATE,
  id_cliente INT,
  id_funcionario INT,
  id_produto INT,
  quantidade INT,
  valor_unitario DECIMAL(10,2),
  total DECIMAL(10,2),
  forma_pagamento VARCHAR(20),
  nome_cliente VARCHAR(100)
);

INSERT INTO usuarios (usuario, senha, perfil) VALUES 
('', '', 'Adm'),
('adm', '123', 'Adm'),
('user', '123', 'Usuário'),
('MarceloTI', 'professorMarcelo', 'Usuário'),
('rafael.nunes', 'r4f43l!', 'Usuário');

INSERT INTO clientes (nome, cpf, telefone, email) VALUES
('MarceloTI Professor', '01345678901', '(11) 98234-6578', 'professorMarceloTI@gmail.com'),
('Igor Fernandes', '05432198765', '(21) 99123-8890', 'igorf@outlook.com'),
('Lúcia Rocha', '07890123456', '(31) 99741-3366', 'lucia.rocha@yahoo.com');

INSERT INTO produtos (nome, quantidade, valor) VALUES
('Mouse Gamer RGB Shadow', 45, 139.90),
('Teclado Mecânico StormX', 30, 289.50),
('Monitor LG UltraWide 29"', 15, 1099.00),
('Notebook Dell Inspiron i7', 10, 4799.99),
('HD Externo Seagate 2TB', 25, 359.90),
('SSD Kingston 1TB NVMe', 60, 399.00),
('Cadeira Gamer ThunderX', 12, 1289.00),
('Headset HyperX Cloud II', 22, 549.99);

INSERT INTO funcionarios (nome, cargo, cpf, telefone) VALUES
('Diretor MarceloTI', 'Chefe', '34512398700', '(31) 98412-0098'),
('Aline Ferreira', 'Caixa', '65891234789', '(31) 99285-3361'),
('Diego Ramos', 'Estoquista', '90123876543', '(31) 99672-8821');

SELECT 
    v.id AS venda_id,
    c.nome AS cliente,
    f.nome AS funcionario,
    v.data_venda,
    v.total AS valor_total
FROM vendas v
JOIN clientes c ON v.id_cliente = c.id
JOIN funcionarios f ON v.id_funcionario = f.id;
