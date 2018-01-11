package org.yang.tinysonar.ast;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by yangxiang on 17/3/31.
 */
public class Node {

    public String name;

    public Node parent = null;

    public List<Node> children = null;

    public NodeType nodeType = null;

    public int index = -1;

    public Node() {
    }

    public Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            if (parent.children == null) {
                parent.children = Lists.newArrayList(this);
                this.index = 0;
            } else {
                parent.children.add(this);
                this.index = parent.children.size();
            }
        }
    }

    public void addChildren(@NotNull Node... nodes) {
        for (Node node : nodes) {
            if (node != null) {
                node.parent = this;
                if (children == null) {
                    children = Lists.newArrayList(node);
                    node.index = 0;
                } else {
                    children.add(node);
                    node.index = children.size() - 1;
                }
            }
        }
    }

}
