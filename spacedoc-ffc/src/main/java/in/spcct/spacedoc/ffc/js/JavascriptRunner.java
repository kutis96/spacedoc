package in.spcct.spacedoc.ffc.js;

import in.spcct.spacedoc.config.PolyglotConfig;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public abstract class JavascriptRunner {

    protected final Context context;

    public JavascriptRunner() {

        File requireDir = new File(PolyglotConfig.getInstance().getRequireCwd());

        try {
            Files.createDirectories(requireDir.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> options = new HashMap<>();
        options.put("js.commonjs-require", "true");
        options.put("js.commonjs-require-cwd", requireDir.getAbsolutePath());

        context = Context.newBuilder("js")
                .allowExperimentalOptions(true)
                .allowIO(true)
                .options(options)
                .build();

        init();

    }

    protected void init() {
        //Override me
    }

    protected Value getJsBindings() {
        return context.getBindings("js");
    }

    protected Value getMember(String identifier) {
        return context.getBindings("js").getMember(identifier);
    }

    protected void putMember(String identifier, Object value) {
        context.getBindings("js").putMember(identifier, value);
    }

    protected void freeMember(String identifier) {
        putMember(identifier, null);
    }

    protected Value eval(String jsCode) {
        return context.eval("js", jsCode);
    }

    protected Value eval(String... jsLines) {
        StringBuilder jsCode = new StringBuilder();
        for (String line : jsLines) {
            jsCode.append(line).append('\n');
        }
        return context.eval("js", jsCode.toString());
    }

}
