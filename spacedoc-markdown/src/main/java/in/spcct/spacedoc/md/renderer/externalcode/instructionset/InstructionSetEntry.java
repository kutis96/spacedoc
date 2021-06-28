package in.spcct.spacedoc.md.renderer.externalcode.instructionset;

import lombok.Data;

@Data
public class InstructionSetEntry {
    private String bitPattern;
    private String mnemonic;
    private String description;
}