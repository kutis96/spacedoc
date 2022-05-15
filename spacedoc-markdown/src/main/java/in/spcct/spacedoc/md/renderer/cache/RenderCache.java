package in.spcct.spacedoc.md.renderer.cache;

import java.util.function.Function;

/**
 * Render cache should provide some way of caching/querying rendered items :)
 * <p>
 * The input should be the source code for the figure to be rendered, the output should be the rendered figure as HTML.
 * Both should be strings.
 * <p>
 * Additional context may be somehow provided for use.
 */
public interface RenderCache {

    class NullRenderCache implements RenderCache {
        @Override
        public String lookupOrGenerate(String code, Function<String, String> lazyRenderer) {
            return lazyRenderer.apply(code);
        }
    }

    /**
     * Try to look up the rendered version of the given source string in the cache and return it if found.
     * If nothing's found, call the renderer, store the rendered output into cache, and return it.
     *
     * @param code         source code to render
     * @param lazyRenderer renderer to call if the result is not found in cache yet
     * @return cached result
     */
    String lookupOrGenerate(String code, Function<String, String> lazyRenderer);

}
