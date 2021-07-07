package in.spcct.spacedoc.md.renderer.externalcode.memorymap;

import lombok.Data;

/**
 * Represents a memory map entry, containing a starting and ending address of a range,
 * the number of lines this entry should take in the final image,
 * whether it should be rendered as a continuous block or not, and a label.
 */
@Data
public class MemoryMapEntry {

    private String startingAddress = null;
    private String endingAddress = null;

    /**
     * Represents the number of lines this entry should take up in the final image
     */
    private Integer size = null;

    /**
     * When false, this entry will be rendered as a continuous block.
     * <p>
     * When true, this entry will be rendered as a block with a break in the middle.
     */
    private Boolean isBroken = false;

    private String label = null;

    /**
     * Determines whether this block represents a range of addresses or not.
     *
     * @return true when it indeed does represent a range, false when it only represents a single value.
     */
    public boolean isRange() {
        return startingAddress != null && endingAddress != null;
    }

}
