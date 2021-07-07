package in.spcct.spacedoc.md.renderer;

import in.spcct.spacedoc.common.exception.ParserException;
import in.spcct.spacedoc.common.exception.RenderingException;

public interface ExternalCodeRenderer {

    /**
     * Short name of the language this code renderer can render.
     * <p>
     * Should be a simple string, following the "[0-9a-z_\-]+" regular expression.
     * <p>
     * Identifies this external code renderer for lookup.
     *
     * @return Unique name for this external code renderer. Typically the same as the name of the language this renders.
     */
    String languageName();

    /**
     * Renders the given source code into a SVG-formatted string.
     *
     * @param source source code of the rendered language
     * @return SVG in a String.x
     * @throws RenderingException when an error during rendering occurs
     * @throws ParserException    when the source code is malformed or otherwise unparseable
     */
    String renderSvg(String source) throws RenderingException, ParserException;

}
