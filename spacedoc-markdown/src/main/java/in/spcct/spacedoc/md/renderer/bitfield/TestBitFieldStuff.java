package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.md.Setup;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.util.Arrays;

public class TestBitFieldStuff {


    @SneakyThrows
    public static void main(String[] args) {
        Setup.registerAll();

        BitFieldRenderer bitFieldRenderer = new BitFieldRenderer(new BitFieldRenderer.Config());

        String svg = bitFieldRenderer.renderStuff(
                Register.builder()
                        .centerLeftLabel("0x0000")
                        .centerRightLabel("Zero Register")
                        .bitArrays(Arrays.asList(
                                new Register.BitArray(null, 0, 16)
                        ))
                        .build(),
                Register.builder()
                        .topLeftLabel("0x0001")
                        .bottomLeftLabel("0x000F")
                        .centerRightLabel("CPU registers")
                        .bitArrays(Arrays.asList(
                                new Register.BitArray(null, 0, 16)
                        ))
                        .numberOfRows(3)
                        .breakInTheMiddle(true)
                        .build(),
                Register.builder()
                        .topLeftLabel("0x0010")
                        .bottomLeftLabel("0xFEFF")
                        .centerRightLabel("RAM")
                        .bitArrays(Arrays.asList(
                                new Register.BitArray(null, 0, 16)
                        ))
                        .numberOfRows(5)
                        .breakInTheMiddle(true)
                        .build(),
                Register.builder()
                        .topLeftLabel("Top left")
                        .centerLeftLabel("Center left")
                        .bottomLeftLabel("Bottom left")
                        .topRightLabel("Top right")
                        .centerRightLabel("Center right")
                        .bottomRightLabel("Bottom right")
                        .bitArrays(Arrays.asList(
                            new Register.BitArray("Text", 0, 16)
                        ))
                        .numberOfRows(5)
                        .breakInTheMiddle(false)
                        .build()
        );

        try (FileWriter fileWriter = new FileWriter("H:\\Repos\\spacedoc\\src\\test\\resources\\magic.svg")) {
            fileWriter.write(svg);
        }
    }

}
