package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.configloader.Property;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.FieldTypeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.LiteXmlBuilder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.SvgUtils.text;

public class BitFieldRenderer {

    private final Config config;
    private final LiteXmlBuilder svgRoot;

    public BitFieldRenderer(Config config) {
        if (config == null)
            config = new Config();

        this.config = config;

        this.svgRoot = new LiteXmlBuilder("svg")
                .attribute("xmlns", "http://www.w3.org/2000/svg")
                .attribute("class", config.mainElementClass);

    }

    private void determineViewBoxSize(Context context) {
        String width = (config.imageWidth < 0) ? "800"
                : String.valueOf(config.imageWidth);
        String height = (config.imageHeight < 0) ? String.valueOf(context.totalVerticalOffset + config.imagePaddingBottom)
                : String.valueOf(config.imageHeight);

        svgRoot.attribute("width", width)
                .attribute("height", height)
                .attribute("viewBox", "0 0 {0} {1}", width, height);
    }

    public String renderStuff(FieldType... lanes) {
        return renderStuff(Arrays.asList(lanes));
    }

    public String renderStuff(List<FieldType> fieldTypes) {
        LiteXmlBuilder contentRoot = new LiteXmlBuilder("g")
                .attribute("transform", "translate(0.5,0.5)")
                .attribute("text-anchor", "middle")
                .attribute("font-size", config.fontSize)
                .attribute("font-family", config.fontFamily)
                .attribute("font-weight", "normal");

        contentRoot.add(renderBitLabels(fieldTypes));

        Context context = new Context();

        context.totalVerticalOffset = config.verticalOffset;

        for (FieldType fieldType : fieldTypes) {
            contentRoot.add(renderRenderable(context, fieldType));
        }

        svgRoot.add(contentRoot);

        determineViewBoxSize(context);

        return svgRoot.render();
    }

    private LiteXmlBuilder renderRenderable(Context context, FieldType fieldType) {

        FieldTypeRenderer<?> renderer = lookupRenderer(fieldType);

        return renderer.render(config, context, fieldType);
    }

    private FieldTypeRenderer<?> lookupRenderer(FieldType fieldType) {
        Optional<FieldTypeRenderer> maybeRenderer = SillyCDI.lookupAll(FieldTypeRenderer.class, 0)
                .stream()
                .filter(r -> r.getRenderedClass().isAssignableFrom(fieldType.getClass()))   //instanceof
                .findAny();

        if (maybeRenderer.isEmpty())
            throw new UnsupportedOperationException("Renderer for type " + fieldType.getClass().getCanonicalName() + " has not been found.");

        return maybeRenderer.get();
    }

    private LiteXmlBuilder renderBitLabels(List<FieldType> fieldTypes) {
        return renderInferredBitLabels(
                fieldTypes.stream()
                        .filter(a -> a instanceof Register)
                        .map(a -> (Register) a)
                        .collect(Collectors.toList())
        );
    }

    //TODO: Improve, make configurable
    private LiteXmlBuilder renderInferredBitLabels(List<Register> lanes) {
        int maxLaneSum = 0;
        for (Register lane : lanes) {
            int laneSum = 0;
            for (Register.BitArray array : lane.getBitArrays()) {
                laneSum += array.getNumberOfBits();
            }
            maxLaneSum = Math.max(laneSum, maxLaneSum);
        }

        LiteXmlBuilder container = new LiteXmlBuilder("g")
                .attribute("transform", "translate({0},{1})", config.bitFieldHorizontalOffset + config.bitWidth / 2, config.verticalOffset);

        for (int i = 0; i < maxLaneSum; i++) {
            int j = maxLaneSum - i - 1;
            container.add(text("" + j, i * config.bitWidth, config.bitNumberOffset));
        }

        return container;
    }

    /**
     * Contains common context to be shared between renderers.
     */
    public static class Context {
        public int totalVerticalOffset;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Config {

        @Property(value = "font-size", required = false)
        private int fontSize = 14;

        @Property(value = "bit-width", required = false)
        private int bitWidth = 20;

        @Property(value = "bit-separator-height", required = false)
        private int tickHeight = 3;

        @Property(value = "lane-height", required = false)
        private int laneHeight = 26;

        @Property(value = "separator-height", required = false)
        private int separatorHeight = 7;

        @Property(value = "label-offset-left", required = false)
        private int leftLabelHorizontalOffset = -77;

        @Property(value = "label-offset-right", required = false)
        private int rightLabelHorizontalOffset = 5;

        @Property(value = "label-offset-vertical", required = false)
        private int labelVerticalOffset = -3;

        @Property(value = "label-bitnumber-offset", required = false)
        private int bitNumberOffset = -2;

        @Property(value = "image-offset-vertical", required = false)
        private int verticalOffset = 14;

        @Property(value = "image-width", required = false)
        private int imageWidth = -1;

        @Property(value = "image-height", required = false)
        private int imageHeight = -1;

        @Property(value = "image-padding-bottom", required = false)
        private int imagePaddingBottom = 3;

        @Property(value = "image-font-family", required = false)
        private String fontFamily = "sans-serif";

        
        @Property(value = "field-horizontal-offset", required = false)
        private int bitFieldHorizontalOffset = 80;

        @Property(value = "field-break-gap", required = false)
        private int fieldBreakGap = 10;
        @Property(value = "field-break-bezier-x", required = false)
        private int fieldBreakBezierX = 30;
        @Property(value = "field-break-bezier-y", required = false)
        private int fieldBreakBezierY = 30;

        private String mainElementClass;

        private float fillOpacity = 0.1F;
        private String[] fillColors = {
//                null,        //0
//                null,       //1
//                "red",      //2
//                "green",    //3
//                "blue",     //4
//                "cyan",     //5
//                "magenta",  //6
//                "yellow",   //7
//                "orange",   //8
//                "navy",     //9

                // Wavedrom-like color scheme
                null, //0
                "#fff", //1
                "hsl(0, 100%, 50%)", //2
                "hsl(80, 100%, 50%)", //3
                "hsl(170, 100%, 50%)", //4
                "hsl(45, 100%, 50%)", //5
                "hsl(126, 100%, 50%)", //6
                "hsl(215, 100%, 50%)", //7
                "hsl(60, 100%, 50%)", //8
                "hsl(180, 100%, 50%)", //9
        };

        public String getFieldColor(int n) {
            return fillColors[n % Math.min(fillColors.length, 8)];
        }
    }


}
