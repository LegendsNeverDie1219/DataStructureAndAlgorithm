package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/21 15:16
 */
public class CharacterSorting {
    /**
     * 数字+ 字母构成的字符串 进行排序,
     * 排序之后 ,原位置是数字 排序之后仍然是数字, 是字母 仍然是字母.
     * 其中英文字母 大写字母拍到后面.小写排到前面.
     *
     *
     * 输入:
     * ab12C4Ac3B
     * 输出:
     * ab12c3AB4C
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        String inputStr = scanner.nextLine();
        scanner.close();
        String result = characterSort(inputStr);
        System.out.println(result);
    }

    private static String characterSort(String inputStr) {
        char[] charArray = inputStr.toCharArray();
        List<Character> numberList = new ArrayList<>();
        List<Character> letterList = new ArrayList<>();

        for (char ch : charArray) {
            if (Character.isDigit(ch)) {
                numberList.add(ch);
            } else {
                letterList.add(ch);
            }
        }

        Collections.sort(numberList);
        letterList.sort((o1, o2) -> {
            if (Character.isUpperCase(o1) && Character.isLowerCase(o2)) {
                return 1;
            } else if (Character.isUpperCase(o2) && Character.isLowerCase(o1)) {
                return -1;
            }
            return Character.compare(o1, o2);
        });

        StringBuilder sb = new StringBuilder();
        for (char ch : charArray) {
            if (Character.isDigit(ch)) {
                Character removeChar = numberList.remove(0);
                sb.append(removeChar);
            } else {
                Character removeChar = letterList.remove(0);
                sb.append(removeChar);
            }
        }
        return sb.toString();
    }
}
