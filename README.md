## 1. Configuração do Projeto e Tecnologias Essenciais

- [x] Estrutura do Projeto: Criar um projeto Maven.
- [x] Linguagem de Programação: Utilizar Java 17.
- [x] Controle de Versão: Configurar um repositório no GitHub para controle de versões e trabalho em equipe.
- [x] Padrão de Arquitetura: Implementar o padrão MVP (Passive View).
- [ ] Tratamento de Exceções: Aplicar tratamento de exceções em todo o projeto.

## 2. Módulos e Dependências Externas

- [ ] Módulo de Log:
   - [ ] Criar um projeto Maven separado para o módulo de log.
   - [ ] Implementar o padrão de projeto Adapter neste módulo.
   - [ ] Utilizar o JitPack para incluir o módulo de log como uma dependência JAR no projeto principal.
- [ ] Validador de Senhas:
   - [ ] Incluir o validador de senhas via JitPack como uma dependência JAR, a partir do repositório github.com/claytonfraga/validadorsenha.

## 3. Interface Gráfica (UI)

- [ ] Tecnologia: Desenvolver a interface gráfica utilizando Java Swing.
- [ ] Padrão de Navegação: Utilizar o padrão MDI (Multiple Document Interface), permitindo que múltiplas janelas fiquem abertas simultaneamente.
- [ ] Atualização de Telas: Implementar o padrão Observer para que, ao alterar um dado, todas as janelas abertas que exibem esse dado sejam atualizadas automaticamente.
- [ ] Rodapé: Exibir o nome do usuário logado no rodapé da aplicação.
- [ ] CRUD com State: Seguir o padrão de navegação para CRUDs (Create, Read, Update, Delete) utilizando o padrão State, conforme o link do Whimsical fornecido.
- [ ] Padrão Command: Integrar o padrão Command ao padrão State.

## 4. Persistência de Dados

- [ ] Banco de Dados:
   - [ ] Utilizar SQLite ou H2 como sistema de banco de dados.
   - [ ] Garantir que o banco de dados não necessite de instalações ou configurações externas (sem Docker).
   - [ ] Implementar um mecanismo de carga inicial de dados (seeder).
   - [ ] Permitir a troca entre SQLite e H2 por meio de um arquivo de configuração para obter bônus na nota.
- [ ] Padrão Repository: Utilizar o padrão de projeto Repository para o acesso a dados.
- [ ] Restrição: Não utilizar frameworks de persistência como Hibernate.

## 5. Funcionalidades Centrais (Casos de Uso)

- [ ] Gerenciamento de Perfis de Usuário:
   - [ ] Implementar um cadastro de usuário com informações genéricas (nome, e-mail, credenciais).
   - [ ] O primeiro usuário cadastrado deve ser o administrador.
   - [ ] Permitir que usuários regulares solicitem perfis de vendedor e/ou comprador.
   - [ ] Exigir que o usuário escolha o contexto (compra ou venda) ao fazer login, caso possua ambos os perfis.
   - [ ] Desenvolver a funcionalidade "Manter perfil do vendedor" (CRUD).
   - [ ] Desenvolver a funcionalidade "Manter perfil do comprador" (CRUD).
- [ ] Catálogo e Itens:
   - [ ] Implementar a funcionalidade "Manter catálogo de itens" (CRUD), com limite de 30 anúncios por vendedor.
   - [ ] Gerar um Identificador Circular (ID-C) alfanumérico de 12 caracteres para novos itens.
   - [ ] Validar a republicação de um item usando um ID-C existente.
- [ ] Publicação e Precificação:
   - [ ] Criar o caso de uso "Publicar item".
   - [ ] Calcular o preço final aplicando descontos cumulativos com base nos defeitos informados.
   - [ ] Garantir que o preço final não seja inferior a 5% do preço-base.
- [ ] Transações:
   - [ ] Implementar o caso de uso "Enviar oferta de compra", permitindo propostas de 1% a 20% abaixo do preço final.
   - [ ] Implementar o caso de uso "Responder oferta".
   - [ ] Implementar o caso de uso "Finalizar transação", que ocorre após o aceite da oferta.
- [ ] Avaliação e Denúncia:
   - [ ] Desenvolver o caso de uso "Registrar avaliação textual" após a venda.
   - [ ] Implementar o caso de uso "Registrar denúncia de item".
