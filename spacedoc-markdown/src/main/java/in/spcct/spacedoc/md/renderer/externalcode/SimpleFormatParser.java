package in.spcct.spacedoc.md.renderer.externalcode;

import lombok.Data;

import java.util.Optional;

/**
 * A parser for simple, line-oriented "DSL" parsing.
 * Supports comments and additional key: value configuration parsing.
 * <p>
 * The parser provides visitor methods for dealing with configuration
 * and source code lines directly as they're encountered.
 * <p>
 * A simplified grammar:
 * <pre>
 * source ::= line*
 * line ::= \s* (config | source) \s* comment?
 * comment ::= "//" .+$
 * config ::= key \s* ":" \s* value
 * source ::= ...
 * key ::= ...
 * value ::= ...
 * </pre>
 */
public abstract class SimpleFormatParser {

    protected String configInitializer = "--";
    protected String commentInitializer = "//";
    protected String keyValueSeparator = ":";

    public void parse(String source) {
        if (source == null)
            return;

        source.lines().forEach(this::parseLine);
    }

    public void parseLine(String line) {
        if (line == null)
            return;

        line = line.replaceAll(commentInitializer + ".+$", "").strip();

        if (line.startsWith(configInitializer)) {
            Optional<KVEntry> configEntry = parseConfigLine(line);
            configEntry.ifPresent(this::dealWithConfigEntry);
            return;
        }

        dealWithSourceLine(line);
    }

    protected Optional<KVEntry> parseConfigLine(String line) {
        if (line.length() < 2)
            return Optional.empty(); //nothing's here

        line = line.substring(configInitializer.length()); //remove initial "--"

        String[] stuff = line.split(keyValueSeparator);

        String key = stuff[0].trim();

        if (stuff.length == 1)
            return Optional.of(
                    new KVEntry(key, null)
            );

        StringBuilder value = new StringBuilder(stuff[1]);
        for (int i = 2; i < stuff.length; i++) {
            value.append(':').append(stuff[i]);
        }

        return Optional.of(
                new KVEntry(key, value.toString().trim())
        );
    }

    /**
     * Visitor method for dealing with configuration entries.
     *
     * @param entry configuration entry
     */
    public abstract void dealWithConfigEntry(KVEntry entry);

    /**
     * Visitor method for dealing with source lines.
     *
     * @param line line of source code
     */
    public abstract void dealWithSourceLine(String line);

    /**
     * Represents a single Key: Value configuration entry.
     */
    @Data
    public static class KVEntry {
        private final String key;
        private final String value;
        private final boolean hasValue;

        public KVEntry(String key) {
            this.key = key;
            this.value = null;
            this.hasValue = false;
        }

        public KVEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.hasValue = true;
        }

        public boolean doesHaveValue() {
            return hasValue;
        }
    }

}
