package org.yang.tinysonar.ast;

/**
 * Created by yangxiang on 17/3/21.
 */
public class FunctionDef extends Exp {

    public FunctionDef() {
        this.nodeType = NodeType.FUNCTIONDEF;
    }

    public FunctionDef(Exp exp) {
        this.index = exp.index;
        this.name = exp.name;
        this.parent = exp.parent;
        if (parent != null) {
            parent.children.set(index, this);
        }
        this.nodeType = NodeType.FUNCTIONDEF;
    }

}
