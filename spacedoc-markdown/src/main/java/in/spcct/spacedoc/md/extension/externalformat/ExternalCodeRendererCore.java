package in.spcct.spacedoc.md.extension.externalformat;

import in.spcct.spacedoc.common.exception.ParserException;
import in.spcct.spacedoc.common.exception.RenderingException;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;

import java.util.Collection;

public class ExternalCodeRendererCore {

    /**
     * @param languageName
     * @param source
     * @return rendered SVG image
     * @throws RenderingException when a rendering error occurs
     * @throws ParserException    when a parsing error occurs
     */
    public String render(String languageName, String source) throws RenderingException, ParserException {
        if (languageName == null || languageName.isBlank())
            throw new RenderingException("No language specified!");

        ExternalCodeRenderer renderer = ExternalCodeRendererStore.getInstance()
                .lookup(languageName);

        if (renderer == null) {
            throw new RenderingException("No renderer found for " + languageName);
        }

        return renderer.renderSvg(source);

    }

    /**
     * Returns true if a language with the provided identifier can be rendered.
     *
     * @param language language identifier
     * @return true if a renderer was found, false otherwise
     */
    public boolean canRender(String language) {
        return ExternalCodeRendererStore.getInstance()
                .lookup(language) != null;
    }

    /**
     * Lists all known languages that can be rendered.
     *
     * @return list of known, renderable languages
     */
    public Collection<String> getLanguageNames() {
        return ExternalCodeRendererStore.getInstance()
                .listLanguages();
    }


}
