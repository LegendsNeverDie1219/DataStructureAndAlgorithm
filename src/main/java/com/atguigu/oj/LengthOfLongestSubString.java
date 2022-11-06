package com.atguigu.oj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/8 21:09
 */
public class LengthOfLongestSubString {
    /**
     * asdbuiodevauufgh
     * 输出:
     * 3
     * 提示样例:
     * 最长元音子串为 "uio:或者 "auu" 其长度为3, 因此输出为3.
      */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        System.out.println(getLongestVowelStringLength(input));
    }

    private static int getLongestVowelStringLength(String input) {
        List<Character> characterList = Arrays.asList('a', 'e', 'i', 'o', 'u', 'A','E','I','O','U');
        List<Character> vowelCharacterList = new ArrayList<>();
        int maxLength = 0;

        char[] inputStrArr = input.toCharArray();
        for (int i = 0; i < inputStrArr.length; i++) {
            char character = inputStrArr[i];
            if(characterList.contains(character)) {
                vowelCharacterList.add(character);
                // 更新最大长度. 已有最大长度记录 和 目前的最大长度记录进行比较.
                maxLength = Math.max(vowelCharacterList.size(),maxLength);
            } else {
                vowelCharacterList.clear();
            }
        }
        return maxLength;
    }
}
