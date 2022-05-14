package in.spcct.spacedoc.config;

import in.spcct.spacedoc.config.loader.propconfig.AbstractPropertiesFileConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Must annotate any {@link AbstractPropertiesFileConfig} class to be loaded
 *
 * @deprecated as file configs loaded in other than the standard way are generally deemed unnecessary
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ConfigFile {

    /**
     * Property file name
     *
     * @return name of a property file configuring this class
     */
    String fileName();

}
