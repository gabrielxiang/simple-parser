package org.yang.tinysonar;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.yang.tinysonar.ast.*;
import org.yang.tinysonar.ast.Number;

import java.util.List;
import java.util.Stack;

/**
 * Created by yangxiang on 17/3/17.
 */
public class Parser {

    public static Node doParse(@NotNull String code) {
        List<String> segments = spilitBrackets(code.toCharArray());
        Node ast = convert(segments);
        markCall(ast);
        return ast;
    }

    private static void markCall(Node ast) {
        List<Node> children = ast.children;
        if (CollectionUtils.isNotEmpty(children)) {
            if (children.size() == 2) {
                Node left = children.get(0);
                Node right = children.get(1);
                if (left instanceof Exp
                        && right instanceof Exp
                        && !(left instanceof Number)
                        && !(left instanceof Bind)) {
                    if (ast.parent == null) {
                        ast.nodeType = NodeType.CALL;
                    } else if (ast.parent.parent == null) {
                        ast.nodeType = NodeType.CALL;
                    } else {
                        Node parent = ast.parent;
                        Node grand = ast.parent.parent;
                        if (!NodeType.BIND.equals(grand.nodeType)) {
                            ast.nodeType = NodeType.CALL;
                        } else {
                            if (parent.index > 1) {
                                ast.nodeType = NodeType.CALL;
                            }
                        }
                    }
                }
            }
            for (Node child : children) {
                markCall(child);
            }
        }
    }

    public static void main(String... args) {
        Node ast = doParse("(((lambda (x) (lambda (y) (+ x y))) 1) 2)");
        System.out.println(JSON.toJSONString(ast));
    }

    private static List<String> spilitBrackets(char[] chars) {
        StringBuilder sbd = new StringBuilder();
        List<String> result = Lists.newLinkedList();

        for (char c : chars) {
            if (c != ' ' && c != '\0' && c != '\t' && c != '\n') {
                if (c == '(' || c == '[' || c == ')' || c == ']') {
                    refreshTmpString(sbd, result);
                    result.add(String.valueOf(new char[]{c}));
                } else {
                    sbd.append(c);
                }
            } else {
                refreshTmpString(sbd, result);
            }
        }
        return result;
    }

    private static Node convert(List<String> segments) {
        Stack<String> stack = new Stack<>();
        Node currentNode = null;
        for (String segment : segments) {
            if ("(".equals(segment) || "[".equals(segment)) {
                stack.push(segment);
                Node newNode = new Exp("exp", null);
                if (currentNode == null) {
                    currentNode = newNode;
                } else {
                    currentNode.addChildren(newNode);
                    currentNode = newNode;
                }
            } else if (!")".equals(segment) && !"]".equals(segment)) {
                stack.push(segment);
                if (Op.isOp(segment)) {
                    new Op(segment, currentNode);
                } else if (Number.isNumber(segment)) {
                    new Number(segment, currentNode);
                } else if ("lambda".equals(segment)) {
                    if (currentNode != null) {
                        currentNode = new FunctionDef((Exp) currentNode);
                        new Lambda(segment, currentNode);
                    } else {
                        throw new RuntimeException("Error syntax");
                    }
                } else if ("let".equals(segment)) {
                    if (currentNode != null) {
                        currentNode = new Bind((Exp) currentNode);
                        new Let(segment, currentNode);
                    } else {
                        throw new RuntimeException("Error syntax");
                    }
                } else {
                    new Variable(segment, currentNode);
                }
            } else {
                matchBracket(stack, segment);
                if (currentNode != null && currentNode.parent != null) {
                    currentNode = currentNode.parent;
                }
            }
        }
        return currentNode;
    }

    private static void matchBracket(Stack<String> stack, String bracketType) {
        if (")".equals(bracketType)) {
            if (!stack.pop().equals("(")) {
                matchBracket(stack, bracketType);
            }
        } else {
            if (!stack.pop().equals("[")) {
                matchBracket(stack, bracketType);
            }
        }
    }

    private static void refreshTmpString(StringBuilder sbd, List<String> result) {
        if (sbd.length() > 0) {
            result.add(sbd.toString());
            sbd.delete(0, sbd.length());
        }
    }

}
