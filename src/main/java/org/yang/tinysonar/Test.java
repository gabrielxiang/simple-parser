package org.yang.tinysonar;

import com.alibaba.fastjson.JSON;
import org.yang.tinysonar.ast.Node;
import org.yang.tinysonar.visitor.Visitor;

import java.util.Map;

public class Test {

    public static void main(String... args) {
        Node ast = Parser.doParse("(let ([x 2]) (+ (let ([x 1]) x) x))");
        Map<String, Object> env = Visitor.env0();
        Object result = Visitor.interp(ast, env);
        System.out.println(JSON.toJSONString(result));
    }

}
