package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Simple, hacky XML builder.
 */
public class LiteXmlBuilder implements Renderable {

    private final String elementName;
    private final StringBuilder attributes = new StringBuilder();
    private final List<Renderable> children = new LinkedList<>();

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

    /**
     * Adds a {@link Renderable} to the list of currently included child elements.
     *
     * @param renderable child element to include
     * @return this builder
     */
    public LiteXmlBuilder add(Renderable renderable) {
        if (renderable != null)
            children.add(renderable);
        return this;
    }

    /**
     * Adds a body text, represented by an instance of {@link TextRenderable}.
     *
     * @param what body text to include
     * @return this builder
     */
    public LiteXmlBuilder addText(String what) {
        if (what != null)
            add(new TextRenderable(what));
        return this;
    }

    /**
     * Adds an attribute to this element. Uses {@link MessageFormat} for rendering attribute values.
     *
     * @param name    name of the attribute to add
     * @param pattern {@link MessageFormat} pattern
     * @param value   Optional list of values provided to the {@link MessageFormat} formatter.
     * @return this builder
     */
    public LiteXmlBuilder attribute(String name, String pattern, Object... value) {
        MessageFormat messageFormat = new MessageFormat(pattern, Locale.ENGLISH);
        return attribute(name, messageFormat.format(value));
    }

    /**
     * Renders the builder's contents into a string. Non-destructive.
     *
     * @return string representation of this builder.
     */
    public String render() {
        StringBuilder result = new StringBuilder();

        //attributes starts with ' ' always
        for (Renderable element : children) {
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
