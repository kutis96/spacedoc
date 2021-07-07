package in.spcct.spacedoc.ffc.js;

import in.spcct.spacedoc.ffc.Wavedrom;

public class WavedromJS extends PolyglotJsRunner implements Wavedrom {

    @Override
    protected void init() {
        eval(
                "var json5 = require(\"json5\")",
                "var onml = require(\"onml\")",
                "var wavedrom = require(\"wavedrom\")",
                "var skin_def = require(\"wavedrom/skins/default.js\")",
                "var skins = Object.assign({}, skin_def)"
        );
    }

    @Override
    public String renderToSVG(String source) {

        //Parse JSON5 source to object
        putMember("body", source);
        eval("var source = json5.parse(body)");
        freeMember("body"); //free

        //Render to SVG
        eval("var res = onml.s(wavedrom.renderAny(0, source, skins));");
        freeMember("source"); //free

        String result = getMember("res").asString();

        freeMember("res"); //free

        return result;

    }

}
