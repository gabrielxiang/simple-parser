package org.yang.tinysonar.demos;

import com.alibaba.fastjson.JSON;
import org.yang.tinysonar.Parser;
import org.yang.tinysonar.ast.Node;
import org.yang.tinysonar.visitor.Visitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Demo {

    public static void main(String[] args) throws IOException {
        System.out.println("R2 Version 0.0.1\n\n>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();
        Node ast = Parser.doParse(code);
        Map<String, Object> env = Visitor.env0();
        Object result = Visitor.interp(ast, env);
        System.out.println(JSON.toJSONString(result));
    }

}
