package in.spcct.spacedoc.config.loader.fieldmap.converter;

import in.spcct.spacedoc.config.loader.fieldmap.FieldConverter;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapping;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TypeConverterTestBase {

    abstract FieldConverter getDUT();

    abstract Class<?> getConfigUnderTestClass();

    <T> T genericConversionTest(String inputValue, T expectedValue, Class<T> fieldType, String fieldName) {
        FieldMapping fieldMapping = getFieldMapping(fieldName);
        assertEquals(fieldType, fieldMapping.getField().getType());

        Object result = getDUT().convert(inputValue, fieldMapping);

        assertTrue(expectedValue.getClass().isAssignableFrom(result.getClass()), "Illegal result type");

        T typedResult = (T) result;
        assertEquals(expectedValue, typedResult);
        return typedResult;
    }


    private FieldMapping getFieldMapping(String name) {

        Optional<Field> field = FieldUtils.getAllFieldsList(getConfigUnderTestClass())
                .stream().filter(f -> f.getName().equals(name))
                .findFirst();

        assertFalse(field.isEmpty(), "No field could be mapped for name " + name);

        return new FieldMapping(field.get(), name, false);
    }


}
