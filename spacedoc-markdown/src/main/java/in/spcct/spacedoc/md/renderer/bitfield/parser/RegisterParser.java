package in.spcct.spacedoc.md.renderer.bitfield.parser;

import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * {
 *     "type": ...,
 *     "label-left": "Some label",
 *     "label-right": "Some other label",
 *     "fields": [
 *          {
 *              "bits": 16,
 *              "name": "Program Counter",
 *              "type": 2
 *          },
 *          {
 *              "bits": 15,
 *              "name": "Extended PC",
 *              "type": 3
 *          },
 *          {
 *              "name": "X"
 *          }
 *     ]
 * }
 */
public class RegisterParser implements FieldTypeParser {

    private static final String PARAM_LEFT_LABEL = "label-left";
    private static final String PARAM_RIGHT_LABEL = "label-right";
    private static final String PARAM_BIT_PATTERNS = "fields";

    private static final String PARAM_BIT_ARRAY_SIZE = "bits";
    private static final String PARAM_BIT_ARRAY_LABEL = "name";
    private static final String PARAM_BIT_ARRAY_COLOR = "type";

    @Override
    public FieldType parse(JSONObject object) {
        Register register = new Register();

        register.setLeftLabel(object.optString(PARAM_LEFT_LABEL));
        register.setRightLabel(object.optString(PARAM_RIGHT_LABEL));

        register.setBitArrays(
                parseBitArrays(object.getJSONArray(PARAM_BIT_PATTERNS))
        );

        return register;
    }

    private List<Register.BitArray> parseBitArrays(JSONArray jsonArray) {
        List<Register.BitArray> results = new ArrayList<>(jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            results.add(parseBitArray(jsonObject));
        }
        return results;
    }

    private Register.BitArray parseBitArray(JSONObject jsonObject) {
        Register.BitArray bitArray = new Register.BitArray();

        bitArray.setBitColor(jsonObject.optInt(PARAM_BIT_ARRAY_COLOR, 0));
        bitArray.setNumberOfBits(jsonObject.optInt(PARAM_BIT_ARRAY_SIZE, 1));
        bitArray.setText(jsonObject.optString(PARAM_BIT_ARRAY_LABEL));

        return bitArray;
    }

    @Override
    public boolean matchesType(String type) {
        return type.equalsIgnoreCase("reg") | type.equalsIgnoreCase("register");
    }

}
