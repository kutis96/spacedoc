package in.spcct.spacedoc.md.renderer.impl;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.ffc.Wavedrom;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.RenderingException;

import java.io.IOException;

public class WavedromSvgRenderer implements ExternalCodeRenderer {

    @Override
    public String renderSvg(String json) throws RenderingException {

        try {
            return SillyCDI
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
