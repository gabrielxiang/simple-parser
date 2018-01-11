package org.yang.tinysonar.ast;

/**
 * Created by yangxiang on 2018/1/10.
 */
public class Exp extends Node {

    public static boolean isExp(Node node) {
        return node instanceof Exp;
    }

    public Exp() {
        this.nodeType = NodeType.EXP;
    }

    public Exp(String name, Node parent) {
        super(name, parent);
        this.nodeType = NodeType.EXP;
    }

}
