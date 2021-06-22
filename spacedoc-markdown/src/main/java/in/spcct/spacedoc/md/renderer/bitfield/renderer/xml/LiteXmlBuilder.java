package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

import java.text.MessageFormat;
import java.util.Locale;

public class LiteXmlBuilder {

    private final String elementName;
    private final StringBuilder attributes = new StringBuilder();
    private final StringBuilder body = new StringBuilder();

    public LiteXmlBuilder(String elementName) {
        this.elementName = elementName;
    }

    /**
     * Attempts to add an attribute with this value. If the value is null, no attribute is added.
     *
     * @param name name of the attribute
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

    public LiteXmlBuilder add(LiteXmlBuilder element) {
        if(element != null)
            body.append(element.render());
        return this;
    }

    public LiteXmlBuilder body(String what) {
        if(what != null)
            body.append(what);
        return this;
    }

    public LiteXmlBuilder attribute(String name, String pattern, Object... value) {
        MessageFormat messageFormat = new MessageFormat(pattern, Locale.ENGLISH);
        return attribute(name, messageFormat.format(value));
    }

    public String render() {
        //attributes starts with ' ' always
        String content = body.toString();

        if (content.isBlank()) {
            return '<' + elementName + attributes + "/>";
        } else {
            return '<' + elementName + attributes + '>' +
                    body +
                    "</" + elementName + '>';
        }
    }

}
