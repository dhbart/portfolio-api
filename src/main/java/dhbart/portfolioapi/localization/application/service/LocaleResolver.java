package dhbart.portfolioapi.localization.application.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component("supportedLocaleResolver")
public class LocaleResolver {

    public static final String PT_BR = "pt-BR";
    public static final String EN_US = "en-US";
    public static final String ES_ES = "es-ES";

    private static final List<String> SUPPORTED_LOCALES = List.of(PT_BR, EN_US, ES_ES);

    public List<String> resolve(String acceptLanguage) {
        var candidates = new LinkedHashSet<String>();
        if (acceptLanguage != null && !acceptLanguage.isBlank()) {
            try {
                for (var range : Locale.LanguageRange.parse(acceptLanguage)) {
                    addMatchingLocale(candidates, range.getRange());
                }
            } catch (IllegalArgumentException ignored) {
                // An invalid header is treated like an unsupported locale.
            }
        }
        candidates.add(EN_US);
        candidates.add(PT_BR);
        return new ArrayList<>(candidates);
    }

    private void addMatchingLocale(Set<String> candidates, String range) {
        for (String supported : SUPPORTED_LOCALES) {
            var supportedLocale = Locale.forLanguageTag(supported);
            if (supported.equalsIgnoreCase(range)
                    || supportedLocale.getLanguage().equalsIgnoreCase(range)
                    || supportedLocale.getLanguage().equalsIgnoreCase(range.split("-")[0])) {
                candidates.add(supported);
            }
        }
    }
}
