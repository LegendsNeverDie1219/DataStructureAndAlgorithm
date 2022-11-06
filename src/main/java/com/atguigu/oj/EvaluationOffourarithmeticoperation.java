package com.atguigu.oj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/21 15:45
 */
public class EvaluationOffourarithmeticoperation {

    private static final Pattern pattern = Pattern.compile("\\d+");

    /**
     * 计算表达式
     * 给定一个字符串形式的计算表达式,其中只包含数字,+,-, *,/四种运算符,
     * 请对该计算表达式求值,并返回计算结果,如果在计算的过程中遇到了除0,则返回字符串error.
     * <p>
     * 1+2*3-100/2;
     * <p>
     * -43
     * <p>
     * 3/0
     * <p>
     * error
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        scanner.close();
        String result = calculate(expression);
        System.out.println(result);
    }

    private static String calculate(String expression) {
        if (expression.contains("\0")) {
            return "error";
        }
        // 1+2*3-100/2;
        // 1.字符串转化为 数字,运算符数组
        List<String> characterList = infixStr2CharacterList(expression);
        // 2.中缀表达式,转化为后缀表达式
        List<String> suffixExpression = convert2SuffixExpression(characterList);
        // 3.计算后缀表达式.
        return calculateExpression(suffixExpression);
    }

    private static String calculateExpression(List<String> suffixExpression) {
        Stack<String> numStack = new Stack<>();

        for (String str : suffixExpression) {
            if (str.matches("\\d+\\.{0,1}\\d*")) {
                numStack.push(str);
            } else {
                // 是运算符,则弹出数字栈的两个数字,进行计算.
                double num2 = Double.parseDouble(numStack.pop());
                double num1 = Double.parseDouble(numStack.pop());
                double result = 0.0d;
                switch (str) {
                    case "+": {
                        result = num1 + num2;
                        break;
                    }
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        result = num1 / num2;
                        break;
                    default: {
                        System.out.println("不支持 的操作符,呵呵呵");
                        break;
                    }
                }
                numStack.push(String.valueOf(result));
            }
        }
        return numStack.pop();
    }

    private static List<String> convert2SuffixExpression(List<String> characterList) {
        // 1.如果为( 则直接入运算符栈 operateStack
        // 2.如果为) ,则弹出运算符栈的 运算符,并加入到数字栈中, 直到遇到一个(, 最后将( 也弹出.同时)也不要加入.
        // 3.如果为数字,则存入到数字栈
        // 4.如果是运算符,则判断栈顶的运算符 和当前扫描的运算符 的优先级
        // 4.1如果运算符栈为空或者 当前扫描的运算符* 高于 栈顶的运算符的优先级+, 则直接入栈
        // 4.2 否则弹出运算符栈顶的运算符, 并加入到数字栈/集合中.
        Stack<String> operateStack = new Stack<>();
        List<String> numList = new ArrayList<>();
        for (String str : characterList) {
            if ("(".equals(str)) {
                operateStack.push(str);
            } else if (")".equals(str)) {
                while (!operateStack.isEmpty() && !"(".equals(operateStack.peek())) {
                    String operate = operateStack.pop();
                    numList.add(operate);
                }
                operateStack.pop();
            } else if (pattern.matcher(str).matches()) {
                numList.add(str);
            } else {
                if (operateStack.isEmpty() || Operate.getPriority(str) > Operate.getPriority(operateStack.peek())) {
                    operateStack.push(str);
                } else {
                    String highOperate = operateStack.pop();
                    numList.add(highOperate);
                    operateStack.push(str);
                }
            }
        }

        while (!operateStack.isEmpty()) {
            numList.add(operateStack.pop());
        }
        return numList;
    }

    private static List<String> infixStr2CharacterList(String expression) {
        // 1.字符串转化为 数字,运算符数组
        List<String> resultList = new ArrayList<>();
        char[] charArray = expression.toCharArray();
        StringBuilder keepNum = new StringBuilder();

        int i = 0;
        while (i < charArray.length) {
            if (Character.isDigit(charArray[i])) {
                while (i < charArray.length && Character.isDigit(charArray[i])) {
                    keepNum.append(charArray[i]);
                    i++;
                }
                // charArray[i]不是数字,则将keepNum中的数字,存入到resultList,keepNum重置为空.
                resultList.add(keepNum.toString());
                keepNum = new StringBuilder();
            } else {
                // 运算符存入resultList中.
                resultList.add(String.valueOf(charArray[i]));
                i++;
            }
        }
        return resultList;
    }

    private static class Operate {

        public static int getPriority(String str) {
            char ch = str.charAt(0);
            switch (ch) {
                case '+':
                    return 1;
                case '-':
                    return 1;
                case '*':
                    return 2;
                case '/':
                    return 2;
                default: {
                    System.out.println("不支持 的操作符,无法获取优先级");
                    return 0;
                }
            }
        }
    }
}
