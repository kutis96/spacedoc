package in.spcct.spacedoc.md.renderer.bitfield.fieldtype;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Register implements FieldType {

    @Builder.Default
    private List<BitArray> bitArrays = new ArrayList<>();

    private String topLeftLabel;
    private String topRightLabel;
    private String bottomLeftLabel;
    private String bottomRightLabel;
    private String centerLeftLabel;
    private String centerRightLabel;

    @Builder.Default
    private int numberOfRows = 1;

    @Builder.Default
    private boolean breakInTheMiddle = false;

    @Data
    public static class BitArray {
        public BitArray() {
        }

        public BitArray(String text, int bitColor, int numberOfBits) {
            this.text = text;
            this.bitColor = bitColor;
            this.numberOfBits = numberOfBits;
        }

        String text;

        String href;

        // 0,1 = white
        // 2~9 = colors
        // -1  = blank! Must not be enclosed.*
        int bitColor;

        int numberOfBits;

        public boolean isEnclosed() {
            return bitColor != -1;
        }

        public boolean botherWithTicks() {
            return isEnclosed();
//            return !( //do not bother whenever any of this applies
//                    !isEnclosed() || (text == null) || text.isBlank()
//            );
        }
    }

}
