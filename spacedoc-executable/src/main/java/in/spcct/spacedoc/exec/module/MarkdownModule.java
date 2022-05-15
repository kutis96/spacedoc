package in.spcct.spacedoc.exec.module;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.common.module.Module;
import in.spcct.spacedoc.common.util.StringUtils;
import in.spcct.spacedoc.config.ConfigContext;
import lombok.extern.java.Log;
import org.apache.commons.cli.*;
import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Module for rendering markdown formatted SpaceDoc documents.
 */
@Log
public class MarkdownModule implements Module {

    private static final ConfigContext configContext = ConfigContext.getInstance();

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
                .addOption("h", "help", false, "Get help")
                .addOption("P", "param", true, "Add more config parameters! Usage: -O key=value")
                .addOption("PL", "param-use", false, "List parameter use stats at the end of the run")
                .addOption("C", "cfg", true, "Loads parameters from a properties file");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(cliOptions, args);

        //TODO: Clean this all up someday.
        if (commandLine.hasOption("C")) {
            //Load configuration file
            String configPath = commandLine.getOptionValue("C");
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(configPath));
            } catch (IOException e) {
                throw new RuntimeException("Failure reading the configuration file at '" + configPath + "'", e);
            }
            properties.forEach(
                    (key, value) -> configContext.put(
                            ConfigContext.EntrySource.CONFIG_FILE,
                            (String) key, (String) value
                    ));
        }

        if (commandLine.hasOption("P")) {
            //Load command-line options
            Arrays.stream(commandLine.getOptionValues("P"))
                    .forEach(string -> {
                        if (!string.contains("="))
                            throw new IllegalArgumentException("Missing '=' in " + string + ". Parameters should be specified as -P \"key=value\"");

                        String[] splits = string.split("=");
                        if (splits.length != 2)
                            throw new IllegalArgumentException("Weirdness found around '" + string + "'. Parameters should be specified as -P \"key=value\"");

                        String key = splits[0].trim();
                        String val = splits[1].trim();
                        configContext.put(
                                ConfigContext.EntrySource.COMMAND_LINE,
                                key, val
                        );
                    });
        }

        if (commandLine.hasOption("h") || commandLine.getOptions().length == 0) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("SpaceDoc " + getLongName(), cliOptions);
            return;
        }

        File inputFile, outputFile;
        File prefixFile = null, suffixFile = null;

        if (commandLine.hasOption("i")) {
            //Input file
            String path = commandLine.getOptionValue("i");
            inputFile = new File(path);
        } else {
            System.err.println("No input file specified!");
            return;
        }

        if (commandLine.hasOption("o")) {
            //Output file
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

        if (commandLine.hasOption("PL")) {
            System.out.println("These entries were actually queried for existence or used:");
            configContext.getQueriedEntries().forEach(
                    e -> System.out.printf("\t%40s (%12s): %s%n", e.getKey(), e.getSource() == null ? "undefined" : e.getSource(), e.getValue() == null ? "" : e.getValue())
            );
            System.out.println("These values entries were actually used:");
            configContext.getUsedEntries().forEach(
                    e -> System.out.printf("\t%40s (%12s): %s%n", e.getKey(), e.getSource() == null ? "undefined" : e.getSource(), e.getValue() == null ? "" : e.getValue())
            );
            System.out.println("These entries were defined, but never used:");
            configContext.getUnusedEntries().forEach(
                    e -> System.out.printf("\t%40s (%12s): %s%n", e.getKey(), e.getSource() == null ? "undefined" : e.getSource(), e.getValue() == null ? "" : e.getValue())
            );
        }

        System.out.println(inputFile.getAbsolutePath() + " -> " + outputFile.getAbsolutePath());
    }

    private void doTheThing(File inputFile, File outputFile, File prefix, File suffix) {
        List<Extension> extensions = Registry.lookupAll(Extension.class, 0);

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

    /**
     * Appendes the content of the specified file into the given file writer.
     *
     * @param fileWriter writer to append into
     * @param content    content to be appended
     */
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
            log.warning("Failed to copy content from a file: " + content.getAbsolutePath() + "\n"
                    + StringUtils.toStackTraceString(e));
        }

    }
}
