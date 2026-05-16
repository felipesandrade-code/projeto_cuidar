# Projeto Cuidar (WTC) - Contexto do Agente

Este arquivo serve como o guia fundamental para o agente de IA e desenvolvedores humanos, descrevendo a visão geral do projeto, stack tecnológica e padrões de desenvolvimento.

## 1. Visão Geral
O **Projeto Cuidar (WTC)** é um aplicativo Android desenvolvido para aproximar pessoas de orfanatos e instituições de caridade. O objetivo é facilitar doações, inscrições de voluntários e a comunicação direta entre usuários e instituições para gerar impacto social positivo.

## 2. Stack Tecnológica
- **Linguagem:** Kotlin 2.1.10
- **UI Framework:** Jetpack Compose (Modern Declarative UI)
- **Arquitetura:** MVVM (Model-View-ViewModel) com StateFlow e Coroutines.
- **Rede:** Retrofit 2.9.0, OkHttp, Moshi (para JSON).
- **Imagens:** Coil (carregamento assíncrono de imagens dinâmicas).
- **Mapas:** Google Maps SDK for Android & Compose Maps library.
- **Persistência Local:** DataStore (para configurações e cache simples).

## 3. Estrutura de Diretórios (app/src/main/java/br/com/fiap/projetocuidar/)
- `Screens/`: Composable que representam telas inteiras (ex: `HomeScreen`, `ChatScreen`).
- `components/`: Componentes de UI reutilizáveis divididos por contexto (ex: `buttonsComponent`, `home/`, `login/`).
- `data/`: Modelos de dados, ViewModels e lógica de armazenamento local.
- `navigation/`: Configuração de rotas e NavHost (`AppNavHost`).
- `network/`: Cliente de API (`ApiClient`) e definição de endpoints (`ApiService`).
- `util/`: Utilitários, validadores e transformações visuais.

## 4. Fluxo de Desenvolvimento
1. **ViewModel-Driven UI:** O estado da tela deve ser gerenciado por um `ViewModel` usando `StateFlow` ou `mutableStateOf`.
2. **Componentização:** Prefira criar pequenos componentes reutilizáveis em `components/` em vez de telas gigantes.
3. **Navegação Segura:** Use o `NavController` e defina rotas claras em `AppNavHost.kt`.
4. **Tratamento de Erros:** Sempre implemente `try-catch` em chamadas de rede e mostre feedbacks claros para o usuário (Toasts ou Snakbars).

## 5. Documentação Adicional
Consulte a pasta `.agents/` para diretrizes específicas:
- `.agents/Architecture.md`: Detalhes sobre a implementação MVVM e Compose.
- `.agents/rules/coding_standards.md`: Padrões de codificação Kotlin/Compose.
- `.agents/instructions/android_expert.md`: Persona e mindset para desenvolvimento mobile.

---
*Este documento deve ser atualizado sempre que houver mudanças estruturais significativas no sistema.*
