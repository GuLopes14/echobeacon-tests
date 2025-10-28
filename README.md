# 🏍️ Echo Beacon MVC

O projeto **Echo Beacon** foi desenvolvido para a empresa **Mottu** com o objetivo de implementar uma solução tecnológica que melhore a organização e localização das motos no pátio da empresa. A solução integra hardware, software e banco de dados para facilitar a gestão e identificação de veículos de forma eficiente.

---

# 👔 Integrantes
* **Gustavo Lopes Santos da Silva** - RM: 556859
* **Renato de Freitas David Campiteli** - RM: 555627
* **Gabriel Santos Jablonski** - RM: 555452

## 🛠️ Descrição do Projeto

O projeto em desenvolvimento para a empresa **Mottu** visa implementar uma solução tecnológica para melhorar a organização e a localização das motos no pátio da empresa, facilitando a gestão e a identificação de cada veículo de forma mais eficiente. O sistema será composto por uma série de componentes integrados, incluindo **Arduino**, um **aplicativo móvel** e um **banco de dados centralizado**.

A solução será composta por pequenas placas eletrônicas, chamadas de **"EchoBeacon"**, que serão instaladas em cada moto. Essas placas conterão:
- Um **sistema de som** (buzzer).
- Um **LED** para sinalização visual.

Quando uma moto chega ao pátio, informações como **placa**, **chassi** e detalhes sobre qualquer problema específico do veículo serão registradas em um banco de dados integrado. Esses dados poderão ser acessados por um sistema desenvolvido em **Java**.

Além disso, os funcionários responsáveis pela organização e monitoramento das motos no pátio terão acesso a um **aplicativo móvel**, que estará conectado ao banco de dados. Através desse aplicativo, eles poderão:
- Consultar informações detalhadas sobre as motos, como **placa**, **chassi** e **problemas**.
- Ativar o **buzzer** e/ou o **LED** da moto selecionada, emitindo um som e sinal visual para facilitar sua localização, mesmo em um ambiente com várias motos.

Essa solução visa resolver o problema de localizar rapidamente as motos no pátio. Sem uma identificação clara e imediata, os funcionários enfrentam dificuldades para encontrar a moto correta entre tantas outras. Com a implementação desse sistema, a **Mottu** poderá organizar melhor suas motos e otimizar o tempo gasto na identificação e localização dos veículos dentro do pátio, garantindo uma gestão mais ágil e eficiente.

---

## 📽️ Vídeo de demonstração das funcionalidades do sistema: [Clique aqui](https://www.youtube.com/watch?v=-w6mVJg-em0)

---
## 🎯 Objetivo

- **Facilitar a localização de motos no pátio da empresa.**
- **Otimizar o tempo dos funcionários na identificação de veículos.**
- **Garantir uma gestão mais eficiente e organizada.**

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Security com OAuth2 (Google)**
- **Thymeleaf** (Template Engine)
- **PostgreSQL** (Banco de dados)
- **Flyway** (Migração de dados)
- **Docker Compose** (Containerização)
- **Gradle** (Gerenciador de dependências)

---

## 📋 Pré-requisitos

Antes de executar a aplicação, certifique-se de ter instalado:

- **Java 21** ou superior
- **Docker** e **Docker Compose**
- **Git** (para clonar o repositório)

---

## ⚙️ Configuração

### 1. Clone o repositório
```bash
git clone https://github.com/GuLopes14/echobeacon-mvc.git
cd echobeacon-mvc
```

### 2. Configuração do Google OAuth2
Para utilizar a autenticação com Google, você precisa:

1. **Acesse o [Google Cloud Console](https://console.cloud.google.com/)**
2. **Crie um novo projeto** ou selecione um existente
3. **Ative as APIs necessárias:**
   - Vá em **"APIs e Serviços"** → **"Biblioteca"**
   - Procure por **"Google+ API"** e ative
   - Procure por **"People API"** e ative (recomendado)
4. **Configure o OAuth2:**
   - Vá em **"APIs e Serviços"** → **"Credenciais"**
   - Clique em **"Criar credenciais"** → **"ID do cliente OAuth 2.0"**
   - Escolha **"Aplicação web"**
   - Configure as **URLs de redirecionamento autorizadas:**
     ```
     http://localhost:8080/login/oauth2/code/google
     ```
5. **⚠️ IMPORTANTE - Copie as credenciais:**
   - Após criar, será exibida uma janela com:
     - **🔑 ID do cliente** (Client ID) - **COPIE este valor**
     - **🔐 Chave secreta do cliente** (Client Secret) - **COPIE este valor**
   - **💡 Dica:** Você pode acessar essas informações novamente clicando no ícone de download (⬇️) ao lado da credencial criada

### 3. Variáveis de Ambiente

**Windows (PowerShell):**
```powershell
$env:GOOGLE_CLIENT_ID="cole_aqui_o_client_id_copiado"
$env:GOOGLE_CLIENT_SECRET="cole_aqui_o_client_secret_copiado"
$env:ADMIN_EMAILS="seu_email@gmail.com,outro_admin@exemplo.com"
```

**Linux/Mac:**
```bash
export GOOGLE_CLIENT_ID="seu_google_client_id_aqui"
export GOOGLE_CLIENT_SECRET="seu_google_client_secret_aqui"
export ADMIN_EMAILS="admin@exemplo.com,outro_admin@exemplo.com"
```

---

## 🛠️ Instalação e Execução

### Método 1: Rodando com extensão do vs code ou no intellij
- **VSCODE**: Clique no botão **"Run"** que aparece acima da classe `EchobeaconApplication`.
- **INTELLIJ**: Clique no botão **▶️ Run** ao lado da classe `EchobeaconApplication`.

### Método 2: Usando Docker Compose 

1. **Inicie o banco de dados PostgreSQL:**
```bash
docker-compose up -d
```

2. **Execute a aplicação:**
```bash
# Windows
.\gradlew bootRun

# Linux/Mac
./gradlew bootRun
```

---

## 🌐 Acesso à Aplicação

### URLs da Aplicação
- **URL Principal:** http://localhost:8080
- **Login:** http://localhost:8080/login


### 🔐 Autenticação
A aplicação utiliza **OAuth2 com Google** para autenticação. Para acessar:

1. Acesse http://localhost:8080
2. Clique em **"Entrar com Google"**
3. Faça login com sua conta Google
4. Você será redirecionado para a página principal

### 👑 Acesso de Administrador
Usuários configurados na variável `ADMIN_EMAILS` terão privilégios administrativos e poderão:
- Cadastrar novos EchoBeacons
- Gerenciar motos
- Vincular EchoBeacons às motos

---

## 📊 Funcionalidades

### Para Usuários Comuns:
- ✅ Login com Google
- ✅ Visualizar perfil
- ✅ Listar motos disponíveis

### Para Administradores:
- ✅ Todas as funcionalidades de usuário comum
- ✅ Cadastrar EchoBeacons
- ✅ Cadastrar motos
- ✅ Editar informações de motos
- ✅ Vincular EchoBeacons às motos
- ✅ Gerenciar sistema completo

---

## 🗃️ Banco de Dados

A aplicação utiliza **PostgreSQL** e o **Flyway** para migração automática do banco de dados. As migrações estão localizadas em:
```
src/main/resources/db/migration/
```

### Estrutura das Tabelas:
- **echo_beacon** - Armazena informações dos dispositivos EchoBeacon
- **moto** - Contém dados das motocicletas
- **echobeacon_user** - Informações dos usuários do sistema

---
