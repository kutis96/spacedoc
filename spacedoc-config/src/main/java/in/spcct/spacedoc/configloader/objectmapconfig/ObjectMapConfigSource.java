package in.spcct.spacedoc.configloader.objectmapconfig;

import in.spcct.spacedoc.configloader.ConfigSource;

import java.util.Map;

public class ObjectMapConfigSource implements ConfigSource<Object> {

    private final Map<String, Object> map;

    public ObjectMapConfigSource(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public boolean containsKey(String path) {
        return map.containsKey(path);
    }

    @Override
    public Object getValue(String path) {
        return map.get(path);
    }
}
