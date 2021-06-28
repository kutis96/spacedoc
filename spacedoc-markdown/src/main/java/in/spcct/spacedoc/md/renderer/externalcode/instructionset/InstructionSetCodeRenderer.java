package in.spcct.spacedoc.md.renderer.externalcode.instructionset;


import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.ConfigUtils;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Should be able to render "instruction set format" strings into beautiful tables.
 * <p>
 * Instruction set format (schematically):
 * <p>
 * file ::= ( line \n ) +
 * line ::= empty | configvalue | iset
 * <p>
 * configvalue ::= "--" [key] : [value]
 * iset ::= [bit pattern] [separator] [mnemonic] ([separator] [description])*
 * <p>
 * bit pattern ::= any string of characters.
 * 0 and 1 are treated as literal values
 * alphabetic characters are treated as variable bit field placeholders
 * ' are treated as bit field separators
 * spaces are ignored
 * separator ::= "|" character or 2+ spaces
 * mnemonic ::= alphabetic characters
 * description ::= anything
 */
public class InstructionSetCodeRenderer implements ExternalCodeRenderer {

    /**
     * Dumb way of persisting field name colors. Colors are stored by position of the given character. Somehow.
     * <p>
     * Why like this?
     *
     * TODO: Add some way of configuring field colors externally
     */
    List<Character> nameSet = new ArrayList<>(10);

    @Override
    public String languageName() {
        return "instruction-set";
    }

    @Override
    public String renderSvg(String source) {

        InstructionSetParser parser = new InstructionSetParser();

        parser.parse(source);

        BitFieldRenderer.Config rendererConfig = ConfigUtils.createRendererConfig(parser.getConfigMap());

        //TODO: Make externally configurable, somehow.
        BitFieldRenderer bitFieldRenderer = new BitFieldRenderer(rendererConfig);

        List<InstructionSetEntry> entries = parser.getEntryList();

        return bitFieldRenderer.renderStuff(
                entries.stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );

    }

    private FieldType map(InstructionSetEntry entry) {
        return Register.builder()
                .centerLeftLabel(entry.getMnemonic())
                .centerRightLabel(entry.getDescription())
                .bitArrays(
                        patternToBitArray(entry.getBitPattern())
                ).build();
    }

    private List<Register.BitArray> patternToBitArray(String bitPattern) {
        bitPattern = bitPattern.replaceAll("\\s", "");  //remove spaces

        String originalPattern = bitPattern;
        String functionalPattern = bitPattern.replaceAll("[01]", "0");

        String[] splits = functionalPattern.split("(?<=(.))(?!\\1)");  //split into groups of consecutive characters. 0 and 1 are treated as the same in constants.

        List<Register.BitArray> bitArrays = new LinkedList<>();

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

}
