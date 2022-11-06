package com.atguigu.oj;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/22 13:37
 */
public class IntegerFactorization {
    /**
     * 正整数 N的K-P 分解 是指将N写成K个正整数的P次方的和,表示为:
     * N = n[1]^p + ,...+n[k]^p
     * <p>
     * 其中n[i]（i = 1...K) 是第i个分解因子
     * <p>
     * 现在给定正整数 N,K,P 请计算N的K-P分解,并按如下规则返回结果:
     * 如果有多个解,请做出分解因子的和最大的,并按照降序返回分解因子的序列.
     * 如果分解因子的和最大的右多个,则选择序列最大的,序列比较凡是,:降序排序后,依次进行数值比较.
     * 如果无解,则返回空序列.
     * 输入:
     * <p>
     * 输入3个正整数,N,K,P
     * N取值范围[1,400]
     * k取值范围[1,N]
     * P取值范围[2,7]
     * <p>
     * 输入样例1
     * 169 5 2
     * 输出样例1[6 6 6 6 5]
     * <p>
     * 输入样例2
     * 169 167 3
     * 输出
     * []
     * <p>
     * 输入样例3
     * 28 4 2
     * 输出
     * [4 2 2 2]
     */
    private static List<int[]> resultList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int nValue = cin.nextInt();
        int kValue = cin.nextInt();
        int pValue = cin.nextInt();

        cin.close();
        int[] results = getDecomposeResult(nValue, kValue, pValue);
        String collect = Arrays.stream(results).mapToObj(String::valueOf).collect(Collectors.joining(" ", "[", "]"));
        System.out.println(collect);
    }

    private static int[] getDecomposeResult(int nValue, int kValue, int pValue) {
        int[] baseNumberArr = new int[kValue];
        // 先往数组的第一个位置放置底数.(选尽可能的大的)
        // 选择: 数组的第index 个位置要放置什么数字, (需要放置 k个数) 先往下标为0的位置 放置底数.
        //  路径链表: 前index-1 个位置都已经放置好了底数
        dfs(nValue, kValue, pValue, baseNumberArr, 0);

        int[] bestResult = new int[kValue];
        int maxSum = 0;
        int maxBaseNumber = 0;
        for (int[] arr : resultList) {
            int sum = Arrays.stream(arr).sum();
            if (sum > maxSum || (sum == maxSum && arr[0] > maxBaseNumber)) {
                bestResult = arr;
                // 更新最好的结果数组, 同时更新两个max.
                maxSum = sum;
                maxBaseNumber = arr[0];
            }
        }
        return bestResult;
    }

    private static void dfs(int nValue, int kValue, int pValue, int[] baseNumberArr, int index) {
        // base case1 如果次条件满足
        // 说明底数数组前 kValue-1个位置都已经放置好了合适的底数, 并且这些底数的p次幂 之和刚好为nValue.
        if (index == kValue && nValue == 0) {
            resultList.add(Arrays.copyOf(baseNumberArr, kValue)); //todo 递归过程一直在改变baseNumberArr,所以要new Array
            return;
        }
        // base case2 真数有剩余
        if (index == kValue && nValue > 0) {
            return;
        }
        // 底数数量有剩余
        if (index < kValue && nValue == 0) {
            return;
        }

        int maxBaseNumber = getMaxBaseNumber(nValue, pValue);
        // 保证底数数组是降序排序的.
        if (index >= 1 && maxBaseNumber > baseNumberArr[index - 1]) {
            maxBaseNumber = baseNumberArr[index - 1];
        }
        // 路径链表: 前index-1 个位置都已经放置好了底数
        // 选择列表: 可以从maxBaseNumber ~1 范围中挑选一个底数进行放置.
        for (int baseNumber = maxBaseNumber; baseNumber >= 1; baseNumber--) {
            // 做选择
            baseNumberArr[index] = baseNumber;
            int remainValue = nValue - (int) Math.pow(baseNumber, pValue);
            dfs(remainValue, kValue, pValue, baseNumberArr, index + 1);
            // 取消选择
            baseNumberArr[index] = 0;
        }
    }

    private static int getMaxBaseNumber(int nValue, int pValue) {
        // 获取最大的底数
        int maxBaseNumber = 1;
        while (Math.pow(maxBaseNumber, pValue) <= nValue) {
            maxBaseNumber++;
        }
        // 最后一次 一定是大于了 nValue
        return maxBaseNumber - 1;
    }


    @Test
    public void testIntArrIntegerArrIntegerListConvert() {
        int[] intArr = new int[]{1, 2, 3, 4};
        // int[] -> Integer[]  Integer[] -> int[]
        Integer[] integerArrFromInt = Arrays.stream(intArr).boxed().toArray(Integer[]::new);
        System.out.println("integerArrFromInt: " + Arrays.toString(integerArrFromInt));
        int[] intArrFromInteger = Arrays.stream(integerArrFromInt).mapToInt(item -> item).toArray();
        System.out.println("intArrFromInteger: " + Arrays.toString(intArrFromInteger));


        // int[]/Integer[] -> List<Integer>
        List<Integer> integerList1 = Arrays.stream(intArr).boxed().collect(Collectors.toList());
        System.out.println("integerList1: " + integerList1);
        List<Integer> integerList2 = Arrays.asList(integerArrFromInt);
        System.out.println("integerList2: " + integerList2);
        List<Integer> integerList3 = Arrays.stream(integerArrFromInt).collect(Collectors.toList());
        System.out.println("integerList3: " + integerList3);

        // List<Integer> -> int[]/ Integer[]
        int[] intArrFromList = integerList1.stream().mapToInt(item -> item).toArray();
        System.out.println("intArrFromList: " + Arrays.toString(intArrFromList));
        Integer[] integerArrFromList = integerList1.toArray(new Integer[0]);
        System.out.println("integerArrFromList: " + Arrays.toString(integerArrFromList));


    }
}
