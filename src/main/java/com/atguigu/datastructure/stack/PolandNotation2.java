package com.atguigu.datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/15 8:14
 */
public class PolandNotation2 {
    private final static Pattern numPattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        String expression = "10+((20+3)*4)-500";//注意表达式
        // 1.字符串转化为中缀表达式字符集合
        List<String> infixExpressionList = str2InfixExpressionList2(expression);
        System.out.println("中缀表达式对应的List=" + infixExpressionList);

        // 2.中缀表达式字符串集合 转化为后缀表达式字符串集合
        List<String> suffixExpressionList = infixConvert2SuffixExpression(infixExpressionList);
        System.out.println("后缀表达式对应的List=" + suffixExpressionList);

        // 3.通过后缀不表达式开始计算
        int result = calculateValueBySufffixExpression(suffixExpressionList);
        System.out.println("计算结果: " + result);
    }

    private static int calculateValueBySufffixExpression(List<String> suffixExpressionList) {
        // 创建给栈, 只需要一个栈即可
        Stack<String> stack = new Stack<>();
        // 遍历 ls
        for (String item : suffixExpressionList) {
            // 这里使用正则表达式来取出数
            if (item.matches("\\d+")) { // 匹配的是多位数
                // 入栈
                stack.push(item);
            } else {
                // pop出两个数，并运算， 再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                switch (item) {
                    case "+":
                        res = num1 + num2;
                        break;
                    case "-":
                        res = num1 - num2;
                        break;
                    case "*":
                        res = num1 * num2;
                        break;
                    case "/":
                        res = num1 / num2;
                        break;
                    default:
                        throw new RuntimeException("运算符有误");
                }
                //把res 入栈
                stack.push("" + res);
            }

        }
        //最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());
    }

    private static List<String> infixConvert2SuffixExpression(List<String> infixExpressionList) {
        // 10+((20+3)*4)-500
        // 1.初始化两个栈, 运算符栈 s1和存储中间结果的栈 s2
        // 2.从左到右扫描该中缀表达式
        // 3.遇到数字,直接入s2 栈
        // 4.遇到( ,直接入s1 栈
        // 5.遇到) ,则依次弹出s1 栈的运算符,并添加到 s2 中,直到遇到( 之后结束, 把( )都去掉.
        // 6.遇到运算符,比较其与s1栈顶运算符的优先级
        // 6.1如果s1 为空,或者 当前扫描运算符优先级 大于栈顶运算符优先级,则直接入栈s1
        // 6.2否则,则弹出s1栈顶的运算符 c ,并添加到 s2中,再次转到第6步执行,直到不满足条件,将扫描的运算符添加到s1中.
        // 7.把s1中的剩余的运算符弹出并添加到s2中
        // 8.s2栈的逆序输出就是结果.(如果s2是集合,则就是正序输出结果)
        Stack<String> s1 = new Stack<>();
        List<String> s2 = new ArrayList<>();
        //int index = 0;
        for (String str : infixExpressionList) {
            if (numPattern.matcher(str).find()) {
                s2.add(str);
            } else if ("(".equals(str)) {
                s1.push(str);
            } else if (")".equals(str)) {
                while (!"(".equals(s1.peek())) {
                    s2.add(s1.pop());
                }
                // 去掉左括号,并且右括号也不添加
                s1.pop();
            } else {
                while (s1.size() != 0 && Operation.getValue(str) <= Operation.getValue(s1.peek())) {
                    s2.add(s1.pop());
                }
                s1.push(str);
            }
        }
        while (!s1.isEmpty()) {
            s2.add(s1.pop());
        }
        return s2;
    }

    private static List<String> str2InfixExpressionList(String expression) {
        // String expression = "1+((2+3)*4)-5"
        // String expression = "10+((20+3)*4)-500"; 如果是多位数字这样就会有问题.
        List<String> infixExpressionList = new ArrayList<>();
        // for (int i = 0; i < expression.length(); i++) {
        //     String element =  String.valueOf(expression.charAt(i));
        //     infixExpressionList.add(element);
        // }
        // 定义一个索引,去扫描该字符串
        int index = 0;
        StringBuilder keepNum = new StringBuilder();
        while (index < expression.length()) {
            // 1. 如果是非数字,则直接加入集合中
            // 2.如果是数字,则需要扫描下一个字符, 如果index == length-1 或者下个字符不是数字,才将拼串结果加入到集合中,否则一直拼串
            //  0 -> 48 9 -> 57
            char c = expression.charAt(index);
            if (c < 48 || c > 57) {
                infixExpressionList.add(String.valueOf(c));
            } else {
                keepNum.append(c);
                if (index == expression.length() - 1) {
                    infixExpressionList.add(keepNum.toString());
                    keepNum = new StringBuilder();
                } else {
                    char nextChar = expression.charAt(index + 1);
                    if (nextChar < 48 || nextChar > 57) {
                        infixExpressionList.add(keepNum.toString());
                        keepNum = new StringBuilder();
                    }
                }

            }
            index++;
        }
        return infixExpressionList;
    }

    private static List<String> str2InfixExpressionList2(String expression) {
        List<String> infixExpressionList = new ArrayList<>();
        int index = 0;
        StringBuilder keepNum = new StringBuilder();
        while (index < expression.length()) {
            char c = expression.charAt(index);
            if (c < 48 || c > 57) {
                infixExpressionList.add(String.valueOf(c));
                index++;
            } else {
                while (true) {
                    if (index == expression.length()) {
                        break;
                    }
                    if (expression.charAt(index) < 48 || expression.charAt(index) > 57) {
                        break;
                    }
                    keepNum.append(expression.charAt(index));
                    index++;
                }
                infixExpressionList.add(keepNum.toString());
                keepNum = new StringBuilder();
            }
        }
        return infixExpressionList;
    }

    private static List<String> str2InfixExpressionList3(String expression) {
        List<String> infixExpressionList = new ArrayList<>();
        int index = 0;
        StringBuilder keepNum = new StringBuilder();
        while (index < expression.length()) {
            char c = expression.charAt(index);
            if (c < 48 || c > 57) {
                infixExpressionList.add(String.valueOf(c));
                index++;
            } else {
                while (index < expression.length() && expression.charAt(index) >= 48 && expression.charAt(index) <= 57) {
                    keepNum.append(expression.charAt(index));
                    index++;
                }
                infixExpressionList.add(keepNum.toString());
                keepNum = new StringBuilder();
            }
        }
        return infixExpressionList;
    }

    private static class Operation {
        private static final int ADD = 1;
        private static final int SUB = 1;
        private static final int MUL = 2;
        private static final int DIV = 2;

        //写一个方法，返回对应的优先级数字
        public static int getValue(String operation) {
            int result = 0;
            switch (operation) {
                case "+":
                    result = ADD;
                    break;
                case "-":
                    result = SUB;
                    break;
                case "*":
                    result = MUL;
                    break;
                case "/":
                    result = DIV;
                    break;
                default:
                    System.out.println("不存在该运算符" + operation);
                    break;
            }
            return result;
        }
    }
}
