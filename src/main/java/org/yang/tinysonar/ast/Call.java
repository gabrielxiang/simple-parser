package org.yang.tinysonar.ast;

import org.apache.commons.collections.CollectionUtils;

/**
 * Created by yangxiang on 17/3/21.
 */
public class Call extends Exp {

    public Call() {
        this.nodeType = NodeType.CALL;
    }

    public Call(Exp exp) {
        this.index = exp.index;
        this.name = exp.name;
        this.parent = exp.parent;
        this.children = exp.children;
        if (parent != null) {
            parent.children.set(index, this);
        }
        if (CollectionUtils.isNotEmpty(children)) {
            for (Node child : children) {
                child.parent = this;
            }
        }
        this.nodeType = NodeType.CALL;
    }

}
