INSERT INTO experience (
    id,
    company,
    position,
    description,
    start_date,
    end_date,
    current_position,
    display_order,
    created_at,
    updated_at
) VALUES
(
    1,
    'Fruki Bebidas S.A.',
    'Solutions Supervisor / Tech Lead',
    'Took over a corporate systems support area with no defined process and turned it into a predictable, SLA-driven delivery engine.',
    DATE '2024-04-01',
    DATE '2026-01-05',
    FALSE,
    4,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    2,
    'Group Indigo Brasil',
    'IT Specialist',
    'Became the senior technical reference for ERP and HCM systems inside a French multinational, working daily with global teams across four continents.',
    DATE '2022-01-10',
    DATE '2024-03-28',
    FALSE,
    3,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    3,
    'Mercanet SFA',
    'Business Analyst / Technical Consultant',
    'Rebuilt a legacy Visual Basic integration platform in C#, cutting per-client integration time by 98% and becoming the company''s go-to reference for integrations.',
    DATE '2018-05-18',
    DATE '2021-12-14',
    FALSE,
    2,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    4,
    'Design Software Ltda.',
    'Systems Analyst / Developer',
    'Nine years as a one-person engineering team for ERP clients — learning a "dead" language, mastering Brazilian tax compliance, and owning projects end-to-end.',
    DATE '2009-09-21',
    DATE '2018-04-30',
    FALSE,
    1,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
