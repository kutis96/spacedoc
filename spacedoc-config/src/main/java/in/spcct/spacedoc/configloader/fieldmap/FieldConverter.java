package in.spcct.spacedoc.configloader.fieldmap;

public interface FieldConverter {

    Object convert(String source, FieldMapping fieldMapping);

}
