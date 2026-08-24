# Caching strategy

The API uses Spring Cache backed by Caffeine. Caching is applied at the application-service boundary, after the response DTO has been built. This keeps persistence entities out of the cache and ensures a cache hit skips repository access and mapping work.

The following read models are cached: hero, about, experience, projects, technologies, certifications, project details, certification details, and social links. Project responses include their `featured` flag; the current API has no separate featured-project route, so `projects` is the cache used by the existing project-list operation. `featuredProjects` is reserved as a named cache for a future read operation without changing today’s contract.

All caches share the centralized policy in `CacheConfig`:

- expire 10 minutes after write;
- keep at most 256 entries per cache;
- record Caffeine statistics for operational inspection.

Locale-sensitive operations use the incoming `Accept-Language` value as part of the key. Project and certification details additionally include their identifier. Non-locale lists use Caffeine’s default single key.

Actuator, health checks, static resources, and future administrative endpoints are not cached because no cache annotation is placed on those paths.

## Future invalidation

The current API is read-only, so time-based expiration is sufficient. When administrative writes are introduced, each write service must evict the affected cache entries after a successful transaction. Aggregate edits should evict the corresponding list cache and detail cache; edits to localized content should evict the locale-specific key (or the complete related cache when the change affects fallback resolution). This invalidation belongs in the administrative application service, not in controllers or repositories.
