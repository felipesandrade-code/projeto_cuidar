# Padrões de Codificação Kotlin & Jetpack Compose

Este documento define as regras de estilo e boas práticas para manter a consistência do código no projeto.

## 1. Convenções de Nomenclatura
- **Funções Composable:** `PascalCase` (ex: `MyCustomButton`).
- **Variáveis e Funções:** `camelCase` (ex: `val userEmail`, `fun fetchData()`).
- **Constantes:** `SCREAMING_SNAKE_CASE` (ex: `const val BASE_URL`).
- **Interfaces:** Começar com `I` é desencorajado no Kotlin, prefira o nome direto.

## 2. Estrutura do Compose
- **State Hoisting:** Mova o estado para cima na hierarquia sempre que possível para tornar os componentes mais testáveis e reutilizáveis.
- **Modifiers:** Deixe o `Modifier` como o primeiro argumento opcional de qualquer Composable customizado.
- **Optimization:** Use `remember` para preservar valores entre recomposições e `Immutable` para classes de dados enviadas ao Compose.

## 3. Padrões Kotlin
- **Null Safety:** Utilize os operadores `?`, `!!` (com cautela) e `?:` (Elvis operator) para evitar NullPointerExceptions.
- **Extension Functions:** Utilize-as para estender funcionalidades de classes legadas ou de framework.
- **Coroutines:** Sempre especifique o Dispatcher correto (`Main` para UI, `IO` para rede, `Default` para processamento pesado).

## 4. Gerenciamento de Dependências
- **Versions.toml:** Utilize o catálogo de versões para centralizar a gestão de bibliotecas.
- **Build.gradle.kts:** Mantenha os scripts limpos e utilize plugins para automatizar tarefas.

---
*Manter o padrão de código facilita a colaboração e evita bugs silenciosos.*
