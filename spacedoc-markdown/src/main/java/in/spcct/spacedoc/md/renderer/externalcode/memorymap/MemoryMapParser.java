package in.spcct.spacedoc.md.renderer.externalcode.memorymap;

import in.spcct.spacedoc.md.renderer.externalcode.SimpleFormatParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryMapParser extends SimpleFormatParser {

    private final HashMap<String, String> configMap = new HashMap<>();

    private final List<MemoryMapEntry> entryList = new LinkedList<>();

    @Override
    public void dealWithConfigEntry(KVEntry entry) {
        configMap.put(entry.getKey(), entry.getValue());
    }

    private static final Pattern pattern = Pattern.compile(
        "\\[\\s*(?<off1>[0-9xA-Z_]+)\\s*((?<sep>[\\-\\~])\\s*(?<off2>[0-9xA-Z_]+))?\\s*\\](\\(\\s*(?<size>[0-9]+)\\s*\\))?\\s*(?<label>.+)"
    );

    //off1 = offset1
    //off2 = offset2
    //size
    //label

    // [0x0000 - 0x0000](6) label   - continuous, size 6
    // [0x0000 - 0x0000] label      - continuous, size default
    // [0x0000 ~ 0xFFFF](6) label   - broken, size 6
    // [0x0000 ~ 0xFFFF] label      - broken, size default
    // [0x0000]          label      - single entry, size 1
    @Override
    public void dealWithSourceLine(String line) {
        mapSourceLine(line).ifPresent(entryList::add);
    }

    private Optional<MemoryMapEntry> mapSourceLine(String sourceLine) {
        if (sourceLine.isEmpty() || sourceLine.isBlank())
            return Optional.empty();

        Matcher matcher = pattern.matcher(sourceLine);

        if(!matcher.matches())
            throw new UnsupportedOperationException("Cannot parse memory map line: '" + sourceLine + "'");

        String offset1 = matcher.group("off1");
        String offset2 = matcher.group("off2");
        String size = matcher.group("size");
        String label = matcher.group("label");
        String separator = matcher.group("sep");

        MemoryMapEntry result = new MemoryMapEntry();

        result.setStartingAddress(offset1);
        result.setEndingAddress(offset2);

        if(size != null) {
            result.setSize(Integer.parseInt(size));
        }

        result.setLabel(label);

        result.setIsBroken("~".equals(separator));

        return Optional.of(result);

    }

    public HashMap<String, String> getConfigMap() {
        return configMap;
    }

    public List<MemoryMapEntry> getEntryList() {
        return entryList;
    }
}
