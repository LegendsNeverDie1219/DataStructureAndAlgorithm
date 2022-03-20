package com.atguigu.datastructure.tree;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/23 21:21
 */
public class ArrayBinaryTreeDemo2 {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        arrayBinaryTree.preOrder(0);
    }

    // 顺序存储二叉树的遍历
    private static class ArrayBinaryTree {
        private int[] arr;

        public ArrayBinaryTree(int[] arr) {
            this.arr = arr;
        }

        // 数组顺序存储二叉树时
        // 下标为index的元素 的左子节点的下标为 2*index + 1
        // 下标为index的元素 的右子节点的下标 为2*index + 2
        // 下标为index的元素 的父节点的下标 为 (index-1)/2

        // 前序遍历 传进来根元素在数组中的下标 一般是0
        public void preOrder(int index) {
            //1.首先判断数组是否为空,或者数组是否存在元素
            // 2.输出当前节点
            // 3.当前节点的左子节点不为空(左子节点下表没有越界), 左递归前序遍历
            // 4. 当前节点的右子节点不为空(右子节点下表没有越界), 右递归前序遍历
            if (arr == null || arr.length == 0) {
                System.out.println("数组中为空,或者没有元素,无法遍历");
                return;
            }
            System.out.println(arr[index]);

            // arr[2*index + 1]
            if (2 * index + 1 < arr.length) {
                preOrder(2 * index + 1);
            }
            // arr[2 * index + 2]
            if (2 * index + 2 < arr.length) {
                preOrder(2 * index + 2);
            }
        }
    }
}
