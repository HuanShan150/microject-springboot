# Microject

Este é um projeto Spring Boot utilizando arquitetura hexagonal, Docker. O projeto é uma aplicação de gerenciamento de projetos, clientes e atividades.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- H2 Database
- Docker
- JUnit 5
- Mockito
- Postgres

## Estrutura do Projeto

A estrutura do projeto segue a arquitetura HEXAGONAL (Ports and Adapters), dividida nos seguintes pacotes:

- **domain**: Contém as entidades do domínio e enumerações.
- **repository**: Interfaces de repositórios para acesso ao banco de dados.
- **service**: Classes de serviços contendo a lógica de negócios.
- **adapter.controller**: Controladores REST que expõem as APIs.

![image](https://github.com/HuanShan150/microject/assets/30788893/6009a327-6bf1-4bc2-9e58-9157cb43e76b)


## Configuração e Execução do Projeto

### Pré-requisitos

- Java 17
- Docker
- Docker Compose
- Gradle

### Passos para Execução
   **Clone o repositório:**

  Foi utilizado DevContainers para facilitar a instalação do projeto, já terá todas configurações no arquivo .devcontainer.json

  https://code.visualstudio.com/docs/devcontainers/containers

  Ter vscode com a extensão do devcontainer, docker, dockercompose e docker desktop para vizualização dos containers.

  Vscode: 
  https://code.visualstudio.com/download
  
  Docker instalação:
  https://docs.docker.com/get-docker/

  Docker Compose instalação: 

  https://docs.docker.com/compose/install/

  Docker Desktop instalação: 

  https://docs.docker.com/desktop/install/linux-install/

  No vscode após ter a extensão do devcontainers use o atalho:

  CTRL + SHIFT + P

  ![image](https://github.com/HuanShan150/microject/assets/30788893/960af88c-c313-4938-be31-11377e702626)

  
  Selecione a opção Devcontainers: Rebuild and Reopen in Container 

  A primeira build é mais demorado. 

  Após finalizar a API já estará disponivel na porta 8080.

  Obs: Se não desejar usar devcontainer é pode-se rodar apenas com Docker e Docker Compose.

  Obs2: Necessario que o consumidor utilizer a localhost:5173, caso deseje usar outra porta adicionar no WebConfig.java

  Endpoints da API
  Clientes:
  
    POST /clientes: Adicionar um novo cliente
    GET /clientes: Listar todos os clientes
    GET /clientes/{id}: Buscar cliente por ID
    DELETE /clientes/{id}: Remover cliente por ID
  
  Projetos:
  
    POST /projetos: Adicionar um novo projeto
    GET /projetos: Listar todos os projetos
    GET /projetos/{id}: Buscar projeto por ID
    DELETE /projetos/{id}: Remover projeto por ID
    GET /projetos/cliente/{clienteId}: Listar projetos por cliente
  
  Atividades:
  
    POST /atividades: Adicionar uma nova atividade
    GET /atividades: Listar todas as atividades
    GET /atividades/{id}: Buscar atividade por ID
    DELETE /atividades/{id}: Remover atividade por ID
    GET /atividades/projeto/{projetoId}: Listar atividades por projeto
    PUT /atividades/editar/{id}: Atualizar uma atividade

Banco de Dados: 

  Cliente

ID (PK)          
Nome             
Email            


              
              |
              |
              v
Projeto

ID (PK)          
Nome             
Cliente_ID (FK)  
ProjetoStatus    


              
              |
              |
              v
Atividade

ID (PK)          
Descricao        
AtividadeStatus  
Projeto_ID (FK)  


Diagrama de Classes: 

Cliente

 - ID: Long       
 - Nome: String   
 - Email: String  

            
            |
            |
            v
Projeto

 - ID: Long                
 - Nome: String            
 - Cliente: Cliente        
 - ProjetoStatus: Enum     
 - Atividades: List<Atividade> 

            1
            |
            |
            v
Atividade

 - ID: Long                    
 - Descricao: String           
 - AtividadeStatus: Enum       
 - Projeto: Projeto            


AtividadeStatus (Enum)

 BACKLOG          
 EM_ANDAMENTO     
 EM_REVISAO       
 CONCLUIDO        


ProjetoStatus (Enum)

 ABERTO  
 EM_ANDAMENTO
 FECHADO          


