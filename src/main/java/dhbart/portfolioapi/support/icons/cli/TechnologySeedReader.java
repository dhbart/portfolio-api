package dhbart.portfolioapi.support.icons.cli;

import dhbart.portfolioapi.technology.domain.model.Technology;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class TechnologySeedReader {
    private static final Pattern TECHNOLOGY_ROW = Pattern.compile(
            "\\((\\d+),\\s*'[^']*',\\s*'([^']+)',\\s*(\\d+),");

    public java.util.List<Technology> read(Path seedFile) throws IOException {
        var technologies = new ArrayList<Technology>();
        for (var line : Files.readAllLines(seedFile)) {
            var matcher = TECHNOLOGY_ROW.matcher(line);
            if (matcher.find()) {
                technologies.add(Technology.builder().id(Long.valueOf(matcher.group(1)))
                        .slug(matcher.group(2)).displayOrder(Integer.valueOf(matcher.group(3))).build());
            }
        }
        if (technologies.isEmpty()) {
            throw new IOException("No technology rows found in " + seedFile);
        }
        return technologies;
    }
}
