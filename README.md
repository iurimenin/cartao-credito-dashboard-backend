# Cartão Dash - Backend

Sistema para importação e organização de gastos de cartão de crédito via arquivo CSV, com autenticação JWT, desenvolvido em Kotlin e Spring Boot.

## Funcionalidades
- Importação de extratos de gastos via CSV
- Cadastro e consulta de categorias de despesas
- Cadastro e consulta de lojas (associadas a categorias)
- Cadastro e autenticação de usuários (JWT)
- Consulta de gastos filtrados
- Segurança com Spring Security

## Tecnologias Utilizadas
- Kotlin
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Flyway
- OpenCSV
- Gradle

## Configuração

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/iurimenin/cartao-credito-dashboard-backend
   cd cartao-credito-dashboard-backend
   ```

2. **Configure o banco de dados PostgreSQL:**
   - Crie um banco chamado `cartao_db`.
   - Ajuste as credenciais no arquivo `src/main/resources/application.yml`.

3. **Gere uma chave JWT forte (mínimo 32 caracteres para HS256, 64 para HS512) e configure em `application.yml`:**
   ```yaml
   jwt:
     secret: "sua-chave-secreta-super-segura..."
     expiration: 3600000
   ```

4. **Rode as migrations:**
   - O Flyway executa automaticamente ao iniciar a aplicação.

5. **Build e execute o projeto:**
   ```bash
   ./gradlew bootRun
   ```

## Uso da API

### Cadastro de Usuário
```http
POST /auth/register
Content-Type: application/json
{
  "username": "usuario",
  "password": "senha"
}
```

### Login
```http
POST /auth/login
Content-Type: application/json
{
  "username": "usuario",
  "password": "senha"
}
```
**Resposta:**
```json
{"token": "<JWT>"}
```

### Importação de CSV de Gastos
```http
POST /gastos/import
Authorization: Bearer <JWT>
Content-Type: multipart/form-data
file: <arquivo.csv>
```
- O CSV deve ser delimitado por `;` e conter as colunas: data, nomeLoja, ..., valor (ajuste conforme seu layout).

### Cadastro de Categoria
```http
POST /categorias
Authorization: Bearer <JWT>
Content-Type: application/json
{
  "nome": "Mercado"
}
```

### Cadastro de Loja
```http
POST /lojas
Authorization: Bearer <JWT>
Content-Type: application/json
{
  "nome": "Supermercado X",
  "categoriaId": 1
}
```

### Consulta de Gastos
```http
GET /gastos
Authorization: Bearer <JWT>
```

## Testes
Execute:
```bash
./gradlew test
```

## Observações
- Lojas não cadastradas durante a importação são criadas automaticamente na categoria "Outros".
- O campo `loja` em `Gasto` é obrigatório.

---

Desenvolvido por [Iuri Menin]. 