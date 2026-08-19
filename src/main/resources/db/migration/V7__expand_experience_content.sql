ALTER TABLE experience RENAME COLUMN description TO summary;

ALTER TABLE experience
    ADD COLUMN location VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN period VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN description JSONB NOT NULL DEFAULT '[]'::jsonb,
    ADD COLUMN highlights JSONB NOT NULL DEFAULT '[]'::jsonb,
    ADD COLUMN technologies JSONB NOT NULL DEFAULT '[]'::jsonb;

UPDATE experience
SET location = 'Lajeado, RS — Brazil',
    period = 'Apr 2024 – Jan 2026',
    description = '[
        "I joined Fruki, one of the largest beverage manufacturers in Southern Brazil, to lead a team of business analysts and, later, an outsourced development squad — up to 9 people total. When I arrived, the area had more than 90 open tickets, no SLA, and roughly 20–30 new demands landing every single month. My mission was to bring that backlog under control without stopping the flow of new work.",
        "I structured the support process from scratch: defined SLAs (5 business days for refinement, 20 business days for production delivery), implemented Scrum and Kanban rituals (dailies, sprint planning, refinements) with the development squad, and set up continuous monitoring of team indicators. I ran weekly meetings with the managers who opened tickets to validate real business impact and priority — filtering out demands that no longer made sense, not just working through the queue faster.",
        "On the people side, I applied the leadership practices I was studying in my MBA at the time: one-on-ones with every analyst, pairing to unblock stalled tickets, coaching, and recognition. I later also took over the corporate Level-2 (N2) support team, going back to a more hands-on role — debugging production issues directly and helping design fixes — while continuing to reduce the backlog accumulated on that front as well.",
        "Beyond day-to-day support, I led war-room go-lives for high-complexity satellite system implementations, including a C-Commerce platform and a procurement software rollout, coordinating requirements, timeline, resources, and assisted operation through stabilization. I was also the direct interface between business stakeholders and the technical teams — running status reports, negotiating scope, and translating complex business needs into demands the team could actually execute.",
        "One project I''m particularly proud of: HR had a study-reimbursement process that ran entirely over email, with information getting lost between back-and-forth threads. I mapped the process end-to-end with the HR team, designed it as a BPM workflow inside our own Senior ERP (which had that capability built in), found and briefed a developer to build it, and then integrated it with payroll so that once a request was approved, it automatically generated a payment order in the ERP. Today more than 100 employees use that flow instead of email, and the process is fully traceable and reportable."
    ]'::jsonb,
    highlights = '[
        "72% backlog reduction in 12 months — from 90+ to 25 open tickets — while absorbing 20–30 new demands per month",
        "Structured SLA framework from zero: 5 business days for refinement, 20 for production delivery",
        "Led war-room go-lives for C-Commerce platform and procurement software implementations",
        "Digitized HR''s study-reimbursement process via BPM with payroll integration, now used by 100+ employees",
        "Led and mentored a team of up to 9 (business analysts + outsourced Progress development squad)"
    ]'::jsonb,
    technologies = '["SQL", "Oracle", "PostreSql", "Progress", "Senior Vetorh", "Senior BPM", "Leadership", "REST/SOAP APIs", "Azure DevOps", "Zendesk", "Scrum", "Kanban", "PHP", "Qualitor"]'::jsonb
WHERE id = 1;

UPDATE experience
SET location = 'Porto Alegre, RS — Brazil',
    period = 'Jan 2022 – Mar 2024',
    description = '[
        "At Group Indigo, a French multinational, I served as the senior N2/N3 technical reference for corporate ERP environments — first Senior Sapiens and Senior Vetorh HCM, later Totvs Protheus — and for the corporate databases behind them. I was the escalation point for critical production incidents affecting payroll, benefits, and core business systems.",
        "Payroll and HCM were new territory for me when I joined, and I adapted quickly: I became the technical reference for Senior Vetorh across Payroll, Benefits, Occupational Health & Safety (ASO, CAT, PPP, PCMSO) and People Management, supporting functional users and the integrations connecting those modules to the rest of the ecosystem. This is also where I learned Senior''s proprietary programming language (LSP) and, later, ADVPL for Protheus, becoming proficient enough to build customizations and adjust reports directly.",
        "I led the ERP migration project from Senior to Totvs Protheus — a genuinely difficult period, because the migration overlapped with a corporate merger, which meant absorbing the acquired company''s systems at the same time. My team was responsible for supporting the entire new ecosystem through that transition, handling requirements, documentation and assisted operation on both fronts simultaneously.",
        "This role also gave me real international exposure: I regularly joined meetings in English with global teams — colleagues in India, the UK, France, the US and Estonia — including the implementation of a parking-management system and other cross-border projects. When I joined, IT ownership of several tools was unclear, so I took ownership of Zendesk and became the internal reference for it, redesigning ticket flows and encoding a lot of the areas'' business rules into how tickets were routed and handled.",
        "On the automation side, I built SQL and VBA solutions to eliminate manual, repetitive work across operational areas, and produced technical documentation, runbooks and knowledge-base articles to make the systems easier to maintain and to onboard new team members faster."
    ]'::jsonb,
    highlights = '[
        "Senior technical reference (N2/N3) for Senior Sapiens, Senior Vetorh HCM and Totvs Protheus",
        "Led ERP migration (Senior → Protheus) concurrent with a corporate merger",
        "Regular English-language collaboration with global teams across India, UK, France, US and Estonia",
        "Took ownership of Zendesk platform, becoming the internal reference and redesigning ticket flows",
        "Learned and applied Senior''s proprietary language (LSP) and ADVPL (Protheus) for customizations"
    ]'::jsonb,
    technologies = '["SQL", "SQL Server", "VBA", "LSP", "ADVPL", "Senior Vetorh HCM", "Totvs Protheus", "REST/SOAP APIs", "Zendesk", "Agidesk", "TWM Guiando"]'::jsonb
