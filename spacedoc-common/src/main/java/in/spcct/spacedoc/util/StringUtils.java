package in.spcct.spacedoc.util;

public class StringUtils {

    public static String capitalizeFirstLetter(String what) {
        if (what == null || what.isEmpty()) {
            return what;
        }

        return what.substring(0, 1).toUpperCase() + what.substring(1);
    }

}
