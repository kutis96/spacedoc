package in.spcct.spacedoc.exec.module;

import in.spcct.spacedoc.common.exception.ParserException;
import in.spcct.spacedoc.common.exception.RenderingException;
import in.spcct.spacedoc.common.util.StringUtils;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererCore;
import in.spcct.spacedoc.module.Module;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A module for directly invoking {@link ExternalCodeRendererCore} to render custom DSL files into SVG,
 * without the need of using markdown via {@link MarkdownModule} as an intermediary format.
 */
public class LanguageRendererModule implements Module {

    private final ExternalCodeRendererCore rendererCore = new ExternalCodeRendererCore();

    @Override
    public String getLongName() {
        return "LangRender";
    }

    @Override
    public String getDescription() {
        return "Renders sources of various languages into SVG images";
    }

    @Override
    public void run(String[] args) throws Exception {
        Options cliOptions = new Options()
                .addOption("h", "help", false, "Get help")
                .addOption("list", false, "List available language renderers")
                .addOption("lang", "language", true, "Language to use to render things")
                .addOption("i", "input", true, "Input file")
                .addOption("o", "output", true, "Output file");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(cliOptions, args);

        if (commandLine.hasOption("h") || commandLine.getOptions().length == 0) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("SpaceDoc " + getLongName(), cliOptions);
            return;
        }

        if (commandLine.hasOption("list")) {
            List<String> languageNames = rendererCore.getLanguageNames()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            for (String languageName : languageNames) {
                System.out.println(languageName);
            }
            return;
        }

        String language;
        File inputFile, outputFile;

        if (commandLine.hasOption("lang")) {
            language = commandLine.getOptionValue("lang");
        } else {
            System.err.println("No language specified!");
            return;
        }

        if (commandLine.hasOption("i")) {
            String path = commandLine.getOptionValue("i");
            inputFile = new File(path);
        } else {
            System.err.println("No input file specified!");
            return;
        }

        if (commandLine.hasOption("o")) {
            String path = commandLine.getOptionValue("o");
            outputFile = new File(path);
        } else {
            String newName = inputFile.getName();

            int lastDot = newName.lastIndexOf('.');
            if (lastDot != -1) {
                //strip old extension
                newName = newName.substring(0, lastDot);
            }

            newName += ".svg";

            outputFile = new File(inputFile.getParent(), newName);
        }

        render(language, inputFile, outputFile);

    }

    private void render(String language, File inputFile, File outputFile) throws IOException {

        if (!rendererCore.canRender(language))
            System.err.println("No renderer found for '" + language + "'");

        String inputCode = Files.readString(inputFile.toPath());

        String result;
        try {
            result = rendererCore.render(language, inputCode);
        } catch (RenderingException | ParserException e) {
            result = StringUtils.toStackTraceString(e);
        }

        if (result != null)
            Files.writeString(outputFile.toPath(), result);

    }


}
