package in.spcct.spacedoc.config.loader.newconf;

import in.spcct.spacedoc.config.ConfigProperty;

public interface ConfigValue {

    /**
     * @return Whole name of this property, including the namespace
     */
    String getName();

    /**
     * @return Declared default value. If none was declared in the {@link ConfigProperty} annotation, null is returned.
     */
    String getDefaultValue();

    /**
     * Gets the current value of this property
     *
     * @return current value
     */
    String getValue();

    /**
     * Sets a new value for this property.
     * <p>
     * Best used only at app startup.
     *
     * @param value new value. Will be converted.
     */
    void setValue(String value);

}
