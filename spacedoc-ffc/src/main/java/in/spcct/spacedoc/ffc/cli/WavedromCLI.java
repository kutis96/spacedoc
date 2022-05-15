package in.spcct.spacedoc.ffc.cli;

import in.spcct.spacedoc.common.util.FileUtils;
import in.spcct.spacedoc.common.util.StringUtils;
import in.spcct.spacedoc.ffc.Wavedrom;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

/**
 * Renders "wavedrom"-formatted source strings using the "wavedrom-cli".
 * <p>
 * See https://github.com/wavedrom/cli/
 */
@Log
public class WavedromCLI implements Wavedrom {

    private File processSVG(String input) throws IOException, InterruptedException {

        File inputFile = FileUtils.createTempFile(".json5");

        log.fine("Wavedrom input file path: " + inputFile.getAbsolutePath());

        try (FileWriter fileWriter = new FileWriter(inputFile)) {
            fileWriter.write(input);
        }

        File outputFile = FileUtils.createTempFile(".svg");

        Process process = processSVG(inputFile, outputFile);

        process.waitFor(60, TimeUnit.SECONDS);

        if (process.exitValue() > 0) {
            throw new IOException("Failed to Render:\n" + StringUtils.serialize(process.getErrorStream()));
        }

        inputFile.deleteOnExit();
        outputFile.deleteOnExit();

        return outputFile;
    }

    public Process processSVG(File input, File output) throws IOException {
        return NpxRunner.getInstance().execute(
                "wavedrom-cli",
                "-i", input.getAbsolutePath(),  //take this input
                "-s", output.getAbsolutePath()  //render to SVG
        );
    }

    @Override
    public String renderToSVG(String source) throws IOException, InterruptedException {
        return Files.readString(processSVG(source).toPath());
    }
}
