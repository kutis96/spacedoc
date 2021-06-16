package in.spcct.spacedoc.propconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Must annotate any {@link AbstractPropertyConfig} class to be loaded
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyFile {

    /**
     * Property file name
     * @return name of a property file configuring this class
     */
    String value();

    /**
     * Prefix of all the properties configuring this class
     * @return prefix
     */
    String prefix() default "";

}
