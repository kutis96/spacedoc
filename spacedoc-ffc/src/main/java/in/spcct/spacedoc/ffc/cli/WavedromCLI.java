package in.spcct.spacedoc.ffc.cli;

import in.spcct.spacedoc.config.GeneralConfig;
import in.spcct.spacedoc.ffc.Wavedrom;
import in.spcct.spacedoc.ffc.util.SerializationUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WavedromCLI implements Wavedrom {

    private final GeneralConfig envConfig = GeneralConfig.getInstance();

    private File processSVG(String input) throws IOException, InterruptedException {

        File inputFile = File.createTempFile(envConfig.getTempFilePrefix(), ".json5");

        String x = inputFile.getAbsolutePath();
        System.out.println(x);

        try (FileWriter fileWriter = new FileWriter(inputFile)) {
            fileWriter.write(input);
        }

        File outputFile = File.createTempFile(envConfig.getTempFilePrefix(), ".svg");

        Process process = processSVG(inputFile, outputFile);

        process.waitFor(60, TimeUnit.SECONDS);

        if (process.exitValue() > 0) {
            throw new IOException("Failed to Render:\n" + SerializationUtil.serialize(process.getErrorStream()));
        }

        inputFile.deleteOnExit();
        outputFile.deleteOnExit();

        return outputFile;
    }

    public Process processSVG(File input, File output) throws IOException {
        return NpxRunner.getInstance().execute(
                "bit-field",
                "-i", input.getAbsolutePath(),
                "-s", output.getAbsolutePath()
        );
    }

    @Override
    public String renderToSVG(String source) throws IOException, InterruptedException {
        List<String> lines = Files.readAllLines(
            processSVG(source).toPath()
        );

        return SerializationUtil.concatenate(lines);
    }
}
