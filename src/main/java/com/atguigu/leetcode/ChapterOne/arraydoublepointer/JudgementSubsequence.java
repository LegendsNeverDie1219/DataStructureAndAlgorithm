package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/24 8:04
 */
public class JudgementSubsequence {
    /**
     * 给定字符串s和t，判断s是否为t的⼦序列。
     * 进阶：如果有⼤量输⼊的 S，称作 S1, S2, ... , Sk 其中 k > 10 亿，你需要依次检查它们是否为 T 的⼦
     * 序列。在这种情况下，你会怎样改变代码？
     * 示例 1：
     * 输⼊：s = "abc", t = "ahbgdc"
     * 输出：true
     */
    @Test
    public void testJudgementSubsequence() {
        String s = "abc";
        String t = "ahbgdc";
        boolean result = judgementSubsequence(s, t);
        System.out.println("result: " + result);

    }

    @Test
    public void testJudgementSubsequence2() {
        String s = "abc";
        String t = "cacbhbc";
        boolean result = judgementSubsequence2(s, t);
        System.out.println("result: " + result);
    }

    private boolean judgementSubsequence2(String shortStr, String longStr) {
        // 利用字典进行进行搜索,这样可以减少遍历longStr的次数
        // 1.预处理字符串longStr,形成一个字典
        // 2.使用二分查找进行来获取 比指针j 大的 最小索引 minCorrectIndex (其中minCorrectIndex是符合 shortStr[i] == longStr[minCorrectIndex]条件的)
        // minCorrectIndex = arr[pos]
        Map<Character, List<Integer>> dictionaryMap = new HashMap<>();
        char[] chars = longStr.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            Character characterKey = chars[index];

            List<Integer> indexList = dictionaryMap.getOrDefault(characterKey, new ArrayList<>());
            indexList.add(index);
            dictionaryMap.put(characterKey, indexList);
        }
        // {a=[1], b=[3, 5], c=[0, 2, 6], h=[4]}    "abc";   "cacbhbc";
        System.out.println(dictionaryMap);
        // 指向longStr
        int j = 0;
        for (int i = 0; i < shortStr.length(); i++) {
            char character = shortStr.charAt(i);
            List<Integer> indexList = dictionaryMap.get(character);

            if (indexList == null || indexList.size() == 0)  {
                return false;
            }
            // 使用二分查找进行来获取 比指针j 大的 最小索引 minCorrectIndex
            // (其中minCorrectIndex是符合 shortStr[i] == longStr[minCorrectIndex]条件的, minCorrectIndex = indexList[leftBound])
            int leftBound = findMinCorrectIndex(j,indexList);

            if (leftBound >= indexList.size()) {
                // 即在数组/集合 中没有找到 比指针j 大的最小索引indexList[index]
                return false;
            }
            int minCorrectIndex = indexList.get(leftBound);
            // 此时 shortStr的字符a就已经匹配上了,开始匹配字符b
            j = minCorrectIndex +1;
        }
        return true;

    }

    private int findMinCorrectIndex(int j, List<Integer> indexList) {
        int left = 0;
        int right = indexList.size()-1;
        while (left <= right) {
            int middle = left + (right-left)/2;
            if (indexList.get(middle) < j) {
                // [middle+1,right]
                left = middle +1;
            } else if (indexList.get(middle) > j) {
                // [left,middle-1]
                right = middle -1;
            } else {
                // 向左收缩边界,以锁定左边界
                right = middle -1;
            }
        }
        return left;
    }

    @Test
    public void test() {
        // 如果map中没有该key对应的值,即get(key)为null, (其中newValue可以为空) 则直接添加，并返回null，
        // 否则不添加，则依旧为原来的值。
        //dictionaryMap.putIfAbsent(characterKey,indexList);
        // 如果map中没有该key对应的值,即get(key)为null, 并且mappingFunction 映射函数的结果不为null, 则会添加. 并返回映射函数的result
        // 否则不添加,返回旧的value
        // dictionaryMap.computeIfAbsent(characterKey,indexList)
    }

    private boolean judgementSubsequence(String s, String t) {
        // 利用双i,j,分别指向s,t
        // 指针一边前进 一边匹配子序列,其中指针t 每次都会向右移动一位,指针 s则根据情况 进行移动
        // 最终 判断s是否为子序列 ,只需要判断s.length 与i 的关系
        int i = 0;
        int j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }

}
