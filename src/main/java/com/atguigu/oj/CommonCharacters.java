package com.atguigu.oj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/21 21:03
 */
public class CommonCharacters {
    /**
     * 给定m个字符串,请计算有那些字符在所有的字符串中都出现过n次以及以上.
     * <p>
     * 按照ASCII码的升序进行排序,如果没有则为空序列[]
     */

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int nValue = cin.nextInt();
        int mValue = cin.nextInt();
        cin.nextLine();
        String[] strings = new String[mValue];
        for (int i = 0; i < mValue; i++) {
            strings[i] = cin.nextLine();
        }

        cin.close();

        char[] results = getNnTimesCharacter(nValue, strings);
        for (char result : results) {
            System.out.println(result);
        }
    }

    private static char[] getNnTimesCharacter(int nValue, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return new char[0];
        }
        String firstStr = strArr[0];
        char[] charArray = firstStr.toCharArray();
        List<Character> characterList = new ArrayList<>();
        for (char ch : charArray) {
            if (!characterList.contains(ch)) {
                characterList.add(ch);
            }
        }
        List<Character> resultList = new ArrayList<>();
        for (Character checkCharacter : characterList) {
            if (isValidCharacter(checkCharacter, nValue, strArr)) {
                resultList.add(checkCharacter);
            }
        }
        Collections.sort(resultList);
        char[] chars = new char[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            chars[i] = resultList.get(i);
        }
        return chars;
    }

    private static boolean isValidCharacter(Character checkCharacter, int nValue, String[] strArr) {
        for (String str : strArr) {
            if (str.length() - str.replaceAll(String.valueOf(checkCharacter), "").length() < nValue) {
                return false;
            }
        }
        return true;
    }
}
