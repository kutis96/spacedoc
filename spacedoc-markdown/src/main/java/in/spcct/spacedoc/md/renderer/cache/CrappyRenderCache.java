package in.spcct.spacedoc.md.renderer.cache;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.internal.RenderCacheConfig;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

// Mappings:
// source hash <-> cache file
public class CrappyRenderCache implements RenderCache {

    private final Set<Integer> cachedSourceHashes = new HashSet<>();

    private final RenderCacheConfig config = Registry.lookup(RenderCacheConfig.class);

    @SneakyThrows
    public CrappyRenderCache() {
        //
        Path cacheDir = Path.of(config.getCacheDirectory());
        Files.createDirectories(cacheDir);
        System.out.println("Cache path: " + cacheDir.toAbsolutePath());
    }

    @Override
    public String lookupOrGenerate(String sourceCode, Function<String, String> lazyRenderer) {
        Integer sourceHash = sourceCode.hashCode();
        String cfn = toCacheFileName(sourceHash);
        try {
            if (isCached(sourceHash)) {
                System.out.println("Cache hit for " + cfn);
                cachedSourceHashes.add(sourceHash);
            } else {
                System.out.println("Cache miss for " + cfn);
                store(sourceHash, lazyRenderer.apply(sourceCode));
            }
            return load(sourceHash);
        } catch (IOException e) {
            System.out.println("Cache fault for " + cfn);
            e.printStackTrace();
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
