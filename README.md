<img src="https://github.com/user-attachments/assets/d7b57fc6-66c1-4b6b-a4ca-e4545a1d5dc0"> 

# 🌱 Div das Plantas API - Onde seus pedidos florescem com Spring!

Bem-vindo(a) à API do **Div das Plantas**, o ecommerce mais verde do backend!  
Aqui, cada request brota com cuidado, e cada response é regado com carinho.  
Este projeto foi feito com `Spring Boot` porque, claro, **plantas gostam de Spring**! 🌼

---

## 🚀 O que é isso que brotou aqui?

A **Div das Plantas API** é uma aplicação RESTful que gerencia um sistema de ecommerce voltado para a venda de plantas ornamentais. O sistema foi dividido em camadas seguindo boas práticas de arquitetura e cultivo:

- **Entity** 🌿  
- **DTO** 🌾  
- **Service** ☀️  
- **Repository** 💧  
- **Controller** 🌻  

---

## 🍃 Endpoints que dão fruto

### 🌸 Categorias
- `GET /categorias` — Lista todas as categorias de plantas
- `GET /categorias/{id}` — Lista as categorias de plantas por id
- `POST /categorias` — Planta uma nova categoria (apenas por um jardineiro 🌱) 
- `PUT /categorias/{id}` — Aduba uma categoria existente (apenas por um jardineiro 🌱)
- `DELETE /categorias/{id}` — Arranca a categoria pela raiz (apenas por um jardineiro 🌱)

### 🌼 Produtos
- `GET /produtos` — Lista todos os produtos floridos
- `GET /produtos/{id}` — Lista os produtos por id
- `POST /produtos` — Cadastra um novo produto já com sua categoria enraizada (apenas por um jardineiro 🌱)
- `PUT /produtos/{id}` — Atualiza informações da muda (apenas por um jardineiro 🌱)
- `DELETE /produtos/{id}` — Remove o produto do canteiro (apenas por um jardineiro 🌱)

### 🌿 Clientes
- `GET /clientes` — Mostra todos os jardineiros cadastrados (apenas por um jardineiro 🌱)
- `GET /clientes/{id}` — Lista os clientes por id (apenas por um jardineiro 🌱)
- `POST /clientes` — Cadastra um novo cliente e consulta o endereço com o ViaCEP 🌐
- `PUT /clientes/{id}` — Atualiza dados e envia um email perfumado ✉️
- `DELETE /clientes/{id}` — Cancela o registro do cliente

### 🌳 Pedidos
- `GET /pedidos` — Mostra todos os pedidos realizados (apenas por um jardineiro 🌱)
- `GET /pedidos/{id}` — Consulta um pedido pelo número e calcula o valor total
- `POST /pedidos` — Cria um novo pedido vinculado a um cliente e várias plantinhas
- `PUT /pedidos/{id}` — Atualiza o status do pedido (enum 🌈) (apenas por um jardineiro 🌱)
- `DELETE /pedidos/{id}` — Remove o pedido da horta

---

## 🛠️ Tecnologias Fertilizantes

- Java 17 ☕
- Spring Boot 3 🌸
- Spring Data JPA
- Spring Validation
- Spring Mail (para espalhar e-mails como sementes)
- Swagger (documentação clara como água 💧)
- API ViaCEP (para regar os dados com endereços reais)
- Banco de dados: PostgreSQL

---

## 🧪 Validações (Regando com regras)

### Cliente:
- `nome` não pode ser nulo ou em branco
- `telefone` precisa de um formato válido
- `email` deve florescer corretamente com formato padrão
- `cpf` deve ser válido (sem falhas no DNA do cliente)

---

## 🧾 Enum e Tratamento de Exceções

Utilizamos enums para os **status do pedido**:  
`RECEBIDO`, `PENDENTE`, `PROCESSANDO`, `EMPACOTANDO`, `EM_TRANSITO`, `ENTREGUE`, `CANCELADO`

E pra não deixar bugs crescerem como ervas daninhas:
- Uma classe com `@ControllerAdvice` lida com erros
- Exceções personalizadas podadas com carinho para cada falha da API

---

## 🌍 Serviço Externo: ViaCEP

Sempre que um cliente se registra, fazemos uma chamada à API do ViaCEP para garantir que o endereço está no mapa (e no jardim).

---

## 💐 Partes Individuais: Feitas com carinho pelos nossos jardineiros

