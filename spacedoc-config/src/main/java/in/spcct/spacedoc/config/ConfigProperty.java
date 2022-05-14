package in.spcct.spacedoc.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Must annotate all fields to be loaded by the {@link in.spcct.spacedoc.config.loader.FieldLoader},
 * or mapped by a {@link in.spcct.spacedoc.config.loader.fieldmap.FieldMapper}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {

    /**
     * Name of property to be used to fill this value
     *
     * @return
     */
    String name(); //Default removed - keep explicit for easier lookup

    String defaultValue() default "";

    /**
     * Is this property mandatorily loaded?
     *
     * @return
     */
    boolean required() default false;


}
