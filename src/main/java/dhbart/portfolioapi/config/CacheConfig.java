package dhbart.portfolioapi.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Duration EXPIRATION = Duration.ofMinutes(10);
    private static final long MAX_ENTRIES = 256;

    @Bean
    CacheManager cacheManager() {
        var cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(List.of(
                CacheNames.HERO,
                CacheNames.ABOUT,
                CacheNames.EXPERIENCE,
                CacheNames.PROJECTS,
                CacheNames.FEATURED_PROJECTS,
                CacheNames.TECHNOLOGIES,
                CacheNames.CERTIFICATIONS,
                CacheNames.PROJECT_DETAILS,
                CacheNames.CERTIFICATION_DETAILS,
                CacheNames.SOCIAL_LINKS));
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(EXPIRATION)
                .maximumSize(MAX_ENTRIES)
                .recordStats());
        return cacheManager;
    }
}
