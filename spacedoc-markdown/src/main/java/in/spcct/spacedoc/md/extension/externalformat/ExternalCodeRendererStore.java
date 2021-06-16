package in.spcct.spacedoc.md.extension.externalformat;

import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ExternalCodeRendererStore {

    private static final ExternalCodeRendererStore INSTANCE = new ExternalCodeRendererStore();

    public static ExternalCodeRendererStore getInstance() {
        return INSTANCE;
    }

    protected ExternalCodeRendererStore() {
        //stuff
    }

    private final Map<String, ExternalCodeRenderer> rendererStore = new HashMap<>();

    public void register(String languageName, ExternalCodeRenderer renderer) {
        rendererStore.put(languageName.toLowerCase(Locale.ROOT), renderer);
    }

    public ExternalCodeRenderer lookup(String languageName) {
        return rendererStore.get(languageName.toLowerCase(Locale.ROOT));
    }

}
