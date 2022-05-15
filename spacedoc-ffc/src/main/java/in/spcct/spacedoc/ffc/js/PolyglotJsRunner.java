package in.spcct.spacedoc.ffc.js;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.internal.PolyglotConfig;
import lombok.extern.java.Log;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static in.spcct.spacedoc.common.util.StringUtils.toStackTraceString;

/**
 * A simple wrapper for running JavaScript code via the GraalVM polyglot feature.
 */
@Log
public abstract class PolyglotJsRunner {

    protected final Context context;

    public PolyglotJsRunner() {

        PolyglotConfig polyglotConfig = Registry.lookup(PolyglotConfig.class);

        File requireDir = new File(polyglotConfig.getRequireCwd());

        try {
            Files.createDirectories(requireDir.toPath());
        } catch (IOException e) {
            log.warning("Exception creating require directories for JS runners\n" + toStackTraceString(e));
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
