package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.common.exception.ParserException;
import in.spcct.spacedoc.common.exception.RenderingException;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;

/**
 * {@link ExternalCodeRenderer} for rendering "bitfield" DSL-formatted strings.
 * <p>
 * The BitField DSL is an application of JSON, having the following format:
 * <pre>
 * {
 *     "fields": [
 *          {
 *              "type": "type name"
 *              ...
 *          },
 *          {
 *              "type": "separator"     //see {@link in.spcct.spacedoc.md.renderer.bitfield.parser.SeparatorParser}
 *          },
 *          {
 *              "type": "reg",          //see {@link in.spcct.spacedoc.md.renderer.bitfield.parser.RegisterParser}
 *              ...
 *          },
 *          {
 *
 *          }
 *          ...
 *     ],
 *     "config": {
 *          "key": "value",
 *          ...
 *     }
 * }
 * </pre>
 * <p>
 * The "fields" array contains the fields to be rendered, in this order.
 * <p>
 * Each field contains a "type" string, specifying the {@link in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType}.
 * This string is specifically parsed using several instances of {@link in.spcct.spacedoc.md.renderer.bitfield.parser.FieldTypeParser}, typically one per FieldType.
 * <p>
 * For more info about the formats specific of each type, see the implementations of the FieldTypeParser class, for example:
 * {@link in.spcct.spacedoc.md.renderer.bitfield.parser.RegisterParser} and {@link in.spcct.spacedoc.md.renderer.bitfield.parser.SeparatorParser}.
 * <p>
 * The "config" object contains keys and values that are eventually translated to a {@link BitFieldRenderer.Config} object.
 * See more info about the configuration and key names in there.
 */
public class BitFieldExternalCodeRenderer implements ExternalCodeRenderer {

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
