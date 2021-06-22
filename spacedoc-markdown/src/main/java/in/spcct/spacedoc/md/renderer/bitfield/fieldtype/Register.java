package in.spcct.spacedoc.md.renderer.bitfield.fieldtype;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Register implements FieldType {

    private List<BitArray> bitArrays = new ArrayList<>();

    private String leftLabel;
    private String rightLabel;

    public Register() {
    }

    public Register(BitArray... bitArrays) {
        this(null, null, bitArrays);
    }

    public Register(String leftLabel, BitArray... bitArrays) {
        this(leftLabel, null, bitArrays);
    }

    public Register(String leftLabel, String rightLabel, BitArray... bitArrays) {
        this.leftLabel = leftLabel;
        this.rightLabel = rightLabel;
        this.bitArrays = Arrays.asList(bitArrays);
    }

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
