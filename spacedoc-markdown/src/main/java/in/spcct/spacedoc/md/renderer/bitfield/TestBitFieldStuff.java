package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Separator;
import lombok.SneakyThrows;

import java.io.FileWriter;

public class TestBitFieldStuff {


    @SneakyThrows
    public static void main(String[] args) {

        BitFieldRenderer bitFieldRenderer = new BitFieldRenderer(new BitFieldRenderer.Config());

        String svg = bitFieldRenderer.renderStuff(
                new Register(
                        "Register 0",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 1",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 2",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 3",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 4",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 5",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 6",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 7",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 8",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 9",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 10",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 11",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 12",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Register(
                        "Register 13",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("General Purpose Register", 3, 16)
                ),
                new Separator(),
                new Register(
                        "Register 14",
                        "Flags",
                        new Register.BitArray(null, -1, 16),
                        new Register.BitArray("reserved", 0, 8),
                        new Register.BitArray("IE", 2, 1),
                        new Register.BitArray("reserved", 0, 5),
                        new Register.BitArray("CA", 3, 1),
                        new Register.BitArray("ZE", 4, 1)
                ),
                new Separator(),
                new Register(
                        "Register 15",
                        "Program Counter",
                        new Register.BitArray(null, 0, 2),
                        new Register.BitArray("X", 1, 1),
                        new Register.BitArray("Program Counter (PC)", 2, 13 + 16)
                )
        );

        try (FileWriter fileWriter = new FileWriter("H:\\Repos\\spacedoc\\src\\test\\resources\\magic.svg")) {
            fileWriter.write(svg);
        }
    }

}
