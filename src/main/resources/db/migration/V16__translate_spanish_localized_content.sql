UPDATE project
SET title = CASE id
        WHEN 201 THEN 'Portafolio Bartholdy'
        WHEN 202 THEN 'Presupuesto — API financiera por voz'
        WHEN 203 THEN 'Crescer Juntos'
        WHEN 204 THEN 'Mercado de entradas'
        WHEN 205 THEN 'API de exploración de películas'
    END,
    headline = CASE id
        WHEN 201 THEN 'Un portafolio moderno creado con Angular 22.'
        WHEN 202 THEN 'Una API de finanzas personales con la que hablas.'
        WHEN 203 THEN 'Una API familiar de rutinas y recompensas, creada con DDD.'
        WHEN 204 THEN 'Explorando persistencia multidatabase y bloqueo distribuido de asientos.'
        WHEN 205 THEN 'Una API de películas basada en tres patrones de diseño clásicos.'
    END
WHERE locale = 'es-ES';

UPDATE project
SET description = CASE id
        WHEN 201 THEN 'Portafolio personal inspirado en sitios web modernos de desarrolladores, creado durante una inmersión en Java y Angular.'
        WHEN 202 THEN 'API de finanzas personales controlada por voz, creada con Spring Boot y Spring AI para transcribir audio, ejecutar casos de uso y responder con voz sintetizada.'
        WHEN 203 THEN 'API REST para gestionar rutinas familiares, tareas infantiles, puntos y recompensas, creada con Java 21, Spring Boot, PostgreSQL y OpenAPI.'
        WHEN 204 THEN 'Aplicación Spring Boot que explora persistencia con MySQL, PostgreSQL y MongoDB, además de caché y bloqueo distribuido con Redis.'
        WHEN 205 THEN 'API creada con Java 21 y Spring Boot que integra TMDB para consultar y gestionar películas aplicando patrones de diseño.'
    END,
    challenge = CASE id
        WHEN 201 THEN 'Crear un portafolio profesional aplicando conceptos modernos de Angular desde cero.'
        WHEN 202 THEN 'Permitir que una persona registre movimientos financieros hablando, sin depender de formularios manuales.'
        WHEN 203 THEN 'Modelar rutinas, tareas, puntos y recompensas con un dominio claro y aislado de la infraestructura.'
        WHEN 204 THEN 'Evitar reservas duplicadas cuando dos personas intentan comprar el mismo asiento simultáneamente.'
        WHEN 205 THEN 'Consumir una API externa manteniendo la integración intercambiable y desacoplada.'
    END,
    solution = CASE id
        WHEN 201 THEN 'Se utilizaron componentes independientes, control de flujo moderno, un sistema de diseño reutilizable y datos tipados.'
        WHEN 202 THEN 'El audio se transcribe, un modelo decide qué herramienta ejecutar y el resultado se convierte nuevamente en voz.'
        WHEN 203 THEN 'Se aplicaron Domain-Driven Design y Clean Architecture, con documentación OpenAPI y PostgreSQL como persistencia.'
        WHEN 204 THEN 'Redis gestiona la caché y el bloqueo distribuido, mientras cada base de datos se utiliza según la forma de sus datos.'
        WHEN 205 THEN 'Facade, Strategy y Singleton mantienen la integración con TMDB simple, intercambiable y documentada.'
    END
WHERE locale = 'es-ES';

UPDATE experience
SET description = CASE id
        WHEN 201 THEN '["Lideré equipos de analistas y desarrollo, estructuré procesos de soporte y reduje significativamente el backlog.", "Coordiné implementaciones complejas, interlocución con el negocio y automatización de procesos internos."]'::jsonb
        WHEN 202 THEN '["Fui la referencia técnica para sistemas ERP, HCM y bases de datos corporativas.", "Lideré una migración de ERP durante una fusión empresarial y trabajé con equipos internacionales."]'::jsonb
        WHEN 203 THEN '["Migré integraciones de Visual Basic a una arquitectura C# multihilo e idempotente.", "Trabajé directamente con clientes para convertir necesidades de negocio en especificaciones técnicas."]'::jsonb
        WHEN 204 THEN '["Desarrollé módulos ERP, informes e integraciones para clientes de distintos sectores.", "Trabajé con cumplimiento fiscal brasileño, comercio electrónico, bases de datos y aplicaciones móviles."]'::jsonb
    END,
    highlights = CASE id
        WHEN 201 THEN '["Reducción del 72% del backlog en 12 meses", "Liderazgo de equipos de hasta 9 personas", "Digitalización de procesos de RR. HH."]'::jsonb
        WHEN 202 THEN '["Referencia técnica N2/N3 para ERP y HCM", "Liderazgo de migración de ERP durante una fusión", "Colaboración internacional en inglés"]'::jsonb
        WHEN 203 THEN '["Reducción del 98% en el tiempo de integración", "Migración de más de un millón de registros", "Cero interrupciones para los clientes"]'::jsonb
        WHEN 204 THEN '["Desarrollo de componentes para facturación electrónica", "Implementación de módulos fiscales brasileños", "Gestión integral de proyectos para clientes"]'::jsonb
    END
WHERE locale = 'es-ES';

UPDATE experience
SET position = CASE id
        WHEN 201 THEN 'Supervisor de Soluciones / Tech Lead'
        WHEN 202 THEN 'Especialista de TI'
        WHEN 203 THEN 'Analista de Negocio / Consultor Técnico'
        WHEN 204 THEN 'Analista de Sistemas / Desarrollador'
    END,
    summary = CASE id
        WHEN 201 THEN 'Asumí un área de soporte de sistemas corporativos sin procesos definidos y la convertí en un motor de entregas predecible y orientado a SLA.'
        WHEN 202 THEN 'Me convertí en la referencia técnica senior para sistemas ERP y HCM dentro de una multinacional francesa, trabajando con equipos globales en cuatro continentes.'
        WHEN 203 THEN 'Reconstruí una plataforma de integración heredada en C#, reduciendo en un 98% el tiempo de integración por cliente y convirtiéndome en la referencia técnica de las integraciones.'
        WHEN 204 THEN 'Durante nueve años fui un equipo de ingeniería de una sola persona para clientes ERP, trabajando con desarrollo, consultoría, impuestos brasileños e integraciones.'
    END
WHERE locale = 'es-ES';

UPDATE certification
SET title = CASE id
        WHEN 201 THEN 'Análisis y Desarrollo de Sistemas'
        WHEN 202 THEN 'MBA en Gestión de Personas, Liderazgo y Productividad'
        WHEN 203 THEN 'Deal - Spring Boot y Angular (17+)'
        WHEN 204 THEN 'Bootcamp Java Santander'
        WHEN 205 THEN 'Especialista en Productos de IA'
        WHEN 206 THEN 'Gestión de Productos'
        WHEN 207 THEN 'Desarrollo Guiado por Pruebas'
        WHEN 208 THEN 'Diseño Guiado por el Dominio'
        WHEN 209 THEN 'SOLID Express'
        WHEN 210 THEN 'Autenticación y Keycloak'
        WHEN 211 THEN 'Pruebas Avanzadas'
    END
WHERE locale = 'es-ES';
