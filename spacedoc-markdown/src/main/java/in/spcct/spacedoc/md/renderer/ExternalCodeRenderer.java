package in.spcct.spacedoc.md.renderer;

public interface ExternalCodeRenderer {

    String languageName();

    String renderSvg(String source) throws RenderingException;

}
