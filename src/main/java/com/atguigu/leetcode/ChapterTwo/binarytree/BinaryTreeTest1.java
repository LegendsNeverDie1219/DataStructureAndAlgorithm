package binarytree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/27 6:40
 */
public class BinaryTreeTest1 {
    @Test
    public void testQuickSort() {
        // 快速排序
        /**
         * 快速排序核心思路:
         * 如果要对arr[low,high] 进行排序
         * 则首先从数组中随便选中一个元素pivot作为分界点
         * 然后通过交换元素
         * 使得 arr[left,low-1] 的元素 都小于pivot
         * arr[low+1, right]的元素 都大于 pivot
         * 接着 递归的去 arr[left,pivot-1] 去找新的分界点,再次进行排序
         *  递归的去arr[pivot+1,right] 去找新的分界点, 再次进行排序.
         */
        int[] arr = {-9, 78, 0, 23, -567, 70, -1, 900, 4561};
        System.out.println("排序之前");
        System.out.println(Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序之后");
        System.out.println(Arrays.toString(arr));
    }

    private void quickSort(int[] arr, int leftBorder, int rightBorder) {
        if (leftBorder >= rightBorder) {
            return;
        }
        int left = leftBorder;
        int right = rightBorder;
        int pivot = arr[leftBorder];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            // right指向的元素 < pivot, 将right指向元素移入坑中
            if (left < right) {
                arr[left] = arr[right];
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            // left指向的元素 > pivot, 将left指向元素移入坑中
            if (left < right) {
                arr[right] = arr[left];
            }
            if (left == right) {
                arr[left] = pivot;
            }
        }
        quickSort(arr, leftBorder, left - 1);
        quickSort(arr, left + 1, rightBorder);
    }

    @Test
    public void testMergeSort() {
        /**
         * 归并排序核心思路:
         * 先将数组一分为二
         * 然后将左半部分arr[left,middle-1]排序,其次将右半部分arr[middle+1,right]排序.
         * 最后将左半部分和右半部分合并.
         */
        int[] arr = {-9, 78, 0, 23, -567, 70, -1, 900, 4561};
        System.out.println("排序之前");
        System.out.println(Arrays.toString(arr));

        mergeSort(arr, 0, arr.length - 1, new int[arr.length]);
        System.out.println("排序之后");
        System.out.println(Arrays.toString(arr));
    }

    private void mergeSort(int[] arr, int leftBorder, int rightBorder, int[] tempArr) {
        // 只有一个元素,无需排序.
        if (leftBorder >= rightBorder) {
            return;
        }
        int middle = leftBorder + (rightBorder - leftBorder) / 2;
        mergeSort(arr, leftBorder, middle, tempArr);
        mergeSort(arr, middle + 1, rightBorder, tempArr);
        mergeResult(arr, leftBorder, middle, rightBorder, tempArr);
    }

    private void mergeResult(int[] arr, int leftBorder, int middle, int rightBorder, int[] tempArr) {
        // left 指针在 arr[leftBorder,middle]  游走
        int left = leftBorder;
        // right 指针在 arr[middle+1,rightBorder] 游走
        int right = middle + 1;
        // temp指针 在tempArr游走
        int temp = 0;
        while (left <= middle && right <= rightBorder) {
            if (arr[left] < arr[right]) {
                tempArr[temp] = arr[left];
                temp++;
                left++;
            } else {
                tempArr[temp] = arr[right];
                temp++;
                right++;
            }
        }
        // 左半部分还有剩余元素
        if (left <= middle) {
            while (left <= middle) {
                tempArr[temp] = arr[left];
                temp++;
                left++;
            }
        } else {
            while (right <= rightBorder) {
                tempArr[temp] = arr[right];
                temp++;
                right++;
            }
        }

        // 把tempArr中合并的元素 装入到arr中
        temp = 0;
        int index = leftBorder;
        while (index <= rightBorder) {
            arr[index] = tempArr[temp];
            index++;
            temp++;
        }
    }

    /**
     * 我们先从简单的题开始，看看力扣第 226 题「翻转二叉树」，
     * 输入一个二叉树根节点root，让你把整棵树镜像翻转，比如输入的二叉树如下：
     * <p>
     * 函数定义: 反转以root 为根的二叉树.
     */
    TreeNode invertTree(TreeNode root) {
        // 将反转整棵树的行为 细化到每一个节点的动作
        // 只要把每一个节点左子节点 右子节点 交换位置, 最后的结果就是反转了整课二叉树.
        if (root == null) {
            return null;
        }
        // 反转root节点 本身.
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // 反转root节点的左子树
        invertTree(root.left);
        // 反转root节点的右子树
        invertTree(root.right);
        return root;
    }

    /**
     * 填充二叉树节点的右侧指针
     * 给定一个完美二叉树, 其所有的叶子结点都在同一层,每个父节点都有2个叶子节点.
     * 二叉树定义如下.
     * 填充它的每个next指针,让这个指针指向下一个右侧节点.
     * 如果找不到下一个右侧节点.则将next指针设置为null.
     */
    TreeNode connect(TreeNode root) {
        /**
         * 题目的意思就是让我们 把二叉树的每一层都用next指针串起来.
         * 本题目无法细化到每一个节点.
         * 所以需要细化成每两个相邻的 节点.
         */
        if (root == null) {
            return null;
        }
        connectTwoNeighborNode(root.left, root.right);
        return root;
    }

    // 函数定义: 将每一层的二叉树节点 用next指针连接起来.
    // 本题目无法细化到每一个节点. 所以需要细化成每两个相邻的 节点.
    // 1.将这两个节点连接起来.
    // 2.将节点1,节点2 左右子节点 分别连接起来
    // 3.将节点1的右子节点 节点2的左子节点连接起来.
    private void connectTwoNeighborNode(TreeNode nodeOne, TreeNode nodeTwo) {
        if (nodeOne == null || nodeTwo == null) {
            return;
        }
        // 细化成 每两个相邻的节点 的动作.
        // 1.将这两个节点连接起来.
        nodeOne.next = nodeTwo;

        // 2.连接相同父节点的两个子节点
        connectTwoNeighborNode(nodeOne.left, nodeOne.right);
        connectTwoNeighborNode(nodeTwo.left, nodeTwo.right);

        //3.连接跨父节点的两个子节点.
        connectTwoNeighborNode(nodeOne.right, nodeTwo.left);
    }

    /**
     * 将二叉树展开为链表
     * 给定一棵二叉树,将他转换为一个单链表
     */
    // 函数定义: 将以 root 为根 的二叉树 拉平成一条单链表.
    // 步骤:
    // 1.将root的左子树,和右子树拉平
    // 2. 将整棵左子树 作为 右子树.
    // 3.将原来右子树挂到新右子树叶子结点 下方
    void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);


        //递归框架是后序遍历，因为我们要先拉平左右子树才能进行后续操作。
        TreeNode tempLeft = root.left;
        TreeNode tempRight = root.right;

        // 将左子树作为右子树(原来的左子树置为null)
        root.left = null;
        root.right = tempLeft;

        // 将原来的右子树挂到新的右子树的叶子结点上
        //找到新右子树的叶子节点
        TreeNode temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }

        temp.right = tempRight;
    }

    private static class TreeNode {
        public String value;
        public TreeNode left;
        public TreeNode right;
        public TreeNode next;
    }
}
