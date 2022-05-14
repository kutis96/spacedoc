package in.spcct.spacedoc.md.renderer.impl;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.common.exception.RenderingException;
import in.spcct.spacedoc.ffc.Graphviz;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;

import java.io.IOException;

/**
 * External code renderer for the "graphviz" format.
 *
 * @see "https://en.wikipedia.org/wiki/Graphviz"
 * @see "https://graphviz.org/"
 */
public class GraphvizSvgRenderer implements ExternalCodeRenderer {

    @Override
    public String renderSvg(String source) throws RenderingException {
        try {
            return Registry
                    .lookup(Graphviz.class)
                    .renderToSVG(source);

        } catch (IOException e) {
            throw new RenderingException("IO Exception during Graphviz render", e);
        } catch (InterruptedException e) {
            throw new RenderingException("Rendering interrupted, likely by timeout. Make sure the Graphviz tools are correctly installed.", e);
        }
    }

    @Override
    public String languageName() {
        return "graphviz";
    }

}
