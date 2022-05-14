package in.spcct.spacedoc.config.loader.fieldmap.converter;

import in.spcct.spacedoc.config.loader.fieldmap.FieldConverter;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicTypeConverterTest extends TypeConverterTestBase {

    BasicTypeConverter dut = new BasicTypeConverter();

    @Test
    void convertBasicInt() {
        Class<Integer> fieldType = int.class;
        String fieldName = "basicInt";
        genericConversionTest("555", 555, fieldType, fieldName);

        assertThrows(
                Exception.class,
                () -> genericConversionTest("aeiou", 555, fieldType, fieldName)
        );
    }

    @Test
    void convertBoxedInt() {
        Class<Integer> fieldType = Integer.class;
        String fieldName = "boxedInt";

        genericConversionTest("123", 123, fieldType, fieldName);

        assertThrows(
                Exception.class,
                () -> genericConversionTest("aeiou", 555, fieldType, fieldName)
        );
    }

    @Test
    void convertBasicLong() {
        Class<Long> fieldType = long.class;
        String fieldName = "basicLong";

        genericConversionTest("333", 333L, fieldType, fieldName);

        assertThrows(
                Exception.class,
                () -> genericConversionTest("aeiou", 555L, fieldType, fieldName)
        );
    }

    @Test
    void convertBoxedLong() {
        Class<Long> fieldType = Long.class;
        String fieldName = "boxedLong";
        genericConversionTest("666", 666L, fieldType, fieldName);

        assertThrows(
                Exception.class,
                () -> genericConversionTest("aeiou", 666L, fieldType, fieldName)
        );
    }

    @Test
    void convertString() {
        genericConversionTest("Hello humans and things!", "Hello humans and things!", String.class, "string");
    }

    @Test
    void convertBasicBool() {
        String fieldName = "basicBool";
        Class<Boolean> fieldType = boolean.class;
        genericConversionTest("true", true, fieldType, fieldName);
        genericConversionTest("false", false, fieldType, fieldName);

        assertThrows(
                Exception.class,
                () -> genericConversionTest("1", true, fieldType, fieldName)
        );
        assertThrows(
                Exception.class,
                () -> genericConversionTest("0", false, fieldType, fieldName)
        );
        assertThrows(
                Exception.class,
                () -> genericConversionTest("aaaa", true, fieldType, fieldName)
        );
        assertThrows(
                Exception.class,
                () -> genericConversionTest("aaaa", false, fieldType, fieldName)
        );
    }

    @Test
    void convertBoxedBool() {
        String fieldName = "boxedBool";
        Class<Boolean> fieldType = Boolean.class;
        genericConversionTest("true", true, fieldType, fieldName);
        genericConversionTest("false", false, fieldType, fieldName);

        assertThrows(
                Exception.class,
                () -> genericConversionTest("1", true, fieldType, fieldName)
        );
        assertThrows(
                Exception.class,
                () -> genericConversionTest("0", false, fieldType, fieldName)
        );
        assertThrows(
                Exception.class,
                () -> genericConversionTest("aaaa", true, fieldType, fieldName)
        );
        assertThrows(
                Exception.class,
                () -> genericConversionTest("aaaa", false, fieldType, fieldName)
        );
    }


    @Override
    FieldConverter getDUT() {
        return dut;
    }

    @Override
    Class<?> getConfigUnderTestClass() {
        return ConfigUnderTest.class;
    }

    @Data
    static class ConfigUnderTest { //realism.

        int basicInt;
        Integer boxedInt;

        long basicLong;
        Long boxedLong;

        String string;

        boolean basicBool;
        Boolean boxedBool;

    }

}