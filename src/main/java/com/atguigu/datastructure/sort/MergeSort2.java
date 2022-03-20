package com.atguigu.datastructure.sort;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/20 17:09
 */
public class MergeSort2 {
    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2}; //

        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);

        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

    }

    public static void mergeSort(int[] array, int left, int right, int[] temp) {
        // 最少要有两个元素 才可以继续分解. 左闭右闭
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle, temp);
            mergeSort(array, middle + 1, right, temp);

            // 合并
            mergeMethod(array, left, right, middle, temp);
        }
    }

    public static void mergeMethod(int[] array, int left, int right, int middle, int[] temp) {
        //  1.左子序列 范围[left,middle] 右子序列范围 [middle+1,right] 左子序列当前元素arr[i] 右子序列当前元素arr[j]
        // 1.扫描排序好的左子序列,右子序列, 如果左子序列当前元素arr[i]<= 右子序列当前元素arr[j],就将arr[i]添加到temp数组中,否则添加arr[j]
        // 2. 如果左子序列或者右子序列还有剩余的元素, 则一股脑添加到temp 数组中
        // 3.将temp[] 数组元素复制到array中.
        int i = left;
        int j = middle + 1;
        int t = 0;
        while (i <= middle && j <= right) {
            //如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
            //即将左边的当前元素，填充到 temp数组
            //然后 t++, i++
            if (array[i] <= array[j]) {
                temp[t] = array[i];
                t++;
                i++;
            } else {
                temp[t] = array[j];
                t++;
                j++;
            }
        }
        // 说明左子序列还有剩余
        if (i <= middle) {
            while (i <= middle) {
                temp[t] = array[i];
                t++;
                i++;
            }
        } else {
            while (j <= right) {
                temp[t] = array[j];
                t++;
                j++;
            }
        }

        t = 0;
        //第一次合并 left = 0 , right = 1 //  left = 2  right = 3 // tL=0 ri=3
        //最后一次 left = 0  right = 7
        while(left <= right) {
            array[left] = temp[t];
            t++;
            left++;
        }
    }
}
