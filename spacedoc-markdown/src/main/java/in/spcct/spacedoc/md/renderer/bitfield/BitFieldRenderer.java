package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.FieldType;
import in.spcct.spacedoc.md.renderer.bitfield.fieldtype.Register;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.FieldTypeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.LiteXmlBuilder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static in.spcct.spacedoc.md.renderer.bitfield.renderer.xml.SvgUtils.rect;
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
                .attribute("width", config.width)
                .attribute("height", config.height)
                .attribute("viewBox", "0 0 {0} {1}", config.width, config.height)
                .attribute("class", config.mainElementClass)
        ;

        svgRoot.add(
                rect(null, null, config.width, config.height).attribute("fill", "white")
        );
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
                .attribute("transform", "translate({0},{1})", config.fieldHorizontalOffset + config.bitWidth / 2, config.verticalOffset);

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

    /**
     * TODO: Overcomplicate
     */
    @Data
    public static class Config {
        public int leftLabelHorizontalOffset = -80;
        public int rightLabelHorizontalOffset = 5;
        public int fieldHorizontalOffset = 100;
        public int bitWidth = 20;
        public int laneHeight = 26;
        public int fontSize = 14;

        public int laneSeparation = 7;

        public int bitNumberOffset = -2;
        public int tickHeight = 3;

        public int labelVerticalOffset = -3;

        public int verticalOffset = 20;

        public int width = 900;
        public int height = 480;

        public String mainElementClass;
        public String fontFamily = "sans-serif";
    }


}
