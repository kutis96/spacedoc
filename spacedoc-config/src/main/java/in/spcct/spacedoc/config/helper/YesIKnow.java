package in.spcct.spacedoc.config.helper;

import java.util.Map;

public class YesIKnow {
    public static Map<String, Object> thatStringsAreActuallyObjects(Map<String, String> smh) {
        return (Map) smh;
    }
}
