package in.spcct.spacedoc.configloader.objectmapconfig;

import in.spcct.spacedoc.configloader.ConfigSource;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;

public class ObjectMapConfigSource implements ConfigSource<Object> {

    /**
     * Values are expected to be of any type; in case they're of a {@link Map} type, {@link Map<String, Object>} is assumed.
     * <p>
     * This applies for all the child maps as well. None of these should point to the original map, or any of the parent maps.
     */
    private final Map<String, Object> map;

    /**
     * Creates a new config source with the specified backing map.
     *
     * @param map backing map to be used.
     */
    public ObjectMapConfigSource(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public boolean containsItem(@NonNull String... pathSegments) {
        return getMapByPath(pathSegments)
                .containsKey(lastKey(pathSegments));
    }

    @Override
    public Object getItem(@NonNull String... pathSegments) {
        return getMapByPath(pathSegments)
                .get(lastKey(pathSegments));
    }

    private String lastKey(String... pathSegments) {
        if (pathSegments.length == 0)
            return null;

        return pathSegments[pathSegments.length - 1];
    }

    private Map<String, Object> getMapByPath(String... pathSegments) {
        return getMapByPath(pathSegments, 0, map);
    }

    private Map<String, Object> getMapByPath(String[] pathSegments, int startingIndex, Map<String, Object> originalMap) {
        if (pathSegments.length == startingIndex)
            return Collections.emptyMap();
        if (pathSegments.length == startingIndex + 1)
            return originalMap;
        String key = pathSegments[startingIndex];

        if (!map.containsKey(key))
            return Collections.emptyMap();

        Object value = map.get(key);

        if (!(value instanceof Map)) {
            return Collections.emptyMap();
        }

        return getMapByPath(pathSegments, startingIndex + 1, (Map<String, Object>) value);    //let's pretend this won't ever fail.
    }
}
