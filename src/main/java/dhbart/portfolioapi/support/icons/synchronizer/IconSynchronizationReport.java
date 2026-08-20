package dhbart.portfolioapi.support.icons.synchronizer;

import java.util.List;

public record IconSynchronizationReport(
        int total, int downloaded, int skipped, List<String> unsupported, List<String> failures) {
}
