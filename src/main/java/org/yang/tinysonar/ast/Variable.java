package org.yang.tinysonar.ast;

/**
 * Created by yangxiang on 2018/1/10.
 */
public class Variable extends Exp {

    public Variable(String name, Node parent) {
        super(name, parent);
        this.nodeType = NodeType.VARIABLE;
    }

}
