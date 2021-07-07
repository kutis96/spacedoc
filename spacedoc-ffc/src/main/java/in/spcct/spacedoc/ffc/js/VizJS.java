package in.spcct.spacedoc.ffc.js;

import in.spcct.spacedoc.ffc.Graphviz;

/**
 * Implements {@link Graphviz} by using the VizJS javascript library.
 * <p>
 * (https://github.com/mdaines/viz.js)
 */
public class VizJS extends PolyglotJsRunner implements Graphviz {

    @Override
    protected void init() {
        eval(
                "const Viz = require('viz.js')",
                "const { Module, render } = require('viz.js/full.render.js')",
                "let viz = new Viz({ Module, render });"
        );
    }

    @Override
    public String renderToSVG(String source) {

        putMember("source", source);
        putMember("error", null);
        putMember("svg", null);

        eval(
                "viz.renderString(source)\n" +
                        "  .then(result => {\n" +
                        "    svg = result;\n" +
                        "  })\n" +
                        "  .catch(err => {\n" +
                        "    // Create a new Viz instance (@see Caveats page for more info)\n" +
                        "    viz = new Viz({ Module, render });\n" +
                        "\n" +
                        "    // Possibly display the error\n" +
                        "    error = err;\n" +
                        "  });"
        );

        freeMember("source");

        if(!getMember("error").isNull()) {
            throw new RuntimeException("Failed to render Graphviz: " + getMember("error"));
        }

        return getMember("svg").asString();

    }

}
