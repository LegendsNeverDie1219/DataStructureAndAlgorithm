package com.atguigu.leetcode.ChapterThree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/26 22:28
 */
public class BackTrack2 {
    /**
     * 电话号码的字母组合.
     * 输⼊：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     */
    @Test
    public void test() {
        String digits = "23";
        List<String> strings = letterCombinations(digits);
        strings.forEach(System.out::println);
    }

    public List<String> letterCombinations(String digits) {
        List<String> resultList = new ArrayList<>();
        Map<Character, String> mappingMap = new HashMap<>();
        initMappingMap(mappingMap);
        StringBuilder trackBuilder = new StringBuilder();
        char[] digitArr = digits.toCharArray();
        backTrack4LetterCombinations(digitArr, 0, trackBuilder, mappingMap, resultList);
        return resultList;

    }

    private void backTrack4LetterCombinations(char[] digitArr, int start, StringBuilder trackBuilder,
                                              Map<Character, String> mappingMap, List<String> resultList) {
        // base case  不能交换顺序.
        if (trackBuilder.length() == digitArr.length) {
            resultList.add(trackBuilder.toString());
            return;
        }

        for (int i = start; i < digitArr.length; i++) {
            String mappingLetter = mappingMap.get(digitArr[i]);
            char[] letterArr = mappingLetter.toCharArray();
            for (char ch : letterArr) {
                trackBuilder.append(ch);
                backTrack4LetterCombinations(digitArr, i + 1, trackBuilder, mappingMap, resultList);
                trackBuilder.deleteCharAt(trackBuilder.length() - 1);
            }
        }
    }

    private void initMappingMap(Map<Character, String> mappingMap) {
        mappingMap.put('0', "");
        mappingMap.put('1', "");
        mappingMap.put('2', "abc");
        mappingMap.put('3', "def");
        mappingMap.put('4', "ghi");
        mappingMap.put('5', "jkl");
        mappingMap.put('6', "mno");
        mappingMap.put('7', "pqrs");
        mappingMap.put('8', "tuv");
        mappingMap.put('9', "wxyz");
    }

    @Test
    public void testGenerateParenthesis() {
        List<String> strings = generateParenthesis(3);
        strings.forEach(System.out::println);
    }

    /**
     * 比如说，输入n=3，输出为如下 5 个字符串：
     * "((()))",
     * "(()())",
     * "(())()",
     * "()(())",
     * "()()()"
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> resultList = new ArrayList<>();
        StringBuilder trackBuilder = new StringBuilder();
        // 2n个位置, 决策树的高度为2n, 每个位置的选择为( or ) ,决策树的宽度为2
        backTrack4GenerateParenthesis(n, n, trackBuilder, resultList);
        return resultList;
    }

    private void backTrack4GenerateParenthesis(int leftRemain, int rightRemain, StringBuilder trackBuilder,
                                               List<String> resultList) {

        // 异常的 base case
        if (leftRemain < 0 || rightRemain < 0) {
            return;
        }
        // 右括号 用的太多.
        if (leftRemain > rightRemain) {
            return;
        }
        // 正常的base case ,则更新结果.
        if (leftRemain == 0 && rightRemain == 0) {
            resultList.add(trackBuilder.toString());
            return;
        }

        // 该位置的选择只有两个 ( or )
        trackBuilder.append("(");
        backTrack4GenerateParenthesis(leftRemain - 1, rightRemain, trackBuilder, resultList);
        trackBuilder.deleteCharAt(trackBuilder.length() - 1);


        trackBuilder.append(")");
        backTrack4GenerateParenthesis(leftRemain, rightRemain - 1, trackBuilder, resultList);
        trackBuilder.deleteCharAt(trackBuilder.length() - 1);


    }
}
