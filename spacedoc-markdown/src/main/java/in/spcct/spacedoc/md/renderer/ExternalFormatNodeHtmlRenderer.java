package in.spcct.spacedoc.md.renderer;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.common.util.StringUtils;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererCore;
import in.spcct.spacedoc.md.renderer.cache.RenderCache;
import lombok.extern.java.Log;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.CoreHtmlNodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * An extension of the CommonMark {@link FencedCodeBlockRenderer}.
 * <p>
 * Supports rendering of various custom languages, the renderers of which are currently registered in {@link in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererStore}.
 * <p>
 * If no renderer for the specified language is found, a fallback to the default renderer occurs.
 */
@Log
public class ExternalFormatNodeHtmlRenderer extends FencedCodeBlockRenderer {

    private final HtmlNodeRendererContext context;
    private final HtmlWriter htmlWriter;

    private final CoreHtmlNodeRenderer coreHtmlNodeRenderer;

    public ExternalFormatNodeHtmlRenderer(HtmlNodeRendererContext context) {
        this.context = context;
        this.htmlWriter = context.getWriter();
        this.coreHtmlNodeRenderer = new CoreHtmlNodeRenderer(context);
    }

    /**
     * Attempts to render a {@link FencedCodeBlock}.
     * <p>
     * Fenced code blocks look a little like this:
     * <pre>
     *     ```language-name
     *          ...
     *          some code in this language
     *          ...
     *     ```
     * </pre>
     * <p>
     * The language-name string is extracted, and used for determining the renderer to be used for rendering of the enclosed code.
     * <p>
     * The main rendering logic has been extracted to {@link ExternalCodeRendererCore} to simplify code reuse within this project.
     *
     * @param node
     */
    @Override
    public void render(Node node) {
        if (!(node instanceof FencedCodeBlock))
            throw new IllegalStateException("Expected node of type " + FencedCodeBlock.class.getName() +
                    ", but got " + node.getClass().getName());

        FencedCodeBlock codeBlock = (FencedCodeBlock) node;

        String languageName = codeBlock.getInfo().toLowerCase(Locale.ROOT);

        ExternalCodeRendererCore core = new ExternalCodeRendererCore();

        if (!core.canRender(languageName)) {
            //render defaults
            coreHtmlNodeRenderer.render(node);
            return;
        }

        String svg;
        RenderCache renderCache = Registry.lookup(RenderCache.class);
        String code = ((FencedCodeBlock) node).getLiteral();
        svg = renderCache.lookupOrGenerate(
                code, c -> {
                    try {
                        return core.render(languageName, c);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return generateErrorSVG(e);
                    }
                });

        Map<String, String> attributes = context.extendAttributes(node, "div", Collections.emptyMap());
        renderSVG(svg, languageName, attributes);
    }

    /**
     * Renders the given "SVG" content into an enclosing div into the {@link #htmlWriter} object.
     *
     * <pre>
     *     <div class="[languageName]-image image" [attributes]>
     *         [literalContent]
     *     </div>
     * </pre>
     *
     * @param literalContent content to render
     * @param languageName   name of the language
     * @param attributes     map of attributes to be put in the enclosing div
     */
    private void renderSVG(String literalContent, String languageName, Map<String, String> attributes) {
        htmlWriter.line();
        Map<String, String> actualAttributes = (attributes == null) ? new HashMap<>() : attributes;
        actualAttributes.put("class", languageName + "-image image");  //TODO: Make CSS classes configurable
        htmlWriter.tag("div", actualAttributes);
        htmlWriter.raw(literalContent);
        htmlWriter.tag("/div");
        htmlWriter.line();
    }

    /**
     * Creates an "SVG"/HTML string containing a serialized exception stack trace to aid with debugging.
     *
     * @param e exception to serialize
     * @return HTML string
     */
    private String generateErrorSVG(Exception e) {
        return "<div class=\"error\">" +
                "<pre>" +
                StringUtils.toStackTraceString(e) +
                "</pre>" +
                "</div>";
    }
}
