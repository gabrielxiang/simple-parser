package org.yang.tinysonar.ast;

/**
 * Created by yangxiang on 17/3/21.
 */
public class Bind extends Exp {

    public Bind() {
        this.nodeType = NodeType.BIND;
    }

    public Bind(Exp exp) {
        this.index = exp.index;
        this.name = exp.name;
        this.parent = exp.parent;
        if (parent != null) {
            parent.children.set(index, this);
        }
        this.nodeType = NodeType.BIND;
    }

}
