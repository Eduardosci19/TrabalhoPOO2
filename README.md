# TrabalhoPOO2


## Funcionalidades Principais

1. :lock: **Tela de Login**
    - Permite que usuários cadastrados façam a autenticação no sistema.
    - **Campos:** Nome de usuário e Senha.
    - Exibe uma mensagem de erro caso o login falhe.
  
      ![image](https://github.com/user-attachments/assets/2e344e4d-b7e1-4960-b9c0-783a8381e3c5)


2. :house: **Tela Principal**
    - Acessível após o login bem-sucedido.
    - Exibe um menu com opções para navegação entre as telas:
        - **Cadastro de Usuários**
        - **Cadastro de Clientes**
        - **Agenda de Compromissos**

         ![image](https://github.com/user-attachments/assets/b00abe6d-89c9-4398-a190-fce12d0493fd)



3. :busts_in_silhouette: **Tela de Cadastro de Usuários**
    - Permite o cadastro, edição e exclusão de usuários.
    - **Campos:** Nome, E-mail, Nome de Usuário, Senha.
    - Validações de duplicidade no nome de usuário.

    ![image](https://github.com/user-attachments/assets/82b84ba8-5bae-4bc4-9bd9-550371c8767a)


5. :bust_in_silhouette: **Tela de Cadastro de Clientes**
    - Permite o cadastro, edição e exclusão de clientes.
    - **Campos:** Nome, Endereço, Telefone, E-mail, CPF/CNPJ.
  
    ![image](https://github.com/user-attachments/assets/56bb3d8e-b667-448d-b294-8a6716789d65)


   5. 🗓️ **Tela de Cadastro de Agenda**
    - Permite o registro de compromissos ou eventos.
    - **Campos:** Data, Horário, Descrição, Cliente associado (escolhido a partir do cadastro de clientes).
    - Exibe os compromissos em formato de tabela com opções de visualização por dia, semana ou mês.
    - Ações disponíveis:
        - Adicionar compromisso.
        - Editar compromisso.
        - Excluir compromisso.
     
      ![image](https://github.com/user-attachments/assets/33575a82-54b2-4ea7-bdb6-f3d172c84387)


## Banco de Dados

📊 **MySQL Workbench e Estrutura do Banco de Dados**

- O banco de dados local utiliza **MySQL** para armazenar as informações de usuários, clientes e compromissos.
- O banco de dados contém as seguintes tabelas principais:




    1. 👥 **Tabela de Usuários**
       ![image](https://github.com/user-attachments/assets/6c1c3c2c-3e21-49b5-82f8-73336a4d78b6)


       ![image](https://github.com/user-attachments/assets/bf0bbb84-a418-4f47-8846-e56fab97e623)





    3. 👤 **Tabela de Clientes**
       ![image](https://github.com/user-attachments/assets/32575d0b-f038-43ba-87b3-5291f31e4171)

       ![image](https://github.com/user-attachments/assets/09957e4c-cebd-4743-b99b-f5bb0a53375e)







    5. 🗓️ **Tabela de Agenda**
        ![image](https://github.com/user-attachments/assets/a8b29565-1b3e-43e8-a5e2-5e55f6ded33e)

       ![image](https://github.com/user-attachments/assets/40c7a764-f456-4dca-88dc-3db4b38856d9)



- O sistema permite realizar operações **CRUD** (Create, Read, Update, Delete) para usuários, clientes e compromissos.

### Conexão com o Banco de Dados

- O sistema utiliza **JDBC (Java Database Connectivity)** para se conectar e manipular os dados no banco de dados **MySQL**.
- Configuração da conexão com o banco de dados via `MySQL Workbench`:
    - Host: `localhost`
    - Porta: `3306`
    - Nome do banco: `sistema_gerenciamento`
    - Usuário e senha de acesso configurados no MySQL Workbench.
