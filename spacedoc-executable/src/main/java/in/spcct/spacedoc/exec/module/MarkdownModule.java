package in.spcct.spacedoc.exec.module;

import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererExtension;
import in.spcct.spacedoc.module.Module;
import org.apache.commons.cli.*;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MarkdownModule implements Module {
    @Override
    public String getLongName() {
        return "MarkDown";
    }

    @Override
    public String getDescription() {
        return "Render awesome Spacedoc Markdown documents";
    }

    @Override
    public void run(String[] args) throws ParseException {
        Options cliOptions = new Options()
                .addOption("i", "input", true, "Markdown file. Must be specified.")
                .addOption("o", "output", true, "Output html. When none is specified, an output file will be processed at the same path as the input file, with .html extension attached instead of the original one.")
                .addOption("head", true, "Prefix this file to the output html. Optional.")
                .addOption("tail", true, "Suffix this file to the output html. Optional.")
                .addOption("h", "help", false, "Get help");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(cliOptions, args);

        if (commandLine.hasOption("h") || commandLine.getOptions().length == 0) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("SpaceDoc " + getLongName(), cliOptions);
            return;
        }

        File inputFile, outputFile;
        File prefixFile = null, suffixFile = null;

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

            newName += ".html";

            outputFile = new File(inputFile.getParent(), newName);
        }

        if (commandLine.hasOption("head")) {
            String path = commandLine.getOptionValue("head");
            prefixFile = new File(path);
        }

        if (commandLine.hasOption("tail")) {
            String path = commandLine.getOptionValue("tail");
            suffixFile = new File(path);
        }

        doTheThing(inputFile, outputFile, prefixFile, suffixFile);

        System.out.println(inputFile.getAbsolutePath() + " -> " + outputFile.getAbsolutePath());
    }

    private void doTheThing(File inputFile, File outputFile, File prefix, File suffix) {
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                ExternalCodeRendererExtension.create()
        );

        org.commonmark.parser.Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();

        Node document;
        try (FileReader fileReader = new FileReader(inputFile)) {
            document = parser.parseReader(fileReader);
        } catch (IOException e) {
            throw new RuntimeException("Oh fiddlesticks, what now?", e);
        }

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            copyInto(fileWriter, prefix);
            renderer.render(document, fileWriter);
            copyInto(fileWriter, suffix);
        } catch (IOException e) {
            throw new RuntimeException("Oh fiddlesticks, what now?", e);
        }
    }

    private static void copyInto(FileWriter fileWriter, File content) {
        if (content == null)
            return;

        try (FileReader fileReader = new FileReader(content)) {

            char[] buffer = new char[4096];
            int n = 0;
            while (-1 != (n = fileReader.read(buffer))) {
                fileWriter.write(buffer, 0, n);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
