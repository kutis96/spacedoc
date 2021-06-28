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
    public LiteXmlBuilder renderType(BitFieldRenderer.Config config, BitFieldRenderer.Context context, Register register) {
        LiteXmlBuilder laneContainer = new LiteXmlBuilder("g")
                .attribute("transform", "translate({0},{1})", 0, context.totalVerticalOffset);


        int horizontalOffset = config.getBitFieldHorizontalOffset();
        int wholeFieldHeight = config.getLaneHeight() * register.getNumberOfRows();

        drawLeftLabels(config, register, laneContainer, horizontalOffset, wholeFieldHeight);

        boolean broken = register.isBreakInTheMiddle();

        for (Register.BitArray bitArray : register.getBitArrays()) {

            int arrayWidth = bitArray.getNumberOfBits() * config.getBitWidth();

            LiteXmlBuilder boxGroup = new LiteXmlBuilder("g")
                    .attribute("transform", "translate({0},{1})", horizontalOffset, 0);

            //fill + box
            drawFill(config, bitArray, boxGroup, arrayWidth, wholeFieldHeight, broken);

            //ticks
            drawTicks(config, bitArray, boxGroup, wholeFieldHeight);
            LiteXmlBuilder boxContainer = new LiteXmlBuilder("g")
                    .attribute("stroke", "black")
                    .attribute("stroke-width", 1)
                    .attribute("stroke-linecap", "round")
                    .add(boxGroup);
            laneContainer.add(boxContainer);

            //center label
            drawLabel(config, laneContainer, horizontalOffset, wholeFieldHeight, bitArray, arrayWidth);

            horizontalOffset += arrayWidth;
        }


        drawRightLabels(config, register, laneContainer, horizontalOffset, wholeFieldHeight);

        context.totalVerticalOffset += wholeFieldHeight;
        return laneContainer;
    }

    private void drawLabel(BitFieldRenderer.Config config, LiteXmlBuilder laneContainer, int horizontalOffset, int wholeFieldHeight, Register.BitArray bitArray, int arrayWidth) {
        laneContainer.add(
                text(bitArray.getText(),
                        horizontalOffset + arrayWidth / 2,
                        wholeFieldHeight / 2 + config.getFontSize() / 2 + config.getLabelVerticalOffset()
                )
        );
    }

    private void drawLeftLabels(
            BitFieldRenderer.Config config,
            Register register,
            LiteXmlBuilder laneContainer,
            int horizontalOffset,
            int wholeFieldHeight
    ) {
        int leftLabelHorizontalOffset = config.getLeftLabelHorizontalOffset();
        int labelVerticalOffset = config.getLabelVerticalOffset();
        int fontSize = config.getFontSize();
        int laneHeight = config.getLaneHeight();

        if (register.getTopLeftLabel() != null) {
            laneContainer.add(
                    text(
                            register.getTopLeftLabel(),
                            horizontalOffset + leftLabelHorizontalOffset,
                            laneHeight / 2 + fontSize / 2 + labelVerticalOffset
                    )
                            .attribute("text-anchor", "start")
            );
        }
        if (register.getCenterLeftLabel() != null) {
            laneContainer.add(
                    text(
                            register.getCenterLeftLabel(),
                            horizontalOffset + leftLabelHorizontalOffset,
                            wholeFieldHeight / 2 + fontSize / 2 + labelVerticalOffset
                    )
                            .attribute("text-anchor", "start")
            );
        }
        if (register.getBottomLeftLabel() != null) {
            laneContainer.add(
                    text(
                            register.getBottomLeftLabel(),
                            horizontalOffset + leftLabelHorizontalOffset,
                            wholeFieldHeight - (laneHeight / 2) + fontSize / 2 + labelVerticalOffset
                    )
                            .attribute("text-anchor", "start")
            );
        }
    }

    private void drawRightLabels(
            BitFieldRenderer.Config config,
            Register register,
            LiteXmlBuilder laneContainer,
            int horizontalOffset,
            int wholeFieldHeight
    ) {
        int rightLabelHorizontalOffset = config.getRightLabelHorizontalOffset();
        int laneHeight = config.getLaneHeight();
        int fontSize = config.getFontSize();
        int labelVerticalOffset = config.getLabelVerticalOffset();

        if (register.getTopRightLabel() != null) {
            laneContainer.add(
                    text(register.getTopRightLabel(),
                            horizontalOffset + rightLabelHorizontalOffset,
                            laneHeight / 2 + fontSize / 2 + labelVerticalOffset
                    )
                            .attribute("text-anchor", "start")
            );
        }

        if (register.getCenterRightLabel() != null) {
            laneContainer.add(
                    text(register.getCenterRightLabel(),
                            horizontalOffset + rightLabelHorizontalOffset,
                            wholeFieldHeight / 2 + fontSize / 2 + labelVerticalOffset
                    )
                            .attribute("text-anchor", "start")
            );
        }

        if (register.getBottomRightLabel() != null) {
            laneContainer.add(
                    text(register.getBottomRightLabel(),
                            horizontalOffset + rightLabelHorizontalOffset,
                            wholeFieldHeight - (laneHeight / 2) + fontSize / 2 + labelVerticalOffset
                    )
                            .attribute("text-anchor", "start")
            );
        }
    }

    private void drawFill(BitFieldRenderer.Config config, Register.BitArray bitArray, LiteXmlBuilder boxGroup, int width, int laneHeight, boolean broken) {
        if (!bitArray.isEnclosed())
            return;

        LiteXmlBuilder container = boxGroup;

        if (bitArray.getHref() != null && bitArray.getHref().isBlank()) {
            container = new LiteXmlBuilder("a")
                    .attribute("href", bitArray.getHref());
            boxGroup.add(container);
        }

        if (!broken) {
            //regular rect fill
            container.add(rect(null, null, width, laneHeight)
                    .attribute("style", "fill-opacity:{0};{1}", config.getFillOpacity(), getFillAttribute(config, bitArray.getBitColor())));

        } else {
            int breakHeight = laneHeight / 2 - config.getFieldBreakGap() / 2;
            int xHandle = config.getFieldBreakBezierX();
            int yHandle = config.getFieldBreakBezierY();
            //upper break
            container.add(
                    new LiteXmlBuilder("path")
                            .attribute("d", "M 0 0 l 0 {0} q {1} {2}, {3} 0 t {3} 0 l 0 -{0} L 0 0",
                                    breakHeight,
                                    xHandle, yHandle, //bezier stuff
                                    width / 2,
                                    width
                            )
                            .attribute("style", "fill-opacity:{0};{1}", 0.1, getFillAttribute(config, bitArray.getBitColor()))
            );

            //lower break
            container.add(
                    new LiteXmlBuilder("path")
                            .attribute("d", "M {0} {1} l 0 -{2} q -{3} -{4}, -{5} 0 t -{5} 0 l 0 {2} L {0} {1}",
                                    width, laneHeight,
                                    breakHeight,
                                    xHandle, yHandle, //bezier stuff
                                    width / 2
                            )
                            .attribute("style", "fill-opacity:{0};{1}", 0.1, getFillAttribute(config, bitArray.getBitColor()))
            );
        }

    }

    private void drawTicks(BitFieldRenderer.Config config, Register.BitArray bitArray, LiteXmlBuilder g, int laneHeight) {
        int bitWidth = config.getBitWidth();
        int tickHeight = config.getVerticalTickSize();

        if (bitArray.botherWithTicks()) {
            for (int i = 1; i < bitArray.getNumberOfBits(); i++) {

                int x = bitWidth * i;
                g.add(line(x, x, null, tickHeight)); //upper
                g.add(line(x, x, laneHeight, laneHeight - tickHeight)); //lower
            }
        }
    }

    private String getFillAttribute(BitFieldRenderer.Config config, int n) {
        String color = config.getFieldColor(n);

        return (color == null) ? ""
                : "fill:" + color;
    }
}
