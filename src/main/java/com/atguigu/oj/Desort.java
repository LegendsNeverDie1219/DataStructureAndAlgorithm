package com.atguigu.oj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/17 20:37
 */
public class Desort {
    /**
     * 给定一个右正整数组成的数组,请按照以下规则,使数组变得有序(递增或者递减,不一定要严格的递增或者严格递减)
     * <p>
     * 数组已经是有序的,则不做任何处理, 返回数组中最小的元素值.
     * 如果数组不是有序的.
     * 若删除其中一个元素,使得剩余元素是有序的,返回需要删除的元素值.如果存在多个解.则选择删除元素值最小的.
     * 如果无解,则返回-1.
     * <p>
     * 输入
     * 第一行一个整数, 表示给定数组的长度N,范围[2,200]
     * <p>
     * 第二行N个整数, 表示给定的数组,每个元素值的范围[0,200]
     * <p>
     * 样例:
     * 输入:
     * 2
     * 1 2
     * 输出:
     * 1
     * <p>
     * 样例2
     * <p>
     * 5
     * 4 4 2 3 1
     * 2, 删除2 之后数组 4 4 3 1 是有序的.删除 3 后数组 4 4 2 1也是有序的.但是取最小值.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] nums = new int[length];

        for (int i = 0; i < length; i++) {
            nums[i] = scanner.nextInt();
        }
        scanner.close();

        System.out.println(sortByDeleteOne(nums));
    }

    private static int sortByDeleteOne(int[] nums) {
        //思路: 分情况讨论
        // 1.如果数组本身是有序的,则直接返回第一个元素/最后一个元素中的最小值
        // 2.如果数组不是有序的,则尝试删除下标为index的元素之后,(这里采取的是复制数组的方式),再去进行数组是否有序的判断.
        // 如果有序,则添加到resultList中.
        // 3.判断resultList是否为空,如果为空,则说明删除一个元素还是不能保证数组有序,则返回-1.否则返回resultList.sorted().get(0)
        if (isSorted(nums) == -1) {
            return Math.min(nums[0], nums[nums.length - 1]);
        }
        // 数组无序,则进行deleteOneElementThenJudge
        List<Integer> resultList = deleteOneElementThenJudge(nums);
        if (resultList.isEmpty()) {
            return -1;
        }
        Collections.sort(resultList);
        return resultList.get(0);
    }

    private static List<Integer> deleteOneElementThenJudge(int[] nums) {
        // 代码走到这里,说明至少有3个元素.
        List<Integer> resultList = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            int[] newArr = new int[nums.length - 1];
            // 复制原数组的范围: [0,index-1] [index+1,nums.length-1]
            // srcPos+length= 0 + 0
            // srcPos +length = 5 +0
            System.arraycopy(nums, 0, newArr, 0, index);
            // 长度为: index + nums.length-index -1 = nums.length-1
            System.arraycopy(nums, index + 1, newArr, index, nums.length - index - 1);
            if (isSorted(newArr) == -1) {
                resultList.add(nums[index]);
            }
        }
        return resultList;
    }

    private static int isSorted(int[] nums) {
        if (nums[0] < nums[nums.length - 1]) {
            // 判断是否是一直升序.
            for (int i = 0; i < nums.length - 1; i++) {
                // 说明index = i+1的元素 排序不对.开始降序了
                if (nums[i] > nums[i + 1]) {
                    return i + 1;
                }
            }
        } else {
            // 判断是否是一直降序.
            for (int i = 0; i < nums.length - 1; i++) {
                // 说明index = i+1的元素 排序不对.开始降序了
                if (nums[i] < nums[i + 1]) {
                    return i + 1;
                }
            }
        }
        return -1;
    }
}
