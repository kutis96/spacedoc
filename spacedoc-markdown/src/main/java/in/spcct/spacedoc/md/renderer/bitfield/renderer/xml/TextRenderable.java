package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

import lombok.Data;

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
