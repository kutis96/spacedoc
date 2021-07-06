package in.spcct.spacedoc.md.renderer;

import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererCore;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.CoreHtmlNodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ExternalFormatNodeHtmlRenderer extends FencedCodeBlockRenderer {

    private final HtmlNodeRendererContext context;
    private final HtmlWriter html;

    private final CoreHtmlNodeRenderer coreHtmlNodeRenderer;

    public ExternalFormatNodeHtmlRenderer(HtmlNodeRendererContext context) {
        this.context = context;
        this.html = context.getWriter();
        this.coreHtmlNodeRenderer = new CoreHtmlNodeRenderer(context);
    }

    @Override
    public void render(Node node) {
        if (!(node instanceof FencedCodeBlock))
            throw new IllegalStateException("Expected node of type " + FencedCodeBlock.class.getName() +
                    ", but got " + node.getClass().getName());

        FencedCodeBlock codeBlock = (FencedCodeBlock) node;

        String languageName = codeBlock.getInfo().toLowerCase(Locale.ROOT);

        ExternalCodeRendererCore core = new ExternalCodeRendererCore();

        String svg;
        try {
            svg = core.render(languageName, ((FencedCodeBlock) node).getLiteral());

            if (svg == null) {
                //render defaults
                coreHtmlNodeRenderer.render(node);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            svg = generateErrorSVG(e);
        }

        Map<String, String> attributes = context.extendAttributes(node, "div", Collections.emptyMap());
        renderSVG(svg, languageName, attributes);
    }

    private void renderSVG(String literal, String languageName, Map<String, String> attributes) {
        html.line();
        Map<String, String> stuff = new HashMap<>();
        stuff.put("class", languageName + "-image image");  //TODO: Make CSS classes configurable
        html.tag("div", stuff);
        html.raw(literal);
        html.tag("/div");
        html.line();
    }

    private String generateErrorSVG(Exception e) {
        return "<svg viewBox=\"0 0 240 80\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "  <style>\n" +
                "    .small { font: italic 13px sans-serif; }\n" +
                "    .heavy { font: bold 30px sans-serif; }\n" +
                "\n" +
                "    /* Note that the color of the text is set with the    *\n" +
                "     * fill property, the color property is for HTML only */\n" +
                "    .Rrrrr { font: italic 40px serif; fill: red; }\n" +
                "  </style>\n" +
                "\n" +
                "  <text x=\"20\" y=\"35\" class=\"small\">My</text>\n" +
                "  <text x=\"40\" y=\"35\" class=\"heavy\">cat</text>\n" +
                "  <text x=\"55\" y=\"55\" class=\"small\">is</text>\n" +
                "  <text x=\"65\" y=\"55\" class=\"Rrrrr\">Grumpy!</text>\n" +
                "</svg>";
    }
}
