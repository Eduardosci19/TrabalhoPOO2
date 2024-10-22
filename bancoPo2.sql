create database bdprojetoaula;

use bdprojetoaula;


create table tb_usuarios(
id_usuario int primary key,
usuario varchar(50) not null,
login varchar(50) not null unique,
senha varchar(15)not null
);


CREATE TABLE tb_Clientes (
    id_clientes INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    endereco VARCHAR(200),
    telefone VARCHAR(20),
    email VARCHAR(100),
    cpf_cnpj VARCHAR(20)
);

create table tb_agenda (
    id INT PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL UNIQUE,
    data VARCHAR(10) NOT NULL,     
    hora VARCHAR(10) NOT NULL,     
    descricao VARCHAR(255) NOT NULL
);

insert into tb_agenda(id, cliente, data, hora, descricao)
values(1, 'edu', '17-08-2007', '10:00', 'Tenho medico');

select * from tb_agenda;




INSERT INTO tb_usuarios (id_usuario, usuario, login, senha)
VALUES (1, 'usuario', 'user', 'user');


insert into tb_usuarios(id_usuario, usuario, email, login, senha)
values(2, 'admini', 'admin', 'admin','adm');






INSERT INTO tb_Clientes (id_clientes , nome, endereco, telefone, email, cpf_cnpj)
VALUES 
    (1,'João Silva', 'Rua das Flores, 123', '(51) 1234-5678', 'joao.silva@email.com', '123.456.789-00'),
    (2, 'Maria Souza', 'Av. Central, 987', '(51) 9876-5432', 'maria.souza@email.com', '987.654.321-00'),
    (3, 'Carlos Pereira', 'Rua do Sol, 456', '(51) 2345-6789', 'carlos.pereira@email.com', '321.654.987-00'),
    (4, 'Ana Lima', 'Av. dos Anjos, 321', '(51) 3456-7890', 'ana.lima@email.com', '456.789.123-00'),
    (5, 'Paulo Alves', 'Rua do Mar, 789', '(51) 4567-8901', 'paulo.alves@email.com', '789.123.456-00');


update tb_usuarios set usuario = 'rossoeder' where id_usuario = 1;

delete from tb_usuarios where id_usuario = 1;

select * from tb_usuarios;