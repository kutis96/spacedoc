package in.spcct.spacedoc.configloader;

public interface ConfigSource<V> {

    boolean containsKey(String path);

    V getValue(String path);

}
