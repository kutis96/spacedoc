package in.spcct.spacedoc.md;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererExtension;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererStore;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.bitfield.BitFieldExternalCodeRenderer;
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
import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;

public class Setup {

    public static void registerAll() {
        registerCommonMarkExtensions();
        registerExternalCodeRenderers();
        registerBitFieldParsers();
        registerBitFieldRenderers();
    }

    private static void registerCommonMarkExtensions() {
        Class<Extension> extensionClass = Extension.class;
        Registry.registerSingleton(
                extensionClass, 1, TablesExtension::create
        );
        Registry.registerSingleton(
                extensionClass, 1, StrikethroughExtension::create
        );
        Registry.registerSingleton(
                extensionClass, 1, ExternalCodeRendererExtension::create
        );
    }

    public static void registerExternalCodeRenderers() {
        Class<ExternalCodeRenderer> externalCodeRendererClass = ExternalCodeRenderer.class;
        Registry.registerSingleton(
                externalCodeRendererClass, 1, WavedromSvgRenderer::new
        );
        Registry.registerSingleton(
                externalCodeRendererClass, 1, GraphvizSvgRenderer::new
        );
        Registry.registerSingleton(
                externalCodeRendererClass, 1, BitFieldExternalCodeRenderer::new
        );
        Registry.registerSingleton(
                externalCodeRendererClass, 1, InstructionSetCodeRenderer::new
        );
        Registry.registerSingleton(
                externalCodeRendererClass, 1, MemoryMapCodeRenderer::new
        );

        //TODO: Make better after CDI improvement.
        // With nicer CDI, one could actually look stuff up nicer instead of using a special store for everything.
        ExternalCodeRendererStore store = ExternalCodeRendererStore.getInstance();

        for (ExternalCodeRenderer codeRenderer : Registry.lookupAll(externalCodeRendererClass, 0)) {
            store.register(codeRenderer.languageName(), codeRenderer);
        }
    }

    public static void registerBitFieldParsers() {
        Class<FieldTypeParser> fieldTypeParserClass = FieldTypeParser.class;
        Registry.registerSingleton(
                fieldTypeParserClass, 1, SeparatorParser::new
        );
        Registry.registerSingleton(
                fieldTypeParserClass, 1, RegisterParser::new
        );
    }

    public static void registerBitFieldRenderers() {
        Class<FieldTypeRenderer> fieldTypeRendererClass = FieldTypeRenderer.class;
        Registry.registerSingleton(
                fieldTypeRendererClass, 1, SeparatorRenderer::new
        );
        Registry.registerSingleton(
                fieldTypeRendererClass, 1, RegisterRenderer::new
        );
    }

}
