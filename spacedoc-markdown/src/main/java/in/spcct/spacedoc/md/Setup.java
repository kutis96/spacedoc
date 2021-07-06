package in.spcct.spacedoc.md;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererStore;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.BitFieldRendererExtension;
import in.spcct.spacedoc.md.renderer.bitfield.parser.FieldTypeParser;
import in.spcct.spacedoc.md.renderer.bitfield.parser.RegisterParser;
import in.spcct.spacedoc.md.renderer.bitfield.parser.SeparatorParser;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.FieldTypeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.RegisterRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.renderer.SeparatorRenderer;
import in.spcct.spacedoc.md.renderer.externalcode.instructionset.InstructionSetCodeRenderer;
import in.spcct.spacedoc.md.renderer.externalcode.memorymap.MemoryMapCodeRenderer;
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
        SillyCDI.registerCaching(
                externalCodeRendererClass, 1, WavedromSvgRenderer::new
        );
        SillyCDI.registerCaching(
                externalCodeRendererClass, 1, GraphvizSvgRenderer::new
        );
        SillyCDI.registerCaching(
                externalCodeRendererClass, 1, BitFieldRendererExtension::new
        );
        SillyCDI.registerCaching(
                externalCodeRendererClass, 1, InstructionSetCodeRenderer::new
        );
        SillyCDI.registerCaching(
                externalCodeRendererClass, 1, MemoryMapCodeRenderer::new
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
        SillyCDI.registerCaching(
                fieldTypeParserClass, 1, SeparatorParser::new
        );
        SillyCDI.registerCaching(
                fieldTypeParserClass, 1, RegisterParser::new
        );
    }

    public static void registerBitFieldRenderers() {
        Class<FieldTypeRenderer> fieldTypeRendererClass = FieldTypeRenderer.class;
        SillyCDI.registerCaching(
                fieldTypeRendererClass, 1, SeparatorRenderer::new
        );
        SillyCDI.registerCaching(
                fieldTypeRendererClass, 1, RegisterRenderer::new
        );
    }

}
