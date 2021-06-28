package in.spcct.spacedoc.configloader;

import in.spcct.spacedoc.configloader.propconfig.AbstractPropertiesFileConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Must annotate any {@link AbstractPropertiesFileConfig} class to be loaded
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigFile {

    /**
     * Property file name
     * @return name of a property file configuring this class
     */
    String value();

    /**
     * Prefix of all the properties configuring this class. One can also think of it as a namespace or a path prefix.
     *
     * Consider a .properties file:
     *
     * aaa.thing1 = ...
     * aaa.thing2 = ...
     * bbb.thing1 = ...
     * bbb.thing2 = ...
     * ...
     *
     * When this property is set to "aaa", only the items prefixed with "aaa." will be loaded.
     *
     * @return prefix
     */
    String prefix() default "";

}