WHERE id = 2;

UPDATE experience
SET location = 'Caxias do Sul, RS — Brazil',
    period = 'Apr 2018 – Dec 2021',
    description = '[
        "I joined Mercanet, a sales-force-automation (SFA) company, as a business analyst working on system customizations — mostly reports and flows for clients. But my software development background quickly surfaced a bigger problem: the company''s ERP integration layer was built on an aging Visual Basic technology that couldn''t handle the data volumes clients needed.",
        "A software architect designed a new C# architecture for that layer — multithreaded, with better data-handling capabilities — and I took ownership of migrating client integrations onto it one by one. Using multithreading and idempotency, we turned integrations that used to take 6 hours (importing customers, invoices, orders — some processing more than 1 million records) into ones that ran in about 5 minutes, a 98% reduction. Each client migration took roughly two months, and I became the team''s reference for the new integration architecture across SAP RFC, Totvs Datasul and Sankhya environments — with zero disruption to clients during rollout.",
        "Beyond the technical migration, I worked directly with corporate clients on requirements: running discovery and alignment meetings, mapping their processes, and translating business needs into implementable technical specifications for REST, SOAP, JSON and XML integrations. I also supported pre-sales conversations with technical feasibility analysis and post-implementation follow-up, and produced documentation to speed up client onboarding.",
        "Around the same time, Fruki''s controlling/finance area — a role I''d return to years later as an employee — had a monthly ritual of pulling data from 5–6 different reports and manually assembling it in Excel to build a cost sheet and P&L. I mapped that process, wrote a single SQL query joining more than 20 tables that took about 20 minutes to run, and replaced hours of manual work with one query."
    ]'::jsonb,
    highlights = '[
        "98% reduction in integration runtime (6h → 5 min) across SAP RFC, Totvs Datasul and Sankhya",
        "Migrated integration platform from legacy Visual Basic to a multithreaded C# architecture, handling 1M+ record integrations",
        "Became the team''s technical reference for the new integration architecture, with zero client disruption during rollout",
        "Replaced a multi-hour manual Excel reporting process with a single 20-minute SQL query joining 20+ tables"
    ]'::jsonb,
    technologies = '["C#", ".NET", "Visual Basic", "SQL Server", "Oracle", "PostgreSQL", "SAP RFC", "REST APIs", "SOAP", "JSON/XML", "Progress"]'::jsonb
WHERE id = 3;

UPDATE experience
SET location = 'Caxias do Sul, RS — Brazil',
    period = 'Sep 2009 – Apr 2018',
    description = '[
        "This was my first job in technology, and I stayed for nine years. I started developing in Visual FoxPro — a language that was already considered outdated at the time, but I learned it, got very good at it, and built screens, reports and full ERP modules with it, alongside a growing amount of C#.",
        "Because I''ve always been comfortable talking to people, I moved beyond pure development early on: I started visiting clients directly for consulting — understanding their problems, sometimes finding they were still doing entire processes in Excel, proposing solutions, estimating and budgeting the work, then developing, testing, deploying and training users myself. I often worked on-site at the client''s office, building whatever screen or report they needed on the spot. I was effectively a one-person engineering team, and I got to know a huge range of industries as clients — from retail to manufacturing to, memorably, even cemeteries.",
        "A large part of this role was Brazilian tax and fiscal compliance, which is notoriously complex and constantly changing. I helped build the ERP''s SPED modules (Contábil, Fiscal, Contribuições) and, for electronic invoicing (NF-e), helped create a C# component that digitally signed invoices and transmitted them directly to the government. I also gave clients direct consulting on tax calculation and on inventory, purchasing and sales modules.",
        "On the integration side, I connected ERP systems to e-commerce platforms so orders could flow in automatically, and I worked on backup strategies and performance tuning for screens and reports running on SQL Server and Progress/OpenEdge. I also built mobile data-collection applications for Windows CE devices used in warehouse and field operations — my first exposure to mobile development."
    ]'::jsonb,
    highlights = '[
        "Built C# component for digital signature and government transmission of electronic invoices (NF-e)",
        "Delivered Brazilian tax-compliance ERP modules (SPED Contábil, Fiscal, Contribuições) kept current with changing legislation",
        "Owned full project lifecycle solo for corporate clients: discovery, budgeting, development, deployment, training",
        "Built e-commerce–to–ERP integrations for automatic order processing",
        "Developed Windows CE mobile data-collection apps for warehouse and field operations"
    ]'::jsonb,
    technologies = '["C#", "Visual FoxPro", "SQL Server", "Progress/OpenEdge", "Windows CE"]'::jsonb
WHERE id = 4;

ALTER TABLE experience
    ALTER COLUMN location DROP DEFAULT,
    ALTER COLUMN period DROP DEFAULT,
    ALTER COLUMN description DROP DEFAULT,
    ALTER COLUMN highlights DROP DEFAULT,
    ALTER COLUMN technologies DROP DEFAULT;
