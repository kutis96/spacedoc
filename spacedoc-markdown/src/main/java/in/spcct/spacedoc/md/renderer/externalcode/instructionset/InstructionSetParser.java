package in.spcct.spacedoc.md.renderer.externalcode.instructionset;

import in.spcct.spacedoc.md.renderer.externalcode.SimpleFormatParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InstructionSetParser extends SimpleFormatParser {

    private final HashMap<String, String> configMap = new HashMap<>();
    private final List<InstructionSetEntry> entryList = new LinkedList<>();

    @Override
    public void dealWithConfigEntry(KVEntry entry) {
        configMap.put(entry.getKey(), entry.getValue());
    }

    @Override
    public void dealWithSourceLine(String line) {
        mapSourceLine(line).ifPresent(entryList::add);
    }

    private Optional<InstructionSetEntry> mapSourceLine(String sourceLine) {
        if (sourceLine.isEmpty() || sourceLine.isBlank())
            return Optional.empty();

        String[] parts = sourceLine.split("\\s{2,}|\\|");

        InstructionSetEntry entry = new InstructionSetEntry();

        //assume the bit pattern is always there when stuff's not empty
        entry.setBitPattern(parts[0]);

        if (parts.length > 1) {
            //we've got a mnemonic.
            entry.setMnemonic(parts[1]);
        }

        if (parts.length > 2) {
            //we've got some description, it seems.
            //Mild overkill.
            StringBuilder desc = new StringBuilder(parts[2]);
            for (int i = 3; i < parts.length; i++) {
                desc.append("|").append(parts[i]);
            }
            entry.setDescription(desc.toString());
        }

        return Optional.of(entry);
    }

    public HashMap<String, String> getConfigMap() {
        return configMap;
    }

    public List<InstructionSetEntry> getEntryList() {
        return entryList;
    }
}
