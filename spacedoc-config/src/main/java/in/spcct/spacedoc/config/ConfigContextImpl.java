package in.spcct.spacedoc.config;

import java.util.*;

public class ConfigContextImpl implements ConfigContext {

    public static final ConfigContextImpl INSTANCE = new ConfigContextImpl();

    private final Map<String, Entry> backingMap = new HashMap<>();

    private final Set<String> keysTestedForExistence = new HashSet<>();

    @Override
    public void put(EntrySource source, String key, String value) {

        if (backingMap.containsKey(key)) {
            boolean canOverride = source.canOverride(backingMap.get(key).getSource());
            if (!canOverride)
                return; //can't override this, and there's an existing value already
        }

        backingMap.put(key, new Entry(
                source, key, value
        ));

    }

    @Override
    public void putAll(EntrySource source, Map<String, String> properties) {
        properties.forEach(
                (key, value) -> put(source, key, value)
        );
    }

    @Override
    public boolean contains(String propertyName) {
        boolean contains = backingMap.containsKey(propertyName);
        keysTestedForExistence.add(propertyName);
        return contains;
    }

    @Override
    public String get(String propertyName) {
        if (contains(propertyName)) {
            Entry entry = backingMap.get(propertyName);
            entry.setUsed(true);
            return entry.getValue();
        }
        throw new ConfigContext.NoValueAssociatedException("No value set for '" + propertyName + "'");
    }

    @Override
    public Collection<Entry> getAllEntries() {
        return backingMap.values();
    }

    @Override
    public Collection<Entry> getQueriedEntries() {
        HashMap<String, Entry> temp = new HashMap<>();
        keysTestedForExistence.stream()
                .map(key -> new Entry(null, key, null))
                .forEach(entry -> temp.put(entry.getKey(), entry));
        getUsedEntries().forEach(entry -> temp.put(entry.getKey(), entry));
        return temp.values();
    }

}
