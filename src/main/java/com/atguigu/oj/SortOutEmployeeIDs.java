package com.atguigu.oj;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/11 20:09
 */
public class SortOutEmployeeIDs {
    /**
     * 合法的工号: 去除所有的空格后,长度不超过9. 首位为字母,其他均为数字(至少有一个数字)
     * 格式化要求: (格式化后如: a00012345)
     * <p>
     * 工号首字母统一规整成小写字符.
     * 去除所有空格.
     * 如果工号不满9位,在数字前面补零. 使工号长度恰好为9
     * 请对格式化后的合法工号,去重后,按照字典序升序输出.
     * <p>
     * 输入:
     * 一个整数N.表示待整理工号的个数.
     * 接下来的N行,每行字符串是一个待整理的工号.
     * <p>
     * 样例:
     * 8
     * ss789
     * 12n00
     * s00123
     * k2 3490
     * S123
     * s234
     * x235
     * m990
     * <p>
     * k00023490
     * m00000990
     * s00000123
     * s00000234
     * x00000235
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        String[] cardIds = new String[num];
        for (int i = 0; i < num; i++) {
            cardIds[i] = scanner.nextLine();
        }
        scanner.close();
        String[] results = regularId(cardIds);
        for (String result : results) {
            System.out.println(result);
        }
    }

    /**
     * regularID
     *
     * @param cardIds cardIds
     * @return String[]
     */
    private static String[] regularId(String[] cardIds) {
        // 合法的工号: 去除所有的空格后,长度不超过9. 首位为字母,其他均为数字(至少有一个数字)
        Set<String> validIds = new HashSet<>(); // todo 这里可以使用TreeSet

        for (String cardId : cardIds) {
            // 预处理字符串. (去除空格, 转小写.)
            String treatmentStr = preTreatmentStr(cardId);
            // 校验字符串(长度不超过9. 首位为字母,其他均为数字(至少有一个数字))
            if (!isValid2(treatmentStr)) {
                continue;
            }
            // 格式化字符串.(如果工号不满9位,在数字前面补零. 使工号长度恰好为9)
            String formatStr = formatStr2(treatmentStr);
            validIds.add(formatStr);
        }

        return validIds.stream().sorted().toArray(String[]::new);
    }
    
    @Test
    public void testStringFormat() {
        // 在左边补齐空格直到hello长度为10
        String str1 = String.format("%10s,a", "Hello");
        System.out.println("str1:"+ str1);

        // 在左边补齐空格直到123长度为10
        String str2 = String.format("%8d", 123);
        System.out.println("str2:"+ str2);
        System.out.println();



        // 在右边补齐空格直到hello长度为10
        String str3 = String.format("%-10s,a", "Hello");
        System.out.println("str1:"+ str3);
        // 在右边补齐空格直到123长度为10
        String str4 = String.format("%-8d", 123);
        System.out.println("str2:"+ str4);

        //  在左边补齐0直到123长度为8  1.仅对数字有效 2.补齐的数字只能是0(默认为空格)
        String str5 = String.format("%08d", 123);
        System.out.println(str5);


    }

    // 格式化字符串.(如果工号不满9位,在数字前面补零. 使工号长度恰好为9)
    private static String formatStr(String treatmentStr) {
        if (treatmentStr.length() == 9) {
            return treatmentStr;
        }
        // 不满9位
        int charLength = treatmentStr.length();
        int zeroLength = 9 - charLength;
        StringBuilder stringBuilder = new StringBuilder(treatmentStr);
        for (int i = 0; i < zeroLength; i++) {
            // 下标为0的位置是字母.所以从第1个位置开始插入
            stringBuilder.insert(1, '0');
        }
        return stringBuilder.toString();
    }

    private static String formatStr2(String treatmentStr) {
        String format = String.format("%s%08d", treatmentStr.charAt(0),
                Integer.valueOf(treatmentStr.substring(1)));
        return format;
    }


    // 校验字符串(长度不超过9. 首位为字母,其他均为数字(至少有一个数字))
    private static boolean isValid(String treatmentStr) {
        // 长度范围为 [2,9]
        if (treatmentStr.length() < 2 || treatmentStr.length() > 9) {
            return false;
        }
        char[] charArray = treatmentStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (i == 0) {
                if (!Character.isLetter(charArray[i])) {
                    return false;
                }
            } else {
                if (!Character.isDigit(charArray[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid2(String treatmentStr) {
        String regex = "^[a-z]{1}[\\d]{1,8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(treatmentStr);
        return matcher.matches();
    }

    // 预处理字符串. (去除空格, 转小写.)
    private static String preTreatmentStr(String cardId) {
         String replaceStr = cardId.replaceAll("\\s", "");
        return replaceStr.toLowerCase(Locale.ROOT);
    }

}
