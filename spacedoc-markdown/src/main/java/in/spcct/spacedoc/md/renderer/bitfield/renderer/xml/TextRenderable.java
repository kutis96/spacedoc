package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

import lombok.Data;

/**
 * Simplest renderable. Renders the given text into its output.
 */
@Data
public class TextRenderable implements Renderable {

    private String content;

    public TextRenderable(String content) {
        this.content = content;
    }

    @Override
    public String render() {
        return content;
    }
}
