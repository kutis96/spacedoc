package in.spcct.spacedoc.md.renderer.externalcode.memorymap;

import lombok.Data;

@Data
public class MemoryMapEntry {

    private String startingAddress = null;
    private String endingAddress = null;

    private Integer size = null;
    private Boolean isBroken = false;

    private String label = null;

    public boolean isLarge() {
        return startingAddress != null && endingAddress != null;
    }

}
