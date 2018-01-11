package org.yang.tinysonar.ast;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by yangxiang on 17/3/21.
 */
public class Op extends Node {

    private static final List<String> ops = Lists.newArrayList("+", "-", "*", "/");

    public static boolean isOp(String req) {
        for (String op : ops) {
            if (op.equals(req)) {
                return true;
            }
        }
        return false;
    }

    public Op(String name, Node parent) {
        super(name, parent);
        this.nodeType = NodeType.OP;
    }

}
