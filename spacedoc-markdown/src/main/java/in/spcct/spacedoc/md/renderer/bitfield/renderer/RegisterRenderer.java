package in.spcct.spacedoc.md.renderer.bitfield.renderer;

import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.LiteXmlBuilder;

import static in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.SvgUtils.*;

public class RegisterRenderer implements FieldTypeRenderer<Register> {

    @Override
    public Class<Register> getRenderedClass() {
        return Register.class;
    }

    @Override
    public LiteXmlBuilder renderType(BitFieldRenderer.Config config, BitFieldRenderer.Context context, Register fieldType) {
        LiteXmlBuilder laneContainer = new LiteXmlBuilder("g")
                .attribute("transform", "translate({0},{1})", 0, context.totalVerticalOffset);


        int horizontalOffset = config.fieldHorizontalOffset;

        if (fieldType.getLeftLabel() != null) {
            laneContainer.add(
                    text(fieldType.getLeftLabel(), horizontalOffset + config.leftLabelHorizontalOffset, config.laneHeight / 2 + config.fontSize / 2 + config.labelVerticalOffset)
                            .attribute("text-anchor", "start")
            );
        }

        for (Register.BitArray bitArray : fieldType.getBitArrays()) {

            LiteXmlBuilder g = new LiteXmlBuilder("g")
                    .attribute("transform", "translate({0},{1})", horizontalOffset, 0);

            int width = bitArray.getNumberOfBits() * config.bitWidth;

            //text
            laneContainer.add(text(bitArray.getText(), horizontalOffset + width / 2, config.laneHeight / 2 + config.fontSize / 2 + config.labelVerticalOffset));


            horizontalOffset += width;

            //box
            //the fill does it.
//            g.add(line(null, width, null, null));
//            g.add(line(null, null, null, config.laneHeight));
//            g.add(line(null, width, config.laneHeight, config.laneHeight));
//            g.add(line(width, width, null, config.laneHeight));

            //ticks
            if (bitArray.botherWithTicks()) {
                for (int i = 1; i < bitArray.getNumberOfBits(); i++) {

                    int x = config.bitWidth * i;
                    g.add(line(x, x, null, config.tickHeight)); //upper
                    g.add(line(x, x, config.laneHeight, config.laneHeight - config.tickHeight)); //lower
                }
            }

            //fill + box
            // <rect x="293" width="98" height="26" style="fill-opacity:0.1;fill:hsl(80,100%,50%)"/>

            if (bitArray.isEnclosed()) {

                g.add(rect(null, null, width, config.laneHeight)
                        .attribute("style", "fill-opacity:{0};{1}", 0.1, bigBrainFill(bitArray.getBitColor())));

            }

            LiteXmlBuilder boxContainer = new LiteXmlBuilder("g")
                    .attribute("stroke", "black")
                    .attribute("stroke-width", 1)
                    .attribute("stroke-linecap", "round")
                    .add(g);
            laneContainer.add(boxContainer);
        }

        if (fieldType.getRightLabel() != null) {
            laneContainer.add(
                    text(fieldType.getRightLabel(), horizontalOffset + config.rightLabelHorizontalOffset, config.laneHeight / 2 + config.fontSize / 2 + config.labelVerticalOffset)
                            .attribute("text-anchor", "start")
            );
        }

        context.totalVerticalOffset += config.laneHeight;
        return laneContainer;
    }



    private String dumbColor(int n) {
        n = n % 10;
        return (new String[]{
                null,       //0
                null,       //1
                "red",      //2
                "green",    //3
                "blue",     //4
                "cyan",     //5
                "magenta",  //6
                "yellow",   //7
                "orange",   //8
                "navy",     //9
        })[n];

    }

    private String bigBrainFill(int n) {

        Integer[] hslValues = new Integer[]{
                null,
                null,
                0,
                80,
                170,
                45,
                126,
                215,
                null,
                null
        };

//        Integer hue = (n < 0 || n >= hslValues.length) ? null : hslValues[n];
//
//        return (hue == null) ? ""
//                    : "fill:hsl(" + hue + ",100%,50%)";

        String color = dumbColor(n);

        return (color == null) ? ""
                : "fill:" + color;

    }
}
