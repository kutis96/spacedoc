package in.spcct.spacedoc.ffc;

import java.io.IOException;

public interface Graphviz {
    /**
     * Render using default options.
     *
     * @param source Graphviz source string
     * @return SVG-format output string
     * @throws IOException on generic IO error
     * @throws InterruptedException on external program execution interruption
     */
    String renderToSVG(String source) throws IOException, InterruptedException;

    default String renderToSVG(String source, Graphviz.Options options) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class Options {
        //TODO add some rendering options
    }

}
