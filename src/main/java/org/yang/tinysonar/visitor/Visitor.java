package org.yang.tinysonar.visitor;

import org.yang.tinysonar.ast.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangxiang on 2018/1/11.
 */
public class Visitor {

    public static Map<String, Object> env0() {
        return new HashMap<>();
    }

    private static Map<String, Object> extEnv(String x, Object v, Map<String, Object> env) {
        Map<String, Object> newEnv = new HashMap<>();
        for (Map.Entry<String, Object> ent: env.entrySet()) {
            newEnv.put(ent.getKey(), ent.getValue());
        }
        newEnv.put(x, v);
        return newEnv;
    }

    private static class Closure {

        private FunctionDef f;

        private Map<String, Object> env;

        Closure(FunctionDef f, Map<String, Object> env) {
            this.f = f;
            this.env = env;
        }

    }

    public static Object interp(Node exp, Map<String, Object> env) {
        switch (exp.nodeType) {
            case VARIABLE:
                Object v = env.get(exp.name);
                if (v == null) {
                    throw new RuntimeException("Undefined variable");
                } else {
                    return v;
                }
            case NUMBER:
                return Double.valueOf(exp.name);
            case FUNCTIONDEF:
                return new Closure((FunctionDef) exp, env);
            case BIND:
                String x = exp.children.get(1).children.get(0).children.get(0).name;
                Exp le1 = (Exp) exp.children.get(1).children.get(0).children.get(1);
                Exp le2 = (Exp) exp.children.get(2);
                Object lv1 = interp(le1, env);
                return interp(le2, extEnv(x, lv1, env));
            case CALL:
                Exp ce1 = (Exp) exp.children.get(0);
                Exp ce2 = (Exp) exp.children.get(1);
                Object v1 = interp(ce1, env);
                Object v2 = interp(ce2, env);
                if (v1 instanceof Closure) {
                    Closure closure = (Closure) v1;
                    FunctionDef f = closure.f;
                    Map<String, Object> saveEnv = closure.env;
                    return interp(f.children.get(2), extEnv(f.children.get(1).children.get(0).name, v2, saveEnv));
                } else {
                    throw new RuntimeException("Function is not defined");
                }
            case OP:
                Exp oe1 = (Exp) exp.parent.children.get(1);
                Exp oe2 = (Exp) exp.parent.children.get(2);
                Object ov1 = interp(oe1, env);
                Object ov2 = interp(oe2, env);
                switch (exp.name) {
                    case "+":
                        return (Double) ov1 + (Double) ov2;
                    case "-":
                        return (Double) ov1 - (Double) ov2;
                    case "*":
                        return (Double) ov1 * (Double) ov2;
                    case "/":
                        return (Double) ov1 / (Double) ov2;
                }
            case EXP:
                return interp(exp.children.get(0), env);
            default:
                throw new RuntimeException("Unexpected node");
        }
    }

}