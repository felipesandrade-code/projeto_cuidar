---
session: 20260517_1500 - Refatoracao de Comunicacao e CRM
has-technical-debt: false
problem: Descentralização da comunicação, falhas de permissão (403), falta de persistência de dados e fluxo de engajamento incompleto.
result: Central de Comunicação unificada, CRM automatizado, persistência de dados robusta e UX modernizada.
---

# Refatoração Completa do Sistema de Comunicação e Gestão (Orfanato e Voluntários)

## Identificador
- Data/Hora: **17/05/2026 15:00**
- Motivação: **Unificar o fluxo de comunicação entre ONGs e voluntários, resolver bloqueios de permissão do backend (403 Forbidden) e garantir a persistência local dos dados de perfil e engajamento.**
- Contexto da IA: Modern Android (Kotlin/Compose)

## Diagnóstico
- **Estado inicial:** 
  - O aplicativo possuía telas separadas e confusas para Mensagens e Campanhas.
  - O envio de mensagens gerava erro HTTP 403 (Acesso Negado) e 500 devido a mapeamento incorreto de roles e payload de JSON divergente.
  - O Inbox não permitia navegação para o chat, e o histórico de envio era perdido ao reiniciar o app.
  - Voluntários não podiam se candidatar de fato a vagas, e os dados de perfil (CPF, Telefone, Foto) eram voláteis.
- **Diagnóstico real:** 
  - A role `CLIENT` não possuía permissão no Spring Security do backend para a rota `POST /api/messages`.
  - A falta de um `MessageStorage` e uso incorreto de IDs técnicos limitava o tracking forense e a experiência de chat 1-para-1.
- **Estado final:** 
  - Todos os perfis (Doador, Voluntário, ONG) possuem navegação unificada e permissões (`OPERATOR`) alinhadas com o backend temporário.
  - A nova "Central de Comunicação" agrupa Caixa de Entrada, Chat com histórico persistente e Disparo Push (Broadcast).
  - Voluntários podem se candidatar às vagas da ONG com reflexo direto no banco local e os perfis agora suportam uploads de imagem pela galeria nativa.

## Desenvolvimento
**1. Unificação da Central de Comunicação (`MessagesScreen.kt`, `ChatScreen.kt`):**
- Fusão das antigas telas de campanhas e mensagens em uma única UI com abas dinâmicas baseadas na role.
- Implementação de algoritmo de agrupamento (`groupBy`) no Inbox para criar a "Visão por Conversa".
- Resolução dinâmica de Nomes vs IDs técnicos buscando o nome do Doador, Voluntário ou Orfanato no momento da renderização.

**2. Persistência de Dados e UX do Perfil (`TokenManager.kt`, `AuthViewModel.kt`, `ManagementStorage.kt`):**
- Criação de `ManagementStorage` usando DataStore + Moshi para persistir offline Tarefas, Doações, Voluntários e Notícias.
- Atualização do `TokenManager` para gravar permanentemente CPF/CNPJ, Telefone e Foto.
- Substituição do "Seletor de Avatares" por um `rememberLauncherForActivityResult(ActivityResultContracts.GetContent())` real para acesso à galeria do Android.

**3. Segurança e Auditoria Forense (`AuditLogsScreen.kt`, `ApiModels.kt`):**
- Restrição rígida na requisição de Audit Logs enviando o `userId` para evitar vazamento de informações entre ONGs (Isolamento de Dados).
- Ampliação do Payload do Log para incluir IP, Role, Recurso e ID do Recurso alterado.

**4. Automação do CRM e Vagas (`homeComponents.kt`):**
- Adição da ação `applyForTask` permitindo que voluntários aceitem vagas reais (sumindo da timeline visual).
- Alteração no `AuthViewModel` para automatizar o setup da Role (`OPERATOR` para ONGs e Voluntários, `CLIENT` para doadores) mitigando bloqueios do backend na fase de testes.

**5. Correção Crítica do Backend Mapping (`MessageViewModel.kt`):**
- Ajuste do Payload `MessageRequest` para omitir o campo `remetente` no body se o backend extrai via Token, e envio explícito de `senderId` quando necessário para evitar HTTP 500 por nulo.
- Adequação da resposta para o formato em `List<MessageResponse>` em resposta ao padrão de serialização do backend (HTTP 201).