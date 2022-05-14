package in.spcct.spacedoc.config;

import in.spcct.spacedoc.config.loader.fieldmap.FieldConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Converter {
    Class<? extends FieldConverter> value();
}
