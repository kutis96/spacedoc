package in.spcct.spacedoc.md.renderer.bitfield.renderer;

import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.LiteXmlBuilder;

/**
 * Renders the given FieldType into an SVG builder. Somehow.
 */
public interface FieldTypeRenderer<T extends FieldType> {

    Class<T> getRenderedClass();

    /**
     * Attempts to render the given object into SVG objects.
     *
     * @param config    configures common renderer attributes and styling
     * @param context   configures current context for rendering
     * @param fieldType item to render
     * @return SVG object in a builder
     */
    @SuppressWarnings("unchecked")
    default LiteXmlBuilder render(BitFieldRenderer.Config config, BitFieldRenderer.Context context, FieldType fieldType) {
        if(!(fieldType.getClass().isAssignableFrom(getRenderedClass())))    //fieldType instanceof T
            throw new UnsupportedOperationException("Invalid object: expected " + Register.class.getCanonicalName()
                    + ", got " + fieldType.getClass().getCanonicalName());

        return renderType(config, context, (T) fieldType);
    }

    LiteXmlBuilder renderType(BitFieldRenderer.Config config, BitFieldRenderer.Context context, T fieldType);


}
