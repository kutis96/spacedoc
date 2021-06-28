package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class LiteXmlBuilder implements Renderable {

    private final String elementName;
    private final StringBuilder attributes = new StringBuilder();
    private final List<Renderable> bodyElements = new LinkedList<>();

    public LiteXmlBuilder(String elementName) {
        this.elementName = elementName;
    }

    /**
     * Attempts to add an attribute with this value. If the value is null, no attribute is added.
     *
     * @param name  name of the attribute
     * @param value value of the attribute
     * @return this builder
     */
    public LiteXmlBuilder attribute(String name, Object value) {
        if (value != null) {
            attributes.append(' ')
                    .append(name).append('=').append('\"').append(value).append('\"').append(' ');
        }

        return this;
    }

    public LiteXmlBuilder add(Renderable element) {
        if (element != null)
            bodyElements.add(element);
        return this;
    }

    public LiteXmlBuilder addText(String what) {
        if (what != null)
            add(new TextRenderable(what));
        return this;
    }

    public LiteXmlBuilder attribute(String name, String pattern, Object... value) {
        MessageFormat messageFormat = new MessageFormat(pattern, Locale.ENGLISH);
        return attribute(name, messageFormat.format(value));
    }

    public String render() {
        StringBuilder result = new StringBuilder();

        //attributes starts with ' ' always
        for (Renderable element : bodyElements) {
            result.append(element.render());
        }

        String content = result.toString();

        if (content.isBlank()) {
            return '<' + elementName + attributes + "/>";
        } else {
            return '<' + elementName + attributes + '>' +
                    result +
                    "</" + elementName + '>';
        }
    }

}
