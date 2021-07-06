package in.spcct.spacedoc.ffc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class SerializationUtil {

    public static String serialize(InputStream errorStream) {
        return new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public static String concatenate(List<String> lines) {
        StringBuilder stringBuilder = new StringBuilder();

        lines.forEach(l -> stringBuilder.append(l).append('\n'));

        return stringBuilder.toString();
    }

}
