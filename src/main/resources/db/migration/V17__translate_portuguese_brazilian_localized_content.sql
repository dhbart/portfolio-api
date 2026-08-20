UPDATE hero
SET greeting = 'Olá, eu sou',
    title = 'Desenvolvedor de Software • Tech Lead • Analista de Negócios • Product Owner',
    description = 'Gosto de resolver problemas de negócio por meio da tecnologia, construir soluções escaláveis e liderar equipes de alta performance.',
    primary_button_label = 'Ver projetos',
    secondary_button_label = 'Baixar currículo'
WHERE locale = 'pt-BR';

UPDATE about
SET title = 'Sobre mim',
    description = 'Sou um profissional de tecnologia focado em resolver problemas de negócio com soluções simples, escaláveis e relevantes.'
WHERE locale = 'pt-BR';

UPDATE project
SET title = CASE id
        WHEN 1 THEN 'Portfólio Bartholdy'
        WHEN 2 THEN 'Budgeting — API financeira por voz'
        WHEN 3 THEN 'Crescer Juntos'
        WHEN 4 THEN 'Marketplace de ingressos'
        WHEN 5 THEN 'API de exploração de filmes'
    END,
    headline = CASE id
        WHEN 1 THEN 'Um portfólio moderno criado com Angular 22.'
        WHEN 2 THEN 'Uma API de finanças pessoais com controle por voz.'
        WHEN 3 THEN 'Uma API familiar de rotinas e recompensas, criada com DDD.'
        WHEN 4 THEN 'Explorando persistência com múltiplos bancos e bloqueio distribuído de assentos.'
        WHEN 5 THEN 'Uma API de filmes baseada em três padrões clássicos de projeto.'
    END,
    description = CASE id
        WHEN 1 THEN 'Portfólio pessoal inspirado em sites modernos de desenvolvedores, criado durante uma imersão em Java e Angular.'
        WHEN 2 THEN 'API de finanças pessoais controlada por voz, criada com Spring Boot e Spring AI para transcrever áudio, executar casos de uso e responder com voz sintetizada.'
        WHEN 3 THEN 'API REST para gerenciar rotinas familiares, tarefas infantis, pontos e recompensas, criada com Java 21, Spring Boot, PostgreSQL e OpenAPI.'
        WHEN 4 THEN 'Aplicação Spring Boot que explora persistência com MySQL, PostgreSQL e MongoDB, além de cache e bloqueio distribuído com Redis.'
        WHEN 5 THEN 'API criada com Java 21 e Spring Boot que integra o TMDB para consultar e gerenciar filmes aplicando padrões de projeto.'
    END,
    challenge = CASE id
        WHEN 1 THEN 'Criar um portfólio profissional aplicando conceitos modernos de Angular desde o início.'
        WHEN 2 THEN 'Permitir que uma pessoa registre movimentações financeiras falando, sem depender de formulários manuais.'
        WHEN 3 THEN 'Modelar rotinas, tarefas, pontos e recompensas com um domínio claro e isolado da infraestrutura.'
        WHEN 4 THEN 'Evitar reservas duplicadas quando duas pessoas tentam comprar o mesmo assento simultaneamente.'
        WHEN 5 THEN 'Consumir uma API externa mantendo a integração substituível e desacoplada.'
    END,
    solution = CASE id
        WHEN 1 THEN 'Foram utilizados componentes independentes, controle de fluxo moderno, um sistema de design reutilizável e dados tipados.'
        WHEN 2 THEN 'O áudio é transcrito, um modelo decide qual ferramenta executar e o resultado é convertido novamente em voz.'
        WHEN 3 THEN 'Foram aplicados Domain-Driven Design e Clean Architecture, com documentação OpenAPI e PostgreSQL como persistência.'
        WHEN 4 THEN 'O Redis gerencia o cache e o bloqueio distribuído, enquanto cada banco é utilizado conforme o formato dos dados.'
        WHEN 5 THEN 'Facade, Strategy e Singleton mantêm a integração com o TMDB simples, substituível e documentada.'
    END
