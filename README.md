# 🏢 Sistema de Gerenciamento de Condomínio

Este é um sistema desenvolvido em Java para o gerenciamento completo de condomínios, oferecendo funcionalidades para controle de unidades habitacionais, administração de moradores e gestão financeira
Este sistema foi desenvolvido com o objetivo de aplicar na prática conhecimentos relacionados a padrões de projeto, modelagem com diagramas de classe e construção de banco de dados, integrando conceitos fundamentais de desenvolvimento de software orientado a objetos.

Desenvolvedores: João Paulo, Maria Eduarda e Maria Luiza

## 📑 Índice

- [📖 Documentação do projeto](#-documentacao-do-projeto)  
- [🚀 Funcionalidades](#-funcionalidades)  
- [🛠️ Tecnologias Utilizadas](#-tecnologias-utilizadas)  
- [⚙️ Como Executar](#️-como-executar)  
- [📂 Fotos do Projeto](#-foto-do-projeto)  

## 📖 Documentação do projeto

### Principais aspectos abordados no projeto:
- Aplicação de **Padrões de Projeto** para melhor estrutura e manutenção do código
- **Modelagem UML** com **Diagrama de Classe** para o planejamento da arquitetura do sistema
- **Banco de Dados Relacional** para armazenamento persistente das informações
- Utilização dos padrões de projeto: MVC, DAO, Factory e Singleton

Diagrama de classes
![image](https://github.com/user-attachments/assets/4328df25-d658-4086-b8de-b5dd3d5a4da3)
![image](https://github.com/user-attachments/assets/0636e140-3d46-4fed-a46f-91061320134b)

Banco de dados

![image](https://github.com/user-attachments/assets/55cf2c40-24c7-4cb3-8f21-2a84860e9d67)

## 🚀 Funcionalidades

### 🏠 Gestão de Unidades
- Cadastro de novas unidades habitacionais
- Atualização e consulta de informações das casas

### 👥 Gestão de Pessoas
- Cadastro de **proprietários** e **moradores**
- Atualização e visualização detalhada das informações de cada pessoa

### 💰 Administração de Mensalidades
- Lançamento de cobranças mensais
- Acompanhamento de pagamentos

## 🛠️ Tecnologias Utilizadas

- **Linguagem principal** — JAVA
- **Banco de Dados** — MySQL
- **Padrões de Projeto** — Singleton, Factory, DAO e MVC
- **IDE** — NetBeans
- **Diagramas UML** — Para a modelagem e documentação do sistema
- **Git** — Controle de versão

## ⚙️ Como Executar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seuusuario/gerenciador-condominio.git

2. **Execute o script SQL para criar o banco de dados**
   ```
   CREATE DATABASE condominio;
   USE condominio;
   
   CREATE TABLE pessoas (
      id int primary key auto_increment,
       nome varchar(255),
       dt_nasc date,
       cpf varchar(15),
       rg varchar(15)
   );
   
   CREATE TABLE residencias(
      id int primary key auto_increment,
       id_proprietario int,
       numero int not null,
       cep varchar(10),
       logradouro varchar(255),
       data_criacao datetime default current_timestamp,
       
       foreign key (id_proprietario) references pessoas (id)
   );
   
   CREATE TABLE morador_residencia(
      id int primary key auto_increment,
       id_pessoa int,
       id_residencia int,
       
       foreign key (id_pessoa) references pessoas (id) ON DELETE CASCADE,
       foreign key (id_residencia) references residencias (id) ON DELETE CASCADE
   );
   
   CREATE TABLE mensalidade(
      id int primary key auto_increment,
       id_residencia int,
       vencimento date,
       valor double,
       status int,
       
       foreign key (id_residencia) references residencias (id) ON DELETE CASCADE
   );
   
   INSERT INTO pessoas (nome, dt_nasc, cpf, rg) VALUES ("Joao", "2010-05-20", "434231532", "4324123143");
   
   INSERT INTO residencias (id_proprietario, numero, cep, logradouro) VALUES (1, "120", "13880-000", "kaique da sila");
   
   INSERT INTO morador_residencia (id_pessoa, id_residencia) VALUE (1, 1);
   ```
   
3. **Execute o arquvivo main**

## 📂 Fotos do Projeto
