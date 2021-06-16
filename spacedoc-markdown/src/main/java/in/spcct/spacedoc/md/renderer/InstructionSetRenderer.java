package in.spcct.spacedoc.md.renderer;

public class InstructionSetRenderer implements ExternalCodeRenderer {


    @Override
    public String languageName() {
        return "instruction-set";
    }

    @Override
    public String renderSvg(String source) {
        return null;
    }
}
