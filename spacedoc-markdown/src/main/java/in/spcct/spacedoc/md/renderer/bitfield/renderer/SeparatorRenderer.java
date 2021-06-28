package in.spcct.spacedoc.md.renderer.bitfield.renderer;

import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Separator;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.LiteXmlBuilder;

public class SeparatorRenderer implements FieldTypeRenderer<Separator> {

    @Override
    public Class<Separator> getRenderedClass() {
        return Separator.class;
    }

    @Override
    public LiteXmlBuilder renderType(BitFieldRenderer.Config config, BitFieldRenderer.Context context, Separator fieldConfig) {
        context.totalVerticalOffset += config.getSeparatorSize();
        return null;
    }
}
