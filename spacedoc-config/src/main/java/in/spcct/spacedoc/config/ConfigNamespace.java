package in.spcct.spacedoc.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates the base namespace the config properties in a this-annotated config class are in.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigNamespace {

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

