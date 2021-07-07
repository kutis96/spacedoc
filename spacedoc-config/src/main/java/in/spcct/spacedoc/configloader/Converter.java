package in.spcct.spacedoc.configloader;

import in.spcct.spacedoc.configloader.fieldmap.FieldConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Converter {
    Class<? extends FieldConverter> value();
}
