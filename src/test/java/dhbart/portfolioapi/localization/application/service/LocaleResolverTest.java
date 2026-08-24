package dhbart.portfolioapi.localization.application.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LocaleResolverTest {
    private final LocaleResolver resolver = new LocaleResolver();

    @Test
    void shouldResolveSupportedLanguagesBeforeFallbacks() {
        assertThat(resolver.resolve("es-ES,en-US")).containsExactly("es-ES", "en-US", "pt-BR");
    }

    @Test
    void shouldUseEnglishAndPortugueseFallbackForUnsupportedOrMalformedHeader() {
        assertThat(resolver.resolve("xx-ZZ")).containsExactly("en-US", "pt-BR");
        assertThat(resolver.resolve("not a language")).containsExactly("en-US", "pt-BR");
    }
}
