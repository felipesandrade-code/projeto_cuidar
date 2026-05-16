# Diretrizes de Acessibilidade Mobile (Android)

Este projeto segue os padrões de acessibilidade para garantir que todos os usuários possam navegar pelo aplicativo.

## 1. Semântica e TalkBack
- **Content Description:** Toda imagem (`Image`, `AsyncImage`) e botão de ícone deve possuir um `contentDescription` significativo. Se for decorativo, use `null`.
- **Merge Semantics:** Utilize o modificador `semantics(mergeDescendants = true)` em cards e itens de lista para que o TalkBack leia o bloco de informação de uma vez.
- **Labels:** Garanta que os rótulos de campos de formulário (`TextField`) estejam vinculados corretamente através do parâmetro `label`.

## 2. Toque e Interação
- **Target Size:** Todo elemento clicável deve ter uma área de toque mínima de **48x48dp**.
- **Estados Visuais:** Utilize `focus` e estados de clique claros para que o usuário saiba qual elemento está selecionado.

## 3. Contraste e Texto
- **Cores:** Utilize o contraste mínimo de **4.5:1** para textos normais e **3:1** para textos grandes.
- **Dynamic Type:** Respeite as configurações de tamanho de fonte do sistema usando unidades `sp` em vez de `dp` para textos.

## 4. Checklist de Acessibilidade
- [ ] O `contentDescription` descreve a ação ou o conteúdo da imagem?
- [ ] O leitor de tela segue uma ordem lógica de navegação?
- [ ] Existe feedback auditivo ou tátil para ações críticas (erro/sucesso)?

---
*A acessibilidade não é um extra, é parte do compromisso do Projeto Cuidar.*
