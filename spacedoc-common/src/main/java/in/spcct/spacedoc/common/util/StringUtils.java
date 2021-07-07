package in.spcct.spacedoc.common.util;

import lombok.NonNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * A set of utilities for dealing with Strings.
 */
public class StringUtils {

    /**
     * Attempts to capitalize the first character of the input string and return the resulting string.
     *
     * @param what string to be capitalized
     * @return resulting string, or null when null is provided
     */
    public static String capitalizeFirstLetter(String what) {
        if (what == null || what.isEmpty()) {
            return what;
        }

        return what.substring(0, 1).toUpperCase() + what.substring(1);
    }

    /**
     * Converts a {@link Throwable}'s stack trace into a string.
     *
     * @param throwable object to print the stack trace of
     * @return string representation of the stack trace
     */
    public static String toStackTraceString(@NonNull Throwable throwable) {
        try (StringWriter stringWriter = new StringWriter();
             PrintWriter printWriter = new PrintWriter(stringWriter)) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        } catch (IOException ioException) {
            throwable.printStackTrace();
            ioException.printStackTrace();
            return null;
        }
    }

    /**
     * Converts an InputStream into a String.
     *
     * @param inputStream input stream
     * @return string representation of the stream.
     */
    public static String serialize(@NonNull InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

}
