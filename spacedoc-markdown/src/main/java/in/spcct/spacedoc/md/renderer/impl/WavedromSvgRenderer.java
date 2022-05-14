package in.spcct.spacedoc.md.renderer.impl;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.common.exception.RenderingException;
import in.spcct.spacedoc.ffc.Wavedrom;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;

import java.io.IOException;

/**
 * External code renderer for the "wavedrom" format.
 * <p>
 * Renders https://github.com/wavedrom/wavedrom formatted JSON5 source strings to SVG.
 */
public class WavedromSvgRenderer implements ExternalCodeRenderer {

    @Override
    public String renderSvg(String json) throws RenderingException {

        try {
            return Registry
                    .lookup(Wavedrom.class)
                    .renderToSVG(json);

        } catch (IOException e) {
            throw new RenderingException("IO Exception during Wavedrom render", e);
        } catch (InterruptedException e) {
            throw new RenderingException("Rendering interrupted, likely by timeout. Make sure the Wavedrom tools are correctly installed.", e);
        }

    }

    @Override
    public String languageName() {
        return "wavedrom";
    }

}