WHERE locale = 'pt-BR';

UPDATE experience
SET position = CASE id
        WHEN 1 THEN 'Supervisor de Soluções / Tech Lead'
        WHEN 2 THEN 'Especialista de TI'
        WHEN 3 THEN 'Analista de Negócios / Consultor Técnico'
        WHEN 4 THEN 'Analista de Sistemas / Desenvolvedor'
    END,
    summary = CASE id
        WHEN 1 THEN 'Assumi uma área de suporte a sistemas corporativos sem processos definidos e a transformei em uma operação previsível e orientada por SLA.'
        WHEN 2 THEN 'Tornei-me a referência técnica sênior para sistemas ERP e HCM em uma multinacional francesa, trabalhando com equipes globais em quatro continentes.'
        WHEN 3 THEN 'Reconstruí uma plataforma legada de integrações em C#, reduzindo em 98% o tempo de integração por cliente e tornando-me a referência técnica das integrações.'
        WHEN 4 THEN 'Durante nove anos fui uma equipe de engenharia de uma pessoa para clientes ERP, atuando com desenvolvimento, consultoria, legislação tributária brasileira e integrações.'
    END,
    description = CASE id
        WHEN 1 THEN '["Liderei equipes de analistas e desenvolvimento, estruturei processos de suporte e reduzi significativamente o backlog.", "Coordenei implementações complexas, a comunicação com o negócio e a automação de processos internos."]'::jsonb
        WHEN 2 THEN '["Fui a referência técnica para sistemas ERP, HCM e bancos de dados corporativos.", "Liderei uma migração de ERP durante uma fusão empresarial e trabalhei com equipes internacionais."]'::jsonb
        WHEN 3 THEN '["Migrei integrações de Visual Basic para uma arquitetura C# multithread e idempotente.", "Trabalhei diretamente com clientes para transformar necessidades de negócio em especificações técnicas."]'::jsonb
        WHEN 4 THEN '["Desenvolvi módulos ERP, relatórios e integrações para clientes de diferentes setores.", "Trabalhei com conformidade fiscal brasileira, comércio eletrônico, bancos de dados e aplicações móveis."]'::jsonb
    END,
    highlights = CASE id
        WHEN 1 THEN '["Redução de 72% do backlog em 12 meses", "Liderança de equipes de até 9 pessoas", "Digitalização de processos de RH"]'::jsonb
        WHEN 2 THEN '["Referência técnica N2/N3 para ERP e HCM", "Liderança de migração de ERP durante uma fusão", "Colaboração internacional em inglês"]'::jsonb
        WHEN 3 THEN '["Redução de 98% no tempo de integração", "Migração de mais de um milhão de registros", "Zero interrupções para os clientes"]'::jsonb
        WHEN 4 THEN '["Desenvolvimento de componentes para emissão de notas fiscais", "Implementação de módulos fiscais brasileiros", "Gestão completa de projetos para clientes"]'::jsonb
    END
WHERE locale = 'pt-BR';

UPDATE certification
SET title = CASE id
        WHEN 1 THEN 'Análise e Desenvolvimento de Sistemas'
        WHEN 2 THEN 'MBA em Gestão de Pessoas, Liderança e Produtividade'
        WHEN 3 THEN 'Deal - Spring Boot e Angular (17+)'
        WHEN 4 THEN 'Bootcamp Java Santander'
        WHEN 5 THEN 'Especialista em Produtos de IA'
        WHEN 6 THEN 'Gestão de Produtos'
        WHEN 7 THEN 'Desenvolvimento Orientado a Testes'
        WHEN 8 THEN 'Domain-Driven Design'
        WHEN 9 THEN 'SOLID Express'
        WHEN 10 THEN 'Autenticação e Keycloak'
        WHEN 11 THEN 'Testes Avançados'
    END
WHERE locale = 'pt-BR';
