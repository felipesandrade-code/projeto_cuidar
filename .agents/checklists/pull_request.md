# Checklist de Pull Request (Android)

Este checklist deve ser validado antes de qualquer merge para garantir a qualidade do app.

## 1. UI e Compose
- [ ] O novo Composable utiliza `Modifier` como parâmetro?
- [ ] Foi verificado se há recomposições desnecessárias (Layout Inspector)?
- [ ] O layout é responsivo e funciona em diferentes tamanhos de tela?
- [ ] O TalkBack descreve corretamente os novos elementos?

## 2. Lógica e Estado
- [ ] A lógica de negócio está no ViewModel?
- [ ] O estado é exposto via `StateFlow`?
- [ ] As chamadas assíncronas usam `viewModelScope`?
- [ ] O tratamento de erros de rede está implementado?

## 3. Segurança e Performance
- [ ] Não há chaves de API ou segredos no código?
- [ ] As imagens estão sendo carregadas de forma assíncrona (Coil)?
- [ ] Não há vazamentos de memória (Memory leaks)?

## 4. Testes
- [ ] A funcionalidade foi testada manualmente no emulador/dispositivo?
- [ ] Foram adicionados testes unitários para a nova lógica de ViewModel?

---
*Assinar este checklist confirma que o código está pronto para os usuários.*
