package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.RenderingException;
import in.spcct.spacedoc.md.renderer.bitfield.parser.ParserException;

public class BitFieldRendererExtension implements ExternalCodeRenderer {
    @Override
    public String languageName() {
        return "bitfield";
    }

    @Override
    public String renderSvg(String source) throws RenderingException {

        BitFieldParser parser;
        try {
            parser = new BitFieldParser(source);
        } catch (ParserException e) {
            throw new RenderingException("Failed to render \n" + source, e);
        }

        BitFieldRenderer renderer = new BitFieldRenderer(
                parser.getConfig()
        );

        return renderer.renderStuff(
                parser.getData()
        );

    }

}
