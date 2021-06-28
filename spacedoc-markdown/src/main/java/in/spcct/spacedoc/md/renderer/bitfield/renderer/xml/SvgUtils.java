package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

public class SvgUtils {
    public static LiteXmlBuilder rect(Integer x, Integer y, Integer width, Integer height) {
        return new LiteXmlBuilder("rect")
                .attribute("x", x)
                .attribute("y", y)
                .attribute("width", width)
                .attribute("height", height);
    }

    public static LiteXmlBuilder text(String text, Integer x, Integer y) {
        if (text == null)
            return null;
        return new LiteXmlBuilder("text")
                .attribute("x", x)
                .attribute("y", y)
                .addText(text);
    }

    public static LiteXmlBuilder line(Integer x1, Integer x2, Integer y1, Integer y2) {
        return new LiteXmlBuilder("line")
                .attribute("x1", x1)
                .attribute("y1", y1)
                .attribute("x2", x2)
                .attribute("y2", y2);
    }

}
