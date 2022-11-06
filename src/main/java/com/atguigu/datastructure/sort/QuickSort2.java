package com.atguigu.datastructure.sort;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/20 15:24
 */
public class QuickSort2 {
    public static void main(String[] args) {
        int[] arr = {-9, 78, 0, 23, -567, 70, -1, 900, 4561};
        // 第一轮:
        // int[] arr = {-9,  78,  0, 23,-567,70, -1,900, 4561};
        //              l(坑)                              r
        // int[] arr = {-567,78,0,23, -567,   70, -1,900, 4561};
        //              l             r(坑)
        // int[] arr = {-567,78,0,23,  78,70, -1,900, 4561};
        //                   l(坑)      r

        // 第二轮:
        // int[] arr = {-567, 78, 0, 23, 78, 70, -1, 900, 4561};
        //                    l
        //                    r
        // int[] arr = {-567, -9, 0, 23, 78, 70, -1, 900, 4561};

        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1);

        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

    }

    // 快速排序思想:
    // 1.先从数组中选取一个中心轴元素pivot, 默认可以选择arr[left]
    // 2.接着把 比中心轴 小的所有元素 移动到 pivot 左边
    // 3.然后把 比中心轴 大的所有元素 移动到 pivot 右边
    // 4.接着会得到左右两个子序列, 其中左子序列的值都<= pivot 右子序列的值 都>= pivot, 再次对左右子序列递归执行1-3
    public static void quickSort(int[] array, int leftBorder, int rightBorder) {
        // 一个元素本身就是有序的,所以至少需要两个元素.
        if (leftBorder >= rightBorder) { //todo 注意
            return;
        }
        int left = leftBorder;
        int right = rightBorder;
        // 默认选择一个序列的 左边界元素.(arr[left] 为中心轴元素, 此时的left 也是坑的初始位置.)
        int pivot = array[left];

        // 只要左指针和右指针不重合, 就一直执行
        while (left < right) {
            // 坑在左边,所以需要先移动右指针.
            while (left < right && array[right] >= pivot) {
                right--;
            }
            if (left < right) {
                // 右指针 指向的元素 < pivot,需要移动该元素(即将该元素移入坑中,同时right指向的位置变成了新的坑.)
                array[left] = array[right];
            }

            while (left < right && array[left] <= pivot) {
                left++;
            }

            if (left < right) {
                // 左指针 指向的元素 > pivot,需要移动该元素(即将该元素移入坑中,同时left指向的位置为新的坑.)
                array[right] = array[left];
            }

            //说明 左右指针重合了. 需要将pivot的值放入坑中(left,right都可以)
            if (left >= right) {
                array[left] = pivot;
            }
        }

        // 递归左子序列
        quickSort(array, leftBorder, left - 1); //todo 注意放到while 外面
        // 递归右子序列
        quickSort(array, right + 1, rightBorder);
    }
}
