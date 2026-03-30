Logiatech

Sistema desktop de gerenciamento de loja de tecnologia, desenvolvido como projeto acadêmico na disciplina de Linguagem de Programação.

📌 Descrição

O Logiatech é uma aplicação que simula o funcionamento de uma loja física de tecnologia, permitindo o controle de produtos, clientes, fornecedores e vendas.

O sistema foi desenvolvido com o objetivo de aplicar conceitos de Programação Orientada a Objetos junto com integração a banco de dados relacional.

⚙️ Tecnologias Utilizadas

- Java
- MySQL
- Swing (interface gráfica)
- VS Code

🧩 Funcionalidades

- Cadastro de produtos
- Cadastro de clientes
- Cadastro de fornecedores
- Registro de vendas
- Controle de estoque
- Operações CRUD integradas ao banco de dados

🗄️ Banco de Dados

O sistema utiliza MySQL como banco de dados local.

Na raiz do projeto, há um arquivo ".sql" que deve ser executado para:

- Criar o banco de dados
- Criar as tabelas necessárias
- Inserir dados fictícios para testes

▶️ Como Executar

1. Clone o repositório:

git clone <url-do-repositorio>

2. Configure o MySQL:

- Certifique-se de que o MySQL está em execução
- Execute o script ".sql" presente na raiz do projeto

3. Configure a conexão com o banco:

- Acesse a classe "FabricaConexao.java"
- Altere as credenciais conforme seu ambiente:
  - URL: "jdbc:mysql://localhost:3306/lojatech"
  - Usuário: seu usuário do MySQL
  - Senha: sua senha do MySQL

4. Execute o projeto:

- Abra o projeto em uma IDE ou no VS Code
- Execute o arquivo "Main.java"

⚠️ Observações

- O sistema depende de um banco de dados MySQL rodando localmente
- As credenciais padrão podem não funcionar no seu ambiente, sendo necessário ajustá-las
- O projeto não possui versão executável (build), sendo necessário rodar diretamente pelo código

📚 Aprendizados

- Programação Orientada a Objetos em Java
- Integração com banco de dados MySQL
- Construção de interfaces gráficas com Swing
- Estruturação de aplicações desktop

👨‍💻 Autor

Gabriel Vidigal

Projeto desenvolvido para fins acadêmicos.