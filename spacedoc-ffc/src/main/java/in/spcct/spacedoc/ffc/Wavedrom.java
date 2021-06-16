package in.spcct.spacedoc.ffc;

import java.io.IOException;

public interface Wavedrom {

    /**
     * Render using default options.
     *
     * @param source wavedrom source string
     * @return SVG-format output string
     * @throws IOException on generic IO error
     * @throws InterruptedException on external program execution interruption
     */
    String renderToSVG(String source) throws IOException, InterruptedException;

    default String renderToSVG(String source, Wavedrom.Options options) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class Options {
        //TODO
    }
}
