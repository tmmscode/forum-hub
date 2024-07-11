<h1>Forum Hub</h1>

##  Sobre o projeto

Aplicação Backend de um Fórum de cursos, para criação de tópicos e respostas, com autenticação de usuários e perfis de acesso.

Este projeto foi desenvolvido durante a formação **"Oracle Next Education" (Especialização Back-End)**, em parceria com a **[Alura](https://www.alura.com.br/)**.

## Tecnologias utilizadas

- Java (JDK 21 "21.0.2")
- Spring
- Maven
- Lombok
- Hibernate (JPA)
- MySQL
- Flyway
- Springdoc

## Funções

- CRUD de Tópicos e respostas;
- CRUD de Cursos;
- CRUD de Usuários (e perfis de usuário);
- Autenticação de usuários para acesso de rotas da aplicação;

---
## Configuração

É necessário que sejam criadas algumas variáveis de ambiente na máquina onde será executada a aplicação:

```
"${DB_HOST}" (Onde será informado o endereço do banco de dados)
"${DB_NAME}" (Onde será informado o nome do banco de dados)
"${DB_USERNAME}" (Onde será informado o nome de usuário que irá fazer login no banco)
"${DB_PASSWORD}" (Onde será informado a senha do usuário que fará login no banco)

"${JWT_SECRET}" (Onde será informado o segredo para geração do token JWT (caso não informado, o segredo padrão será "12345"))

Obs.: Criar as variáveis sem aspas.
```

As informações das variáveis serão utilizadas no arquivo abaixo:
```
src/main/resources/application.properties  
```
A execução da aplicação se inicia pelo arquivo:
``` 
src/main/java/tmmscode/forumHub/ForumHubApplication.java
```
## Utilização

Para que as rotas da aplicação funcione corretamente, alguns registros precisam ser inseridos ao banco de dados:

- É necessário estar logado para acessar boa parte das rotas da aplicação;


- O primeiro usuário com perfil "ADMIN", deve ser definido diretamente por comando SQL no banco de dados (na tabela de junção "user_profile"), pois a rota de registro de novos usuários define por padrão, apenas o cargo "USER", sem a opção de modificação;

Obs.: A aplicação dispõe de um endpoint para modificação de perfis de usuário, que permite a definição de novos ADMINs, entretando, apenas usuários que já possuem esse cargo, podem ter acesso a esse endpoint

Exemplo da inclusão do perfil "ADMIN" para o primeiro usuário registrado no banco:
```
INSERT INTO user_profile (user_id, profile_id) VALUES ('1', '1');
```

- No mínimo um curso e um usuário precisa existir para que seja realizada a criação de tópicos e respostas.

Para que um curso seja registrado corretamente, ele precisa pertencer a uma categoria. As categorias podem ser configuradas no ENUM (diretório abaixo), que será usado para validação dos dados enviados para o endpoint de registro de novos cursos.

```
src/main/java/tmmscode/forumHub/domain/course/CourseCategory.java
```
Obs.: Para que um curso seja registrado pelos endpoints da aplicação, um usuário com permissões de "ADMIN" deverá estar logado.




---
## Informações adicionais e Curiosidades

Esse projeto foi fundamental para que eu pudesse entender melhor os processos do Spring para proteger uma aplicação.

Ao criar um filtro que verifica a identidade do usuário logado, aprendi também sobre o contexto de autorização, que foi essencial para que eu pudesse utilizá-lo para implementar de uma forma segura, a definição de "autores" dos tópicos e perguntas, sem que essa informação precise ser informada pelo usuário. Nesse caso, basta estar logado que a aplicação se encarregará até mesmo de verificar e validar as funções de edição e exclusão de tópicos/respostas para os reais autores.


---

## Ajustes futuros

Pretendo implementar testes unitários em todos os controladores.

---
Criado por: [Thiago de Melo Marçal da Silva](https://github.com/tmmscode)