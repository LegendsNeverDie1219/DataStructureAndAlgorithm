package com.atguigu.oj;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/21 21:38
 */
public class MobilePhoneCellinputMethod {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();

        scanner.close();
        String result = getDisplayString(inputStr);
        System.out.println(result);
    }

    private static String getDisplayString(String inputStr) {
        char[] charArray = inputStr.toCharArray();

        // 建立一个map 函数,记录数字-> 英文字母的映射关系
        Map<Character, String> numLettersMap = constructNumLettersMap();
        boolean isNumInputMethod = true;
        StringBuilder resultSb = new StringBuilder();
        StringBuilder tempSb = new StringBuilder();
        for (char ch : charArray) {
            // 被#号打断.此时要输出缓存中的字符. 并清空缓存,并切换输入法.
            if (ch == '#') {
                outputCharsFromTempSb(tempSb, resultSb, isNumInputMethod, numLettersMap);
                //并清空缓存
                tempSb.delete(0, tempSb.length());
                //切换输入法.
                isNumInputMethod = !isNumInputMethod;
            } else if (ch == '/') {
                // 被/打断, 此时要输出缓存中的字符,并清空缓存,
                outputCharsFromTempSb(tempSb, resultSb, isNumInputMethod, numLettersMap);
                //并清空缓存
                tempSb.delete(0, tempSb.length());
            } else if (tempSb.length() != 0 && tempSb.toString().indexOf(ch) < 0) {
                // 被其他字符打断,此时要输出缓存中的字符, 并清空缓存,然后再将当前的ch存入缓存中.
                outputCharsFromTempSb(tempSb, resultSb, isNumInputMethod, numLettersMap);
                //并清空缓存
                tempSb.delete(0, tempSb.length());
                tempSb.append(ch);
            } else {
                // 缓存为空 或者是 一直输入的是相同的字符. 此时需要一直往缓存中添加.
                tempSb.append(ch);
            }
        }
        // 最后一个/一组字符.
        outputCharsFromTempSb(tempSb, resultSb, isNumInputMethod, numLettersMap);
        return resultSb.toString();
    }

    private static void outputCharsFromTempSb(StringBuilder tempSb, StringBuilder resultSb, boolean isNumInputMethod,
                                              Map<Character, String> numberLettersMap) {
        //输出缓存中的字符. 并清空缓存

        if ("".equals(tempSb.toString())) {
            return;
        }
        // 是数字输入法.
        if (isNumInputMethod) {
            resultSb.append(tempSb);
            return;
        }
        // 是字母输入法
        // 是0000..
        char firstChar = tempSb.charAt(0);
        if (firstChar == '0') {
            tempSb.replace(0, tempSb.length(), " ");
            resultSb.append(tempSb);
        } else {
            String lettersStr = numberLettersMap.get(firstChar);
            char resultChar;
            // 6 % 3 == 0
            // 7%3 == 1
            int index = tempSb.length() % lettersStr.length();
            if (index == 0) {
                // 取最后一个字符
                resultChar = lettersStr.charAt(lettersStr.length() - 1);
            } else {
                //
                resultChar = lettersStr.charAt(index - 1);
            }
            resultSb.append(resultChar);
        }
    }

    private static Map<Character, String> constructNumLettersMap() {
        Map<Character, String> numLettersMap = new HashMap<>();
        numLettersMap.put('1', ",.");
        numLettersMap.put('2', "abc");
        numLettersMap.put('3', "def");
        numLettersMap.put('4', "ghi");
        numLettersMap.put('5', "jkl");
        numLettersMap.put('6', "mno");
        numLettersMap.put('7', "pqrs");
        numLettersMap.put('8', "tuv");
        numLettersMap.put('9', "wxyz");
        return numLettersMap;
    }
}
