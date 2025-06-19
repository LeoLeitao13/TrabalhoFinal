[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/j-bTVF6H)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19806224)
# 🧪 Trabalho Final — Sistema de Gestão de Eventos Acadêmicos

## 🎯 Objetivo

Desenvolver uma **API RESTful** em **Spring Boot + JPA (Hibernate)** para gerenciar eventos acadêmicos, aplicando boas práticas de design de APIs, uso de relacionamentos JPA, status HTTP corretos e lógica de negócio mais avançada do que um simples CRUD.

---

## 📚 Contexto

A universidade está organizando eventos como **palestras**, **workshops** e **semanas acadêmicas**. Os alunos podem se **inscrever** nesses eventos, que são organizados por **departamentos**. Cada evento pode ter **vários palestrantes** e **limite de vagas**.

---

## 📌 Requisitos Funcionais

### Entidades e Relacionamentos

- **Evento**
  - `id`, `nome`, `descricao`, `data`, `limiteParticipantes`
  - Relacionamentos:
    - ManyToMany com `Palestrante`
    - ManyToOne com `Departamento`
    - OneToMany com `Inscricao`

- **Departamento**
  - `id`, `nome`, `sigla`, `responsavel`

- **Palestrante**
  - `id`, `nome`, `miniCurriculo`, `instituicao`

- **Aluno**
  - `id`, `nome`, `matricula`, `curso`

- **Inscricao**
  - `id`, `dataInscricao`
  - ManyToOne com `Evento`
  - ManyToOne com `Aluno`

---

## ⚙️ Regras de Negócio

1. Um aluno só pode se inscrever em um evento se houver vagas disponíveis.  
2. Um aluno **não pode se inscrever mais de uma vez** no mesmo evento.  
3. Um aluno **não pode se inscrever em dois eventos com data e horário conflitantes**.  
4. A listagem de eventos deve informar: número de inscritos e se está lotado ou com vagas disponíveis.  
5. Um relatório por departamento deve informar: total de eventos organizados e total de inscritos.  
6. **Eventos com data já passada não devem permitir novas inscrições.**  
7. **Ao excluir um evento, todas as inscrições associadas devem ser removidas automaticamente.**  
8. **Não deve ser possível excluir um palestrante que esteja vinculado a algum evento.**  
9. **Cada evento deve ter no mínimo um palestrante vinculado. Não é permitido criar eventos sem palestrantes.**  
10. **A inscrição deve registrar a data e hora exata no momento da submissão automaticamente (servidor).**
11. O cancelamento de uma inscrição deve apenas **trocar o status da inscrição** de ativo para cancelado

---

## 🔗 Rotas da API (padrão REST)

> As rotas a seguir devem ser **seguidas rigorosamente**. **Desvios no padrão podem resultar em desconto na nota**.

### 🎫 Eventos

- `GET /events` – Lista todos os eventos com número de inscritos e status de vagas  
- `GET /events/{id}` – Retorna os detalhes de um evento específico  
- `POST /events` – Cria um novo evento  
- `PUT /events/{id}` – Atualiza os dados de um evento existente  
- `DELETE /events/{id}` – Remove um evento do sistema  

### 🧑‍🏫 Palestrantes

- `GET /speakers` – Lista todos os palestrantes cadastrados  
- `POST /speakers` – Cadastra um novo palestrante  
- `GET /speakers/{id}` – Exibe os dados de um palestrante específico  
- `PUT /speakers/{id}` – Atualiza os dados de um palestrante  
- `DELETE /speakers/{id}` – Remove um palestrante (desde que não esteja vinculado a eventos)  

### 🏛 Departamentos

- `GET /departments` – Lista todos os departamentos  
- `POST /departments` – Cria um novo departamento  
- `GET /departments/{id}` – Retorna os dados de um departamento específico  
- `GET /departments/{id}/report` – Retorna um relatório com total de eventos e inscritos do departamento  
- `PUT /departments/{id}` – Atualiza os dados de um departamento  
- `DELETE /departments/{id}` – Remove um departamento do sistema  

### 🎓 Alunos

- `GET /students` – Lista todos os alunos cadastrados  
- `POST /students` – Cadastra um novo aluno  
- `GET /students/{id}` – Retorna os dados de um aluno específico  
- `PUT /students/{id}` – Atualiza os dados de um aluno  
- `DELETE /students/{id}` – Remove um aluno do sistema  

### 📝 Inscrições

- `POST /events/{idEvento}/registrations` – Realiza a inscrição de um aluno em um evento (informar `idAluno` no body)  
- `GET /students/{idAluno}/registrations` – Lista os eventos em que o aluno está inscrito  
- `DELETE /registrations/{id}` – Cancela a inscrição com base no ID da inscrição

---

## ✅ Critérios de Avaliação

| Critério | Pontuação |
|---------|-----------|
| Modelagem correta dos relacionamentos (JPA) | 1.0 pt |
| Implementação das regras de negócio | 2.0 pts |
| Uso adequado de status HTTP e validações | 1.0 pt |
| Uso correto do padrão REST nas rotas (**obrigatório**) | 1.0 pt |
| Organização do projeto e boas práticas (camadas, responsabilidades, pacotes) | 1.0 pt |

> **Importante:** O uso de rotas fora do padrão definido, ou que não respeitem a semântica REST, implicará **desconto de nota**.

---

## 📆 Datas, Forma de Entrega e Artefatos

- **Data final para entrega:** até **terça-feira, 24 de junho de 2025, às 23:59**.  
- **Forma de entrega:** o projeto deve ser entregue **exclusivamente pelo GitHub Classroom**.  
- **Atenção:** entregas realizadas por e-mail, drive, pendrive, ou qualquer outro meio **não serão consideradas** e a nota será automaticamente **zerada**.

### 📦 Artefatos obrigatórios

1. **Código-fonte completo** no repositório.
2. Um arquivo chamado `api.http` na **raiz do projeto**, a documentação de uso da API com:
   - Chamadas `GET`, `POST`, `PUT`, `DELETE`
   - Headers necessários
   - Bodies de requisição (quando aplicável)

💡 **Dica:** Se você nunca criou um arquivo `.http` antes, pode consultar a documentação da extensão REST Client para VSCode:  
🔗 [https://marketplace.visualstudio.com/items?itemName=humao.rest-client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)

---

## 🧪 Exemplo de `api.http`

```http
### Obter todos os eventos
GET http://localhost:8080/eventos
Accept: application/json

###

### Criar um novo evento
POST http://localhost:8080/eventos
Content-Type: application/json

{
  "nome": "Semana Acadêmica de Engenharia",
  "descricao": "Evento com palestras e oficinas técnicas.",
  "data": "2025-06-25T19:00:00",
  "limiteParticipantes": 100,
  "idDepartamento": 1,
  "idsPalestrantes": [2, 4]
}
