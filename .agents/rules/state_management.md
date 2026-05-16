# Regras de Gestão de Estado e Rede

Este documento orienta como o aplicativo deve lidar com dados voláteis e persistentes.

## 1. Fluxo de Estado (UDF - Unidirectional Data Flow)
- **ViewModel as Source of Truth:** Apenas o ViewModel deve conter a lógica para alterar o estado da UI.
- **Exposição de Estado:** Utilize `StateFlow` ou `MutableState` privados no ViewModel e exponha-os como `StateFlow` somente leitura.
- **Consumo na UI:** Utilize `collectAsStateWithLifecycle()` para garantir que o fluxo de dados pare quando o app estiver em background.

## 2. Comunicação de Rede (Retrofit)
- **Definição de Endpoints:** Devem residir em `ApiService.kt`.
- **Tratamento de JSON:** Utilize Moshi para mapear objetos JSON diretamente para `data classes`.
- **Tratamento de Erros:**
    - Erros 4xx/5xx devem ser capturados e convertidos em mensagens amigáveis.
    - Utilize `Result` ou `sealed classes` para representar estados de Sucesso, Carregamento e Erro.

## 3. Persistência Local (DataStore)
- **Preferência:** Use DataStore para dados de configuração, tokens de sessão e pequenos caches.
- **Repositórios:** Isole o uso do DataStore dentro de classes Repository ou Storage (ex: `OrphanageStorage`).

---
*Dados bem gerenciados garantem uma experiência de usuário fluida e sem bugs de sincronia.*
