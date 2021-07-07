package in.spcct.spacedoc.md.renderer.bitfield.parser;

import in.spcct.spacedoc.common.exception.ParserException;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import org.json.JSONObject;

public interface FieldTypeParser {

    /**
     * Attempt to parse the given {@link JSONObject} into this {@link FieldType}.
     * <p>
     * When this object cannot be parsed
     *
     * @param object object to try parsing
     * @throws ParserException on major error in source object
     * @return parsed object. Nothing when parsing fails.
     */
    FieldType parse(JSONObject object) throws ParserException;

    boolean matchesType(String type);

}
