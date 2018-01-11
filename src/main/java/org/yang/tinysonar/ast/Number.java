package org.yang.tinysonar.ast;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by yangxiang on 17/3/21.
 */
public class Number extends Exp {

    public static boolean isNumber(String req) {
        return NumberUtils.isNumber(req);
    }

    public Number(String name, Node parent) {
        super(name, parent);
        this.nodeType = NodeType.NUMBER;
    }

}
