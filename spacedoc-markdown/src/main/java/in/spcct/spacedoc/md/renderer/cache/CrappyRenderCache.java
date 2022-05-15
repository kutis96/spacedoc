package in.spcct.spacedoc.md.renderer.cache;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.internal.RenderCacheConfig;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;

import static in.spcct.spacedoc.common.util.StringUtils.toStackTraceString;

// Mappings:
// source hash <-> cache file
@Log
public class CrappyRenderCache implements RenderCache {

    private final Set<Integer> cachedSourceHashes = new HashSet<>();

    private final RenderCacheConfig config = Registry.lookup(RenderCacheConfig.class);

    public static final Level cacheDebugLoggingLevel = Level.FINE;

    @SneakyThrows
    public CrappyRenderCache() {
        //
        Path cacheDir = Path.of(config.getCacheDirectory());
        Files.createDirectories(cacheDir);
        log.log(cacheDebugLoggingLevel, "Cache path: " + cacheDir.toAbsolutePath());
    }

    @Override
    public String lookupOrGenerate(String sourceCode, Function<String, String> lazyRenderer) {
        Integer sourceHash = sourceCode.hashCode();

        String cfn = toCacheFileName(sourceHash);

        try {
            if (isCached(sourceHash)) {
                log.log(cacheDebugLoggingLevel, "Cache hit for " + cfn);
                cachedSourceHashes.add(sourceHash);
            } else {
                log.log(cacheDebugLoggingLevel, "Cache miss for " + cfn);
                store(sourceHash, lazyRenderer.apply(sourceCode));
            }
            return load(sourceHash);
        } catch (IOException e) {
            log.log(cacheDebugLoggingLevel, "Cache fault for " + cfn);
            log.warning("Exception using render cache\n" + toStackTraceString(e));
            return lazyRenderer.apply(sourceCode);
        }
    }

    private boolean isCached(Integer sourceHash) {
        return (cachedSourceHashes.contains(sourceHash)
                || Files.exists(Path.of(config.getCacheDirectory(), toCacheFileName(sourceHash)))
        );
    }

    private String load(Integer sourceHash) throws IOException {
        return Files.readString(
                toCacheFilePath(sourceHash)
        );
    }

    private void store(Integer sourceHash, String rendered) throws IOException {
        cachedSourceHashes.add(sourceHash);
        Files.writeString(
                toCacheFilePath(sourceHash),
                rendered
        );
    }

    private Path toCacheFilePath(Integer sourceHash) {
        return Path.of(
                config.getCacheDirectory(),
                toCacheFileName(sourceHash)
        );
    }

    private String toCacheFileName(Integer sourceHash) {
        return String.format("%08x.cache", sourceHash);
    }

}
