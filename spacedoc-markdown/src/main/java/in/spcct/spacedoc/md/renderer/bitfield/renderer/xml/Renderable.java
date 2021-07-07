package in.spcct.spacedoc.md.renderer.bitfield.renderer.xml;

/**
 * A common interface for XML {@link LiteXmlBuilder} renderables.
 */
public interface Renderable {

    /**
     * Render this item to String and return. Fancy toString.
     *
     * @return string representation of this object.
     */
    String render();
}