- [ ] Rastreabilidade:
   - [ ] Criar a funcionalidade "Manter linha do tempo do item", registrando todos os eventos (publicação, oferta, venda, etc.).

## 6. Indicadores Ambientais (GWP e MCI)

- [ ] Cálculo de GWP (Global Warming Potential):
   - [ ] Implementar a fórmula de cálculo do GWP_base e GWP_avoided.
   - [ ] Utilizar a tabela de fatores de emissão fixa por material.
   - [ ] Ao finalizar a venda, tornar o GWP_avoided definitivo e somá-lo aos perfis do comprador e vendedor.
- [ ] Cálculo de MCI (Material Circularity Indicator):
   - [ ] Implementar o cálculo simplificado do MCI_item, que é igual ao fator de qualidade Q.
   - [ ] O fator de qualidade Q é calculado como 1−Σ d_j, onde d_j é a perda de utilidade de cada defeito.
   - [ ] Limitar a soma dos d_j a um máximo de 0,90.
- [ ] Processamento e Exibição:
   - [ ] Criar um serviço interno para "Processar indicadores ambientais".
   - [ ] Exibir os indicadores "CO2e evitado" e "Índice de circularidade" no catálogo.

## 7. Sistema de Reputação e Gamificação

- [ ] Processamento de Reputação: Criar um módulo para "Processar reputação e gamificação".
- [ ] Pontuação por Estrelas:
   - [ ] Atribuir pontos (frações de estrela) para ações específicas (cadastro de item, oferta, resposta rápida, etc.).
   - [ ] Implementar a evolução de níveis: Bronze → Prata → Ouro a cada 5 estrelas.
- [ ] Gamificação:
   - [ ] Insígnias Permanentes: Conceder insígnias (e 0,2 estrela) por ações inéditas ("Primeiro Anúncio", "Primeira Oferta", etc.).
   - [ ] Desafios Semanais: Implementar uma funcionalidade para o administrador gerenciar desafios que concedem 0,15 estrela.
   - [ ] Ranking de Crescimento: Gerar um ranking semanal dos usuários com maior ganho de estrelas.
   - [ ] Selos de Temporada: Implementar uma funcionalidade para o administrador gerenciar selos visuais trimestrais.
- [ ] Penalidades:
   - [ ] Aplicar penalidades (perda de 0,5 estrela) por descrição enganosa.
   - [ ] Bloquear usuários após três infrações.
   - [ ] Conceder o selo "Verificador Confiável" para usuários com alto índice de denúncias procedentes.

## 8. Funcionalidades do Administrador

- [ ] Dashboard: Criar uma tela de "Consultar dashboard" com gráficos (usando JFreeChart) sobre as métricas do sistema.
- [ ] Auditoria Semanal: Implementar uma rotina de auditoria para consolidar métricas e verificar a integridade dos dados.
- [ ] Exportação de Dados: Criar a funcionalidade "Exportar CSV de itens vendidos", restrita ao administrador.
- [ ] Gerenciamento de Tabelas:
   - [ ] Implementar CRUD para a "Tabela de defeitos e abatimentos".
   - [ ] Implementar CRUD para a "Tabela de fatores de emissão".

## 9. Logging

- [ ] Tela de Configuração: Criar uma tela que permita ao usuário escolher o formato do arquivo de log (CSV ou JSON) em tempo de execução.
- [ ] Formato de Log: Implementar a gravação de logs para criação, alteração, exclusão de itens, conclusão de transação e exportação de dados.
- [ ] Mensagens de Log: Seguir o formato de mensagem especificado, incluindo ID-C, operação, nome, data, hora e usuário.
- [ ] Tipos de Arquivo: O sistema deve suportar logs em formato CSV (com separador ";") e JSON.

## 10. Entrega do Projeto

- [ ] Documentação: Criar um documento no Google Docs descrevendo os requisitos implementados.
- [ ] Apresentação: Agendar e realizar uma apresentação do projeto em funcionamento para o professor.
- [ ] Formato da Entrega:
   - [ ] Realizar a entrega via GitHub, fornecendo o comando `git clone`.
   - [ ] Incluir o nome completo de todos os membros da equipe.
   - [ ] Todos os membros da equipe devem realizar a entrega com o mesmo conteúdo.