### 🌼 Avaliações de Produtos - Pela jardineira: Julya Werneck 🌷
Permite que clientes que compraram um produto deixem sua opinião sincera sobre ele. Cada planta merece seu momento de feedback! 🌿✨  
Somente clientes que realmente cultivaram a plantinha (ou seja, compraram o produto) podem avaliá-la.

🌟 **Rotas das Avaliações:**

- **GET /avaliacoes** — Lista todas as avaliações do jardim  
- **GET /avaliacoes/{id}** — Mostra uma avaliação específica colhida com carinho  
- **POST /avaliacoes** — Planta uma nova avaliação (apenas para quem já cultivou 🌱)  
- **PUT /avaliacoes/{id}** — Rega uma avaliação existente com novas palavras  
- **DELETE /avaliacoes/{id}** — Remove uma avaliação seca do jardim

### 💌 Lista de Desejos - Pela jardineira: Lívia Raissinger 🌻
Porque todo jardineiro também sonha com a próxima plantinha, né? 🌱✨
A *lista de desejos* permite que clientes armazenem produtos que desejam adquirir futuramente. Ela é vinculada ao cliente e gerenciada automaticamente pela aplicação. Se ainda não existir, a lista é criada na primeira interação do cliente com esse recurso.
Os produtos adicionados à lista não se repetem, garantindo integridade e organização. As respostas da API trazem dados úteis sobre os itens, como nome, preço e categoria — tudo para cultivar a melhor experiência.
Assim, mesmo o que ainda não foi comprado já começa a florir no sistema 🌸💻


🌟 **Rotas da Lista de Desejos:**

- **GET /DivDasPlantas/listadesejos/{clienteId}** — Mostra todos os produtos desejados por um cliente específico.
- **POST /DivDasPlantas/listadesejos/{clienteId}/produtos/{produtoId}** — Adiciona um produto à lista de desejos do cliente, caso ainda não esteja nela.
- **DELETE /DivDasPlantas/listadesejos/{clienteId}/produtos/{produtoId}** —Remove um produto específico da lista do cliente, com retorno personalizado indicando qual plantinha foi retirada.
- **DELETE /DivDasPlantas/listadesejos/{clienteId}/limpar** — Remove todos os produtos da lista de desejos do cliente, deixando o espaço livre para novas vontades brotarem 🌾
  
### 📄✨ Emissão de Notas Fiscais Automatizada - Pelo jardineiro: Nikolas Wintter 🌹
Sempre que um pedido floresce 🌸 no sistema, uma **Nota Fiscal** é gerada automaticamente em formato **PDF** 🧾 — como uma pétala registrada com todo cuidado!  
Essa nota garante **transparência**, **organização** e aquele toque profissional que encanta qualquer cliente! 💚
O arquivo é magicamente salvo na pasta `notasFiscais`, como uma lembrança digital de cada compra realizada 🛍️.

#### 🧾 O que vem na nota? Veja só esse buquê de informações:
- **Identificação do Pedido** — Cada pedido com seu número único e especial 🔢  
- **Dados do Cliente** — Nome completo 🪪 e CPF 📎 para deixar tudo certinho no jardim da legalidade 🌿  
- **Data e Hora da Compra** — Formatados com carinho para eternizar o momento 🗓️🕒  
- **Status do Pedido** — Mostra se o pedido está brotando 🌱, a caminho 🚛 ou já chegou com amor na porta 🏡  
#### 🌸 Lista de Produtos Comprados:
- Nome do item  
- Quantidade  
- Valor unitário  
- Subtotal por item  
#### 💰 Totais do Pedido:
- Descontos aplicados (se a primavera trouxe promoção 🌸)  
- Valor final pago com carinho  
📌 Essa funcionalidade garante que cada pedido seja **documentado com cuidado** , trazendo praticidade, beleza e confiança para o seu sistema 🌻✨

---

## 💚 Time Jardineiro

Desenvolvido com carinho e muitos `commits` pelos devs:

- [Julya Werneck🌷](https://www.linkedin.com/in/julya-werneck-b166892bb/)  
- [Nikolas Wintter 🌹](https://www.linkedin.com/in/nikolas-wintter-2608a8317/)
- [Paulo Ricardo 🌾](https://www.linkedin.com/in/paulorccardoso/)
- [João Pedro Dias 🍀](https://www.linkedin.com/in/joão-pedro-dias-rodrigues-27b6801b8)
- [Lívia Raissinger 🌻](https://www.linkedin.com/in/liviaraissinger/)
- [Aline Cabral de Azevedo 🌼](https://www.linkedin.com/in/alinemielli/)

---
