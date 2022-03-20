package com.atguigu.Algorithm.kmp;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/15 7:22
 */
public class ViolenceMatch2 {
    public static void main(String[] args) {
        // 现在要判断 str1 是否含有 str2, 如果存在，就返回第一次出现的位置, 如果没有，则返回-1
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int result = violenceMath(str1, str2);
        System.out.println("result : " + result);
    }

    private static int violenceMath(String str1, String str2) {
        // 有两个指针i,j,一个指向str1,一个指向str2
        // 开始的时候i会一直移动,j只会再符合要求的情况下移动
        // 如果指针i 指向的字符 与指针j 指向的字符 匹配的话. 则j++,
        // 否则,j会重置为0. i 会重置为 i-j+1

        int i = 0;
        int j = 0;
        while (i < str1.length()) {
            if (str1.charAt(i) == str2.charAt(j)) {
                while (j < str2.length() - 1) {
                    j++;
                    i++;
                    if (str1.charAt(i) != str2.charAt(j)) {
                        i = i - j;
                        j = 0;
                        break;
                    }
                }
            }
            if (j == str2.length() - 1) {
                break;
            }
            i++;
        }

        if (j == str2.length() - 1) {
            return i - j;
        } else {
            return -1;
        }
    }

    // 有两个指针i,j,一个指向str1,一个指向str2
    // 开始的时候i会一直移动,j只会再符合要求的情况下移动
    // 如果指针i 指向的字符 与指针j 指向的字符 匹配的话. 则j++,
    // 否则,j会重置为0. i 会重置为 i-j+1
    private static int violenceMath2(String str1, String str2) {
        int i = 0;
        int j = 0;
        while (i < str1.length() && j < str2.length()) {
            // 因为是先匹配是否相等,然后再自增i,j的,所以charAt的时候不会 越界.
            if (str1.charAt(i) == str2.charAt(j)) {
                i++;
                j++;
            } else {
                // 指针i回退 j步, 再往前走一步.
                i = i - j + 1;
                j = 0;
            }
        }
        // 此时,如果匹配成功,则 j == str2.length
        int result = -1;
        if (j == str2.length()) {
            result = i - j;
        }
        return result;
    }


}
