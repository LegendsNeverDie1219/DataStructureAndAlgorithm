package com.atguigu.leetcode.ChapterOne.queuestack;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/19 20:47
 */
public class QueueStackTest {
    /**
     * 给定⼀个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s，判断字符串是否有效。
     * 有效字符串需满⾜：
     * 1、 左括号必须⽤相同类型的右括号闭合。
     * 2、 左括号必须以正确的顺序闭合。
     * 示例 1：
     * 输⼊：s = "([)]"
     * 输出：false
     * 思路:利用栈先进后出的特点, 定义一个存储左括号的栈,
     * 遇到左括号就去入栈
     * 遇到右括号,就去找离他最近的左括号,看是否匹配
     */
    @Test
    public void testIsValid() {
        String str1 = "([)]";
        String str2 = "(){}[]";
        boolean result1 = isValid(str1);
        System.out.println("result1: " + result1);
        boolean result2 = isValid(str2);

        System.out.println("result2: " + result2);

        String str3 = ")(){}[]";
    }

    private boolean isValid(String str) {
        Stack<Character> leftParenthesesStack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            if (character == '(' || character == '[' || character == '{') {
                leftParenthesesStack.push(character);
            } else {
                if (!leftParenthesesStack.isEmpty() && getMatchedCharacter(character).equals(leftParenthesesStack.peek())) {
                    leftParenthesesStack.pop();
                } else {
                    // 和最近的左括号不匹配
                    return false;
                }
            }
        }
        // 左括号栈为空 说明所有的左括号都已经被匹配了.
        return leftParenthesesStack.isEmpty();
    }

    private Character getMatchedCharacter(char character) {
        switch (character) {
            case ')':
                return '(';
            case ']':
                return '[';
            case '}':
                return '{';
            default:
                return ' ';
        }
    }

    @Test
    public void testMinAddToMakeValid() {
        //
        String str = "))())";
        System.out.println(minAddToMakeValid(str));
    }

    /**
     * 最少的插入从而使括号字符串变得合法
     * 思路:
     * 以左括号为基准,来判断右括号的需求数量.rightNeed
     * 遍历括号字符串,如果是一个左括号,则右括号的需求量就加1 rightNeed++
     * 如果是一个右括号,则右括号的需求量就减1 rightNeed--.  如果在此期间右括号的需求量为负1,则需要插入一个左括号,并且重置右括号的需求量为0
     * 其中插入左括号的次数用leftInsert来计数
     *
     * @param str 括号字符串
     * @return 最少的插入次数
     */
    int minAddToMakeValid(String str) {
        int rightNeed = 0;
        int leftInsert = 0;
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            if (character == '(') {
                rightNeed++;
            } else {
                rightNeed--;
                if (rightNeed == -1) {
                    // 插入一个左括号
                    leftInsert++;
                    rightNeed = 0;
                }
            }
        }
        return rightNeed + leftInsert;
    }

    /**
     * 给你⼀个括号字符串 s，它只包含字符 '(' 和 ')'。⼀个括号字符串被称为平衡的当它满⾜：
     * 1、任何左括号 '(' 必须对应两个连续的右括号 '))'。
     * 2、左括号 '(' 必须在对应的连续两个右括号 '))' 之前。
     * ⽐⽅说 "())"，"())(())))" 和 "(())())))" 都是平衡的，")()"，"()))" 和 "(()))" 都是不平衡
     * 的。
     * 你可以在任意位置插⼊字符 '(' 和 ')'，请你返回让 s 平衡的最少插⼊次数。
     */
    @Test
    public void testMinAddToMakeValid2() {
        String str = "(()))";
        int insertCount = minAddToMakeValid2(str);
        System.out.println(insertCount);
    }

    // (
    // ()
    // ()(
    // ))(
    // ")) )) )) )"
    // ()()) (
    private int minAddToMakeValid2(String str) {
        int rightNeed = 0;
        int leftInsert = 0;
        int rightInsert = 0;
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            if (character == '(') {
                rightNeed += 2;
                // () (
                // "(()))(()))()())))"
                // 如果插入的是一个左括号, 则右括号的需求量要保持为偶数,
                // 要保持偶数,则如果右括号的需求量为奇数, 则需要插入一个右括号,同时右括号的需求量也减1
                if (rightNeed % 2 == 1) {
                    rightInsert++;
                    rightNeed--;
                }
            } else {
                rightNeed -= 1;
                // )
                // 右括号的需求量为-1的时候, 需要插入一个左括号,同时右括号的需求量也要加1.
                if (rightNeed == -1) {
                    leftInsert++;
                    rightNeed = 1;
                }
            }
        }
        return rightNeed + leftInsert +rightInsert;
    }
}
