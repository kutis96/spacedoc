package in.spcct.spacedoc.md.renderer.externalcode.instructionset;

import lombok.Data;

/**
 * A simple instruction set listing entry.
 * <p>
 * Specifies the bit pattern of the instruction bits,
 * the assembly-language mnemonic for this instruction,
 * and a short description or note explaining the instruction's function.
 */
@Data
public class InstructionSetEntry {
    private String bitPattern;
    private String mnemonic;
    private String description;
}