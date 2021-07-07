package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.common.exception.ParserException;
import in.spcct.spacedoc.configloader.objectmapconfig.ObjectMapConfigSource;
import in.spcct.spacedoc.configloader.objectmapconfig.ObjectMapFieldLoader;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.parser.FieldTypeParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Parses the "BitField DSL" formatted strings into the respective configuration object and field type list.
 * <p>
 * For the specifics and format of this DSL, see the description at {@link BitFieldExternalCodeRenderer}
 */
public class BitFieldParser {

    private static final String JSON_CONFIG_NAME = "config";
    private static final String JSON_DATA_NAME = "fields";
    private static final String JSON_DATA_TYPE_NAME = "type";
    BitFieldRenderer.Config config;
    List<FieldType> data;

    public BitFieldParser(String jsonString) throws ParserException {

        JSONObject sourceObject = new JSONObject(jsonString);

        //Keep the format fairly wavedrom-esque: config object for configuring all the things,
        // and a "bitfield" object containing all the data for rendering.
        JSONObject configObject = null;

        if (sourceObject.has(JSON_CONFIG_NAME)) {
            configObject = sourceObject.getJSONObject(JSON_CONFIG_NAME);
        }

        JSONArray dataObjects = null;

        if (sourceObject.has(JSON_DATA_NAME)) {
            dataObjects = sourceObject.getJSONArray(JSON_DATA_NAME);
        }

        if (dataObjects == null)
            return; //Nothing to do here

        parseConfig(configObject);
        parseData(dataObjects);

    }

    private void parseConfig(JSONObject configObject) {
        config = new BitFieldRenderer.Config();

        if(configObject == null)
            return; //use defaults

        ObjectMapFieldLoader fieldLoader = new ObjectMapFieldLoader();
        fieldLoader.loadFields(
                new ObjectMapConfigSource(configObject.toMap()),
                config, null
        );
    }

    private void parseData(JSONArray dataObjects) throws ParserException {

        List<FieldTypeParser> parsers = SillyCDI
                .lookupAll(FieldTypeParser.class, 0);

        data = new ArrayList<>(dataObjects.length());

        for (int i = 0; i < dataObjects.length(); i++) {
            JSONObject dataObject = dataObjects.getJSONObject(i);

            if (!dataObject.has(JSON_DATA_TYPE_NAME))
                throw new ParserException(dataObject + " has no 'type' field. Cannot determine renderer for this object.");

            Optional<FieldTypeParser> parser = parsers.stream()
                    .filter(p -> p.matchesType(dataObject.getString(JSON_DATA_TYPE_NAME)))
                    .findAny();

            if (parser.isEmpty())
                throw new ParserException("No parser found for " + dataObject + ". Please ensure the correct renderer for this type of object is installed and that this type name is spelled correctly.");

            FieldTypeParser fieldTypeParser = parser.get();

            data.add(fieldTypeParser.parse(dataObject));
        }
    }

    public BitFieldRenderer.Config getConfig() {
        return config;
    }

    public List<FieldType> getData() {
        return data;
    }
}
