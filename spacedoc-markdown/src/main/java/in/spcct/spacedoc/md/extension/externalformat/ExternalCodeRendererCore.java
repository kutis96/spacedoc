package in.spcct.spacedoc.md.extension.externalformat;

import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.RenderingException;

import java.util.Collection;

public class ExternalCodeRendererCore {

    /**
     * @param languageName
     * @param source
     * @return rendered SVG image
     * @throws RenderingException when an error occurs
     */
    public String render(String languageName, String source) throws RenderingException {

        ExternalCodeRenderer renderer = ExternalCodeRendererStore.getInstance()
                .lookup(languageName);

        if (renderer == null) {
            throw new RenderingException("No renderer found for " + languageName);
        }

        return renderer.renderSvg(source);

    }

    public Collection<String> getLanguageNames() {
        return ExternalCodeRendererStore.getInstance()
                .listLanguages();
    }


}
