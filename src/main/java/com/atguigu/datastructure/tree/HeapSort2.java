package com.atguigu.datastructure.tree;


import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/25 20:58
 */
public class HeapSort2 {
    public static void main(String[] args) {
        // 堆排序的三个步骤
        // 1.将无序序列构造成一个堆, 根据升序还是降序需求选择构建成一个大顶堆 还是小顶堆,此时,整个序列的最大值就是堆顶的根节点
        // 2.然后将堆顶的根节点与末尾元素进行交换, 此时末尾元素就为最大值了
        // 3.然后将剩余的n-1个元素重新构造成一个堆, 这样就会得到n个元素的次大值,然后反复执行,就可以得到一个有序序列了.
        int[] arr = new int[]{4, 6, 8, 7, 3, 2, 1, 5, 9};
        System.out.println("排序前: " + Arrays.toString(arr));

        heapSort(arr);

        System.out.println("排序后: " + Arrays.toString(arr));
    }

    private static void heapSort(int[] arr) {
        // 1.首先将无序列表  构造成一个大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        // 2.然后将毒堆顶根节点与末尾元素进行交换
        for (int i = arr.length - 1; i >= 0; i--) {
            // 交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            adjustHeap(arr, 0, i);
        }


    }

    /**
     * 完成将以i为父节点的子树调整成大顶堆  父子节点 的顺序是从下到上.最左到右,
     *
     * @param array  数组
     * @param i      父节点的下标
     * @param length 数组需要调整的长度.
     */
    private static void adjustHeap(int[] array, int i, int length) {
        // 1.先将当前父节点存起来 temp = arr[i](指针i 指向父节点)
        // 2.然后循环比较当前父节点的左右节点,让指针k 指向较大的子节点,
        // 3.接着比较arr[k] 与temp ,如果arr[k] 大,就用arr[i]去存储这个较大的值. 并且指针i 指向这个较大的值. 否则退出循环
        // 4.k指针下移到 下一个左子节点. 然后继续循环比较. 直到数组索引越界或者 遇到break 退出

        int temp = array[i];
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            // 左子节点 < 右子节点
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            if (array[k] > temp) {
                // arr[i] 存储这个较大的值
                array[i] = array[k];
                // 指针i  指向这个较大的值.
                i = k;
            } else {
                // 结束当前for 循环, 进入前一个父节点进行比较
                break;
            }
        }
        // 当前树的最大值的位置 接收(存储) 当前父节点的值.
        array[i] = temp;
    }
}
