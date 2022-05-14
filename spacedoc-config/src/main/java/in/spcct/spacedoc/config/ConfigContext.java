package in.spcct.spacedoc.config;

import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A configuration wrapper for "smart" user-friendly configurations.
 * <p>
 * Lets the users query which properties were actually used, which weren't used, etc.
 * <p>
 * A "getOrDefault" method is deliberately not provided to avoid annoying errors, such as:
 * - Some value is used, but where did it come from?
 * - Possible race conditions in case a default value is inserted if not found
 */
public interface ConfigContext {

    static ConfigContext getInstance() {
        return ConfigContextImpl.INSTANCE;
    }

    class NoValueAssociatedException extends RuntimeException {
        public NoValueAssociatedException(String message) {
            super(message);
        }

        public NoValueAssociatedException(String message, Throwable cause) {
            super(message, cause);
        }

        public NoValueAssociatedException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Says where the configuration value came from.
     * <p>
     * Also specifies the override priority:
     * entries with lower ordinal value may be overriden by entries with a greater ordinal value,
     * that is the entries lower in the following source code listing can override entries higher in this listing.
     */
    enum EntrySource {
        /**
         * = This entry was an internal default value, as no other value was provided
         */
        INTERNAL_DEFAULT,
        /**
         * = This entry came from some configurations file
         */
        CONFIG_FILE,
        /**
         * = This entry came from the command-line parameters
         */
        COMMAND_LINE,
        /**
         * = This value was determined by the build configuration of this application, and thus cannot be overridden.
         */
        BUILD;

        boolean canOverride(EntrySource other) {
            return this.ordinal() >= other.ordinal();
        }
    }

    @Data
    class Entry {
        public Entry(EntrySource source, String key, String value) {
            this.source = source;
            this.key = key;
            this.value = value;
            this.used = false;
        }

        private final EntrySource source;
        private final String key;
        private final String value;
        private boolean used;
    }

    /**
     * Adds a property to current configuration
     *
     * @param source where did this value come from, be honest >:c In case this is somehow determined by internal logic,
     *               try to use the source of the data the decisions are made upon.
     * @param key    configuration key to register the value under
     * @param value  configuration value to register
     */
    void put(EntrySource source, String key, String value);

    /**
     * Bulk-loads properties from the same source.
     *
     * @param source     where did this value come from, be honest >:c In case this is somehow determined by internal logic,
     *                   try to use the source of the data the decisions are made upon.
     * @param properties [key, value]
     */
    void putAll(EntrySource source, Map<String, String> properties);

    /**
     * On top of checking whether this value exists,
     * the key must also be added to an internal set of queried keys for later retrieval.
     *
     * @param propertyName key to check existence of
     * @return true if a property with this name exists
     */
    boolean contains(String propertyName);

    /**
     * On top of returning the value associated with this key,
     * the key must also be added to an internal set of queried keys for later retrieval.
     *
     * @param propertyName key to get value for
     * @return associated value with this key when found, an unchecked exception is thrown otherwise.
     * @throws NoValueAssociatedException when the supplied propertyName has no value associated with it (see {@link #contains(String)})
     */
    String get(String propertyName);

    /**
     * Only use for statistics, not for configuration!
     *
     * @return All entries. Do not modify.
     */
    Collection<Entry> getAllEntries();

    /**
     * @return Set of all entries that were used, regardless of their origin
     */
    default Set<Entry> getUsedEntries() {
        return getAllEntries().stream()
                .filter(Entry::isUsed)
                .collect(Collectors.toSet());
    }

    /**
     * @return Set of keys that were queried by either {@link #contains(String)}, or {@link #get(String)}.
     * Values that were only queried by contains and never had a value assigned to them
     * should have the {@link Entry#source} and {@link Entry#value} fields set to null.
     */
    Collection<Entry> getQueriedEntries();

    /**
     * @return Set of entries that were queried and had user-defined values
     */
    default Set<Entry> getUsedDefinedEntries() {
        return getAllEntries().stream()
                .filter(Entry::isUsed)
                .filter(entry -> entry.source == EntrySource.CONFIG_FILE
                        || entry.source == EntrySource.COMMAND_LINE)
                .collect(Collectors.toSet());
    }

    /**
     * @return Set of entries that were used and had default values
     */
    default Set<Entry> getUsedDefaultEntries() {
        return getAllEntries().stream()
                .filter(Entry::isUsed)
                .filter(entry -> entry.source == EntrySource.INTERNAL_DEFAULT)
                .collect(Collectors.toSet());
    }

    /**
     * @return Set of entries that were user-defined but were not used
     */
    default Set<Entry> getUnusedEntries() {
        return getAllEntries().stream()
                .filter(entry -> !entry.isUsed())
                .collect(Collectors.toSet());
    }


}
