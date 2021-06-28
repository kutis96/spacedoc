package in.spcct.spacedoc.md.renderer;


import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Should be able to render "instruction set format" strings into beautiful tables.
 * <p>
 * Wavedrom's bitfield is nearly what I wanted
 */
public class InstructionSetRendererExtension implements ExternalCodeRenderer {

    List<Character> nameSet = new ArrayList<>(10);

    @Override
    public String languageName() {
        return "instruction-set";
    }

    @Override
    public String renderSvg(String source) {

        List<Entry> entries = parse(source);

        //TODO: Make externally configurable, somehow.
        BitFieldRenderer bitFieldRenderer = new BitFieldRenderer(new BitFieldRenderer.Config());

        return bitFieldRenderer.renderStuff(
                entries.stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );

    }

    private FieldType map(Entry entry) {
        return Register.builder()
                .centerLeftLabel(entry.getMnemonic())
                .centerRightLabel(entry.getDescription())
                .bitArrays(
                        mapBitArray(entry.getBitPattern())
                ).build();
    }

    private List<Register.BitArray> mapBitArray(String bitPattern) {
        bitPattern = bitPattern.replaceAll("\\s", "");  //remove spaces

        String originalPattern = bitPattern;
        String functionalPattern = bitPattern.replaceAll("[01]", "0");

        String[] splits = functionalPattern.split("(?<=(.))(?!\\1)");  //split into groups of consecutive characters. 0 and 1 are treated as the same in constants.

        List<Register.BitArray> bitArrays = new LinkedList<>();

        int colorCounter = 0;

        int indexCounter = 0;
        for (String s : splits) {
            int groupSize = s.length();

            String originalText = originalPattern.substring(indexCounter, indexCounter + groupSize);

            indexCounter += groupSize;

            Register.BitArray entry = new Register.BitArray();
            entry.setNumberOfBits(groupSize);

            if (s.startsWith("0")) {
                //binary constant
                entry.setText(originalText);
                entry.setBitColor(0);
            } else if (s.startsWith("'")) {
                //separator, do not render
                entry = null;
            } else {
                //variable
                Character name = s.charAt(0);

                entry.setText((name + "").toUpperCase());

                int color;

                if (!nameSet.contains(name))
                    nameSet.add(name);

                color = 2 + (nameSet.indexOf(name) % (10 - 2));

                entry.setBitColor(color);
            }

            if (entry != null)
                bitArrays.add(entry);
        }

        return bitArrays;
    }

    private List<Entry> parse(String source) {
        return source.lines()
                .map(this::parseLine)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Format: bit pattern | mnemonic | description
     *
     * @param source line of instruction set listing source code
     * @return instruction set entry
     */
    private Entry parseLine(String source) {
        if (source.isEmpty() || source.isBlank())
            return null;

        String[] parts = source.split("\\s{2,}|\\|");

        Entry entry = new Entry();

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

        return entry;
    }

    @Data
    private static class Entry {
        private String bitPattern;
        private String mnemonic;
        private String description;
    }
}
