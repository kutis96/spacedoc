package in.spcct.spacedoc.common.module;

import in.spcct.spacedoc.common.util.StringUtils;

public interface Module {

    default String getShortName() {
        //initial letter and all formerly uppercase letters
        String shortName = "";

        String capitalized = StringUtils.capitalizeFirstLetter(getLongName());

        shortName = capitalized.replaceAll("[a-z]", "");

        return shortName;
    }

    String getLongName();

    String getDescription();

    void run(String[] args) throws Exception;

}
