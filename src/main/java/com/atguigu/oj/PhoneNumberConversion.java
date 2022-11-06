package com.atguigu.oj;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/30 21:08
 */
public class PhoneNumberConversion {
    /**
     * 题目描述:
     * 某语音翻译软件,需要实现如下中英文电话号码转换功能:
     * 若输入的是英文数字单词或Double 组成的点好号码, 则输出对应的中文数字单词;
     * 若时输入为中文事实上素质单词组成的电话号码, 则输出对应的英文数字单词.
     * 若输入不合法,则输出字符串 ERROR.
     * 中文数字,英文数字分别见下表:
     * 中文数字单词 Yi Er San Si Wu Liu Qi Ba Jiu Ling
     * 英文数字单词 One Two Three Four Five Six Seven Eight Nine Zero
     * <p>
     * 说明:
     * 输入保证每个单词都是合法的英文数字单词/中文数字单词/Double
     * 合法的电话号码要么全中文, 不会含Double. 要么全英文, 可能含Double.
     * 若含有Double:
     * 合法格式:
     * 其后必须跟随英文数字单词, 代表两个该数字,如输入DoubleSix 代表SixSix;
     * 不合法的格式: 其后跟随的不是英文数字单词, 如DoubleLiu 或DoubleDouble
     * <p>
     * 输入:
     * 一行仅有大小写字母组成的字符串. 非空且长度 <= 500
     * 输出:
     * 一个字符串, 表示转换后的电话号码. 若输入不合法, 输出ERROR.
     * <p>
     * 样例:
     * 输入样例  1
     * SixOneThreeOneDoubleZero
     * <p>
     * 输出样例 1
     * <p>
     * LiuYiSanYiLingLing
     * <p>
     * 输入样例 2
     * YiLingSanSanJiu
     * 输出样例2
     * OneZeroThreeThreeNine
     * 输入样例 3
     * DoubleLiu
     * 输出样例
     * ERROR
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        String inputStr = scanner.nextLine();
        scanner.close();
        String result = translate(inputStr);
        System.out.println(result);
    }

    private static Map<String, String> zhMap = new HashMap<>();

    private static Map<String, String> enMap = new HashMap<>();


    static {
        //   * 中文数字单词 Yi Er San Si Wu Liu Qi Ba Jiu Ling
        //      * 英文数字单词 One Two Three Four Five Six Seven Eight Nine Zero
        zhMap.put("Yi", "One");
        zhMap.put("Er", "Two");
        zhMap.put("San", "Three");
        zhMap.put("Si", "Four");
        zhMap.put("Wu", "Five");
        zhMap.put("Liu", "Six");
        zhMap.put("Qi", "Seven");
        zhMap.put("Ba", "Eight");
        zhMap.put("Jiu", "Nine");
        zhMap.put("Ling", "Zero");

        zhMap.forEach((key, value) -> {
            enMap.put(value, key);
        });
        enMap.put("Double", "Double");
    }

    private static Map<String, String> doubleEnMap = new HashMap<>();

    static {
        doubleEnMap.put("DoubleOne", "YiYi");
        doubleEnMap.put("DoubleTwo", "ErEr");
        doubleEnMap.put("DoubleThree", "SanSan");
        doubleEnMap.put("DoubleFour", "SiSi");
        doubleEnMap.put("DoubleFive", "WuWu");
        doubleEnMap.put("DoubleSix", "LiuLiu");
        doubleEnMap.put("DoubleSeven", "QiQi");
        doubleEnMap.put("DoubleEight", "BaBa");
        doubleEnMap.put("DoubleNine", "JiuJiu");
        doubleEnMap.put("DoubleZero", "LingLing");
    }

    private static String translate(String inputStr) {
        if (inputStr.contains("DoubleDouble")) {
            return "ERROR";
        }
        // 字符串转化为以单词分隔的字符串集合.
        List<String> wordList = splitStrByWord(inputStr);
        // 校验该字符串是中文还是英文
        boolean zhCheckResult = isValidZhWord(wordList);

        // 如果是中文进行zhMap 的替换
        if (zhCheckResult) {
            String convertStr = replaceStrByZhMap(inputStr);
            if (isValidEnWord(splitStrByWord(convertStr))) {
                return convertStr;
            }
        }

        boolean enCheckResult = isValidEnWord(wordList);
        // 如果是英文则先进行doubleEnMap的替换, 然后再进行enMap 的替换.
        if (enCheckResult) {
            String convertStr = replaceStrByDoubleEnMapAndEnMap(inputStr);
            if (isValidZhWord(splitStrByWord(convertStr))) {
                return convertStr;
            }
        }
        return "ERROR";
    }

    private static String replaceStrByDoubleEnMapAndEnMap(String inputStr) {
        StringBuilder inputStrBuilder = new StringBuilder(inputStr);
        doubleEnMap.forEach((enWord, zhWord) -> {
            replaceInputStr(inputStrBuilder, enWord, zhWord);
        });

        enMap.forEach((enWord, zhWord) -> {
            replaceInputStr(inputStrBuilder, enWord, zhWord);
        });
        return inputStrBuilder.toString();
    }

    private static String replaceStrByZhMap(String inputStr) {
        StringBuilder inputStrBuilder = new StringBuilder(inputStr);
        zhMap.forEach((zhWord, enWord) -> {
            replaceInputStr(inputStrBuilder, zhWord, enWord);
        });
        return inputStrBuilder.toString();
    }

    private static String replaceStrByZhMap2(String inputStr) {
        String str = inputStr;
        for (Map.Entry<String, String> entry : zhMap.entrySet()) {
            String zhWord = entry.getKey();
            String enWord = entry.getValue();
            str = str.replaceAll(zhWord, enWord);
        }
        return str;
    }


    private static void replaceInputStr(StringBuilder inputStrBuilder, String sourceWord, String targetWord) {
        int startIndex = inputStrBuilder.indexOf(sourceWord);
        while (startIndex != -1) {
            // Si -> Four todo 替换的是inputStrBuilder本身, 即使是不接收inputStrBuilder还是会变.
            inputStrBuilder.replace(startIndex, startIndex + sourceWord.length(), targetWord);
            int fromIndex = startIndex + targetWord.length();
            startIndex = inputStrBuilder.indexOf(sourceWord, fromIndex);
        }
    }

    private static boolean isValidZhWord(List<String> wordList) {
        Set<String> zhMapKeys = zhMap.keySet();
        return zhMapKeys.containsAll(wordList);
    }

    private static boolean isValidEnWord(List<String> wordList) {
        Set<String> enMapKeys = enMap.keySet();
        return enMapKeys.containsAll(wordList);
    }

    private static List<String> splitStrByWord(String inputStr) {
        char[] charArray = inputStr.toCharArray();
        List<String> wordList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : charArray) {
            // A-Z的大写字母. 说明是新的一个单词.
            if (ch >= 'A' && ch <= 'Z') {// todo 最后一个单词需要添加.
                if (stringBuilder.length() != 0) {
                    // 把缓存中 已经存储的好的一个单词 ,添加到wordList中.
                    String oneWord = stringBuilder.toString();
                    wordList.add(oneWord);
                    // 清空缓存stringBuilder
                    stringBuilder.delete(0, stringBuilder.length());
                }
            }
            stringBuilder.append(ch);
        }
        wordList.add(stringBuilder.toString());
        return wordList;
    }

    @Test
    public void test() {
        List<Integer> list1 = Lists.newArrayList(1,2,3);
        List<Integer> list2 = Lists.newArrayList(1,2,3,1,2,3);
        // todo list2中的所有元素 如果都在list1中,则返回true.
        System.out.println(list1.containsAll(list2));
    }
}
