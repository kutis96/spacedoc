package in.spcct.spacedoc.md.renderer;

import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;

import java.util.Collections;
import java.util.Set;

/**
 * A CommonMark {@link NodeRenderer}, rendering {@link FencedCodeBlock} nodes.
 */
public abstract class FencedCodeBlockRenderer implements NodeRenderer {

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.singleton(FencedCodeBlock.class);
    }
}
