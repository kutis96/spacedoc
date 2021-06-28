package in.spcct.spacedoc.configloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Must annotate all {@link AbstractPropertyConfig} fields to be loaded
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
