package in.spcct.spacedoc.md;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererStore;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.InstructionSetRendererExtension;
import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRendererExtension;
import in.spcct.spacedoc.md.renderer.bitfield.parser.FieldTypeParser;
import in.spcct.spacedoc.md.renderer.bitfield.parser.RegisterParser;
import in.spcct.spacedoc.md.renderer.bitfield.parser.SeparatorParser;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.FieldTypeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.RegisterRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.SeparatorRenderer;
import in.spcct.spacedoc.md.renderer.impl.GraphvizSvgRenderer;
import in.spcct.spacedoc.md.renderer.impl.WavedromSvgRenderer;

public class Setup {

    public static void registerAll() {
        registerExternalCodeRenderers();
        registerBitFieldParsers();
        registerBitFieldRenderers();
    }

    public static void registerExternalCodeRenderers() {
        Class<ExternalCodeRenderer> externalCodeRendererClass = ExternalCodeRenderer.class;
        SillyCDI.register(
                externalCodeRendererClass, 1, WavedromSvgRenderer::new
        );
        SillyCDI.register(
                externalCodeRendererClass, 1, GraphvizSvgRenderer::new
        );
        SillyCDI.register(
                externalCodeRendererClass, 1, InstructionSetRendererExtension::new
        );
        SillyCDI.register(
                externalCodeRendererClass, 1, BitFieldRendererExtension::new
        );

        //TODO: Make better after CDI improvement.
        // With nicer CDI, one could actually look stuff up nicer instead of using a special store for everything.
        ExternalCodeRendererStore store = ExternalCodeRendererStore.getInstance();

        for(ExternalCodeRenderer codeRenderer : SillyCDI.lookupAll(externalCodeRendererClass, 0)) {
            store.register(codeRenderer.languageName(), codeRenderer);
        }
    }

    public static void registerBitFieldParsers() {
        Class<FieldTypeParser> fieldTypeParserClass = FieldTypeParser.class;
        SillyCDI.register(
                fieldTypeParserClass, 1, SeparatorParser::new
        );
        SillyCDI.register(
                fieldTypeParserClass, 1, RegisterParser::new
        );
    }

    public static void registerBitFieldRenderers() {
        Class<FieldTypeRenderer> fieldTypeRendererClass = FieldTypeRenderer.class;
        SillyCDI.register(
                fieldTypeRendererClass, 1, SeparatorRenderer::new
        );
        SillyCDI.register(
                fieldTypeRendererClass, 1, RegisterRenderer::new
        );
    }

}
