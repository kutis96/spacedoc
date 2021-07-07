package in.spcct.spacedoc.configloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Must annotate allfields to be loaded by the {@link in.spcct.spacedoc.configloader.propconfig.PropertyConfigFieldLoader},
 * or mapped by a {@link in.spcct.spacedoc.configloader.fieldmap.FieldMapper}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {

    /**
     * Name of property to be used to fill this value
     * @return
     */
    String value() default "";

    /**
     * Is this property mandatorily loaded?
     * @return
     */
    boolean required() default true;


}
