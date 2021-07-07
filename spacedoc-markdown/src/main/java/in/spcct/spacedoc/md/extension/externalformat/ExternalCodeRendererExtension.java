package in.spcct.spacedoc.md.extension.externalformat;

import in.spcct.spacedoc.md.renderer.ExternalFormatNodeHtmlRenderer;
import org.commonmark.Extension;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * A CommonMark extension supporting a custom external format renderer, {@link ExternalFormatNodeHtmlRenderer}.
 */
public class ExternalCodeRendererExtension implements HtmlRenderer.HtmlRendererExtension {

    public static Extension create() {
        return new ExternalCodeRendererExtension();
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(ExternalFormatNodeHtmlRenderer::new);
    }

}
