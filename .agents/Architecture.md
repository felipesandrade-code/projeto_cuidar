# Arquitetura do Projeto WTC (Cuidar +)

Este documento descreve a arquitetura técnica, os padrões de design e as tecnologias utilizadas no projeto Android.

## 1. Stack Tecnológica Mobile
- **Linguagem:** Kotlin
- **Framework UI:** Jetpack Compose (Declarative UI)
- **Rede:** Retrofit 2 + OkHttp 4 (com Logging Interceptor)
- **Serialização:** Moshi (Kotlin Reflection adapter)
- **Imagens:** Coil-compose (Carregamento lazy e cache)
- **Injeção de Dependência:** ViewModel Factory manual (Escalável para Hilt se necessário)
- **Navegação:** Jetpack Navigation Compose

## 2. Padrão Arquitetural: MVVM
O projeto utiliza o padrão Model-View-ViewModel para garantir a separação de responsabilidades.

1. **View (Compose):** Observa o estado do ViewModel. Não contém lógica de negócio.
2. **ViewModel (Android ViewModel):** Mantém o estado da UI via `StateFlow` ou `MutableState`. Gerencia chamadas assíncronas com `viewModelScope`.
3. **Model (Data Classes):** Representação dos dados da API e de domínio.
4. **Repository/Storage:** Camada de abstração para dados (ex: `OrphanageStorage` via DataStore).

## 3. Fluxo de Dados (Data Flow)
1. **Action:** O usuário clica em um botão na `Screen`.
2. **Event:** A `Screen` chama um método no `ViewModel`.
3. **Execution:** O `ViewModel` dispara uma Coroutine para chamar a `ApiService`.
4. **State Update:** O resultado da API (sucesso ou erro) atualiza o `StateFlow`.
5. **Recomposition:** O Compose observa a mudança de estado e redesenha apenas o necessário.

## 4. Estrutura de Componentes
- **Screens:** Composables de alto nível que ocupam a tela toda. Recebem ViewModels.
- **Components:** Composables granulares e reutilizáveis (Botões, Cards, Logos). São "stateless" sempre que possível.
- **Navigation:** Centralizada no `AppNavHost.kt`. Define rotas e argumentos.

## 5. Convenções de API
- **Base URL:** Localhost via emulador (`10.0.2.2:8080`).
- **Autenticação:** JWT Token enviado via Bearer header no `ApiClient.kt`.
- **Rotas:** Seguem o padrão RESTful documentado no Swagger do backend.

---
*Este guia deve orientar a criação de novas funcionalidades para manter a consistência do app.*
