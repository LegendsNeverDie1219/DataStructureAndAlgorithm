package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/4 22:21
 */
public class BinaryLongestContinusDigit {
    static int lengthMax = 1;
    static int scanIndex = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        long num = scanner.nextLong();
        scanner.close();
        System.out.println(getLongestNum2(num));
    }


    private static int getLongestNum(long num) {
        String binaryString = Long.toBinaryString(num);
        char[] charArray = binaryString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char currentCharacter = charArray[i];
            int currentCharacterLengthMax = getCurrentCharacterLengthMax(currentCharacter, charArray, i+1, 1);
            lengthMax = Math.max(currentCharacterLengthMax, lengthMax);
        }

        return lengthMax;
    }

    /**
     * 获取当前字符 连续出现的 最大长度.
     *
     * @param currentCharacter                 当前字符
     * @param charArray                        字符数组
     * @param nextIndex                        下一个字符索引
     * @param currentCharacterContinuousLength 当前字符连续的长度.
     * @return
     */
    private static int getCurrentCharacterLengthMax(char currentCharacter, char[] charArray, int nextIndex,
                                                    int currentCharacterContinuousLength) {
        if (nextIndex >= charArray.length || currentCharacter != charArray[nextIndex]) {
            return currentCharacterContinuousLength;
        }
        // 做选择
        int length = currentCharacterContinuousLength + 1;
        // 进入下一层决策树.
        return getCurrentCharacterLengthMax(currentCharacter, charArray, nextIndex + 1,
                length);
    }

    private static int getLongestNum2(long num) {
        String binaryString = Long.toBinaryString(num);
        char[] charArray = binaryString.toCharArray();

        while (scanIndex < charArray.length) {
            char currentCharacter = charArray[scanIndex];
            int currentCharacterLengthMax = getCurrentCharacterLengthMax2(currentCharacter, charArray, 1);
            lengthMax = Math.max(currentCharacterLengthMax, lengthMax);
            scanIndex++;
        }
        return lengthMax;
    }

    private static int getCurrentCharacterLengthMax2(char currentCharacter, char[] charArray,
                                                     int currentCharacterContinuousLength) {
        if (scanIndex + 1 >= charArray.length || currentCharacter != charArray[scanIndex + 1]) {
            return currentCharacterContinuousLength;
        }
        // 做选择
        int length = currentCharacterContinuousLength + 1;
         scanIndex++;
        // 进入下一层决策树.
        return getCurrentCharacterLengthMax2(currentCharacter, charArray, length);
    }
}
