package in.spcct.spacedoc.md.renderer.bitfield.parser;

import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Separator;
import org.json.JSONObject;

/**
 * Parses the "Separator" field type.
 * <p>
 * Currently takes no arguments/values from the field-specifying object.
 * <p>
 * Uses "sep" or "separator" type strings.
 */
public class SeparatorParser implements FieldTypeParser {

    @Override
    public FieldType parse(JSONObject object) {
        return new Separator(); //Separator currently takes no arguments
    }

    @Override
    public boolean matchesType(String type) {
        return type.equalsIgnoreCase("sep") | type.equalsIgnoreCase("separator");
    }

}
