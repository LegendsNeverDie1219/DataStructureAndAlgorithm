package com.atguigu.leetcode.ChapterTwo.binarytree;

import com.atguigu.leetcode.ChapterTwo.TreeNode;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/2 10:17
 */
public class BinaryTreeTest2 {
    /**
     * 先来道简单的，这是力扣第 654 题，题目如下：
     * 给定一个不重复的整数数组nums 。最大二叉树可以用下面的算法从nums 递归地构建:
     * <p>
     * 创建一个根节点，其值为nums 中的最大值。
     * 递归地在最大值左边的子数组前缀上构建左子树。
     * 递归地在最大值 右边 的子数组后缀上构建右子树。
     * 返回nums 构建的 最大二叉树 。
     * <p>
     * 输入：nums = [3,2,1,6,0,5]
     * 输出：[6,3,5,null,2,0,null,null,1]
     * 解释：递归调用如下所示：
     * - [3,2,1,6,0,5] 中的最大值是 6 ，左边部分是 [3,2,1] ，右边部分是 [0,5] 。
     * - [3,2,1] 中的最大值是 3 ，左边部分是 [] ，右边部分是 [2,1] 。
     * - 空数组，无子节点。
     * - [2,1] 中的最大值是 2 ，左边部分是 [] ，右边部分是 [1] 。
     * - 空数组，无子节点。
     * - 只有一个元素，所以子节点是一个值为 1 的节点。
     * - [0,5] 中的最大值是 5 ，左边部分是 [0] ，右边部分是 [] 。
     * - 只有一个元素，所以子节点是一个值为 0 的节点。
     * - 空数组，无子节点。
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        return buildBinaryTree(nums, left, right);
    }

    public TreeNode buildBinaryTree(int[] nums, int left, int right) {
        // 先分析根节点要做什么,然后套用前序/中序/后序遍历框架
        // 1.从数组中找到最大值rootValue (6), 以及最大值对应的rootIndex(3)
        // 2.构造root节点.
        // 3.给root节点添加左子树root.left= [3,2,1]  即数组左半部分递归构造 最大二叉树
        // 4.给root节点添加右子树root.right = [0,5]  即数组右半部分递归构造 最大二叉树
        if (left > right) {
            return null;
        }
        int rootValue = Integer.MIN_VALUE;
        int rootIndex = -1;
        for (int i = left; i <= right; i++) {
            if (nums[i] > rootValue) {
                rootValue = nums[i];
                rootIndex = i;
            }
        }
        TreeNode treeNode = new TreeNode(rootValue);
        treeNode.left = buildBinaryTree(nums, left, rootIndex - 1);
        treeNode.right = buildBinaryTree(nums, rootIndex + 1, right);
        return treeNode;
    }

    /**
     * 给定两个整数数组preorder 和 inorder，其中preorder 是二叉树的先序遍历，
     * inorder是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     * <p>
     * <p>
     * 输入: preorder = [-1], inorder = [-1]
     * 输出: [-1]
     */
    public TreeNode buildTreeByPreorderAndInorder(int[] preorder, int preStart, int preEnd, int[] inorder,
                                                  int inStart, int inEnd) {
        //buildTreeByInorderAndPreOrder
        // 先找到根节点的value,index(在前序中序的位置), 然后分析根节点要做什么,接着套用前序/中序/后序遍历框架
        // 1. 根据根节点的value 构造一个rootNode
        // 2.给rootNode 添加左子树  即利用preorder数组的某一个范围 inorder数组的某一范围,递归构建左子树
        // 3.给rootNode 添加右子树  即利用preorder数组的某一个范围 inorder数组的某一范围,递归构建右子树
        if (preStart > preEnd) {
            return null;
        }
        int rootValue = preorder[preStart];

        int rootNodeIndex = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (rootValue == inorder[i]) {
                rootNodeIndex = i;
                break;
            }
        }
        TreeNode rootNode = new TreeNode(rootValue);
        int leftSize = rootNodeIndex - inStart;


        rootNode.left = buildTreeByPreorderAndInorder(preorder, preStart + 1, preStart + leftSize,
                inorder, inStart, rootNodeIndex - 1);
        rootNode.right = buildTreeByPreorderAndInorder(preorder, preStart + leftSize + 1, preEnd,
                inorder, rootNodeIndex + 1, inEnd);
        return rootNode;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // preStart,preEnd 控制preOrder的起始位置, inOrderStart,inOrderEnd 控制inOrder的起始位置.
        return buildTreeByPreorderAndInorder(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }


    /**
     * 给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历，请你构造并返回这颗二叉树。
     * <p>
     * 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
     * 输出：[3,9,20,null,null,15,7]
     * <p>
     * 输入：inorder = [-1], postorder = [-1]
     * 输出：[-1]
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        //buildTreeByInorderAndPostOrder
        return buildTreeByInorderAndPostOrder(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTreeByInorderAndPostOrder(int[] inorder, int inOrderStart, int inOrderEnd,
                                                    int[] postorder, int postOrderStart, int postOrderEnd) {
        if (inOrderStart > inOrderEnd) {
            return null;
        }

        int rootValue = postorder[postOrderEnd];
        int rootNodeIndex = -1;
        for (int i = inOrderStart; i <= inOrderEnd; i++) {
            if (inorder[i] == rootValue) {
                rootNodeIndex = i;
                break;
            }
        }
        TreeNode rootNode = new TreeNode(rootValue);
        // 左子树节点的个数.
        int leftSize = rootNodeIndex - inOrderStart;

        rootNode.left = buildTreeByInorderAndPostOrder(inorder, inOrderStart, rootNodeIndex - 1, postorder,
                postOrderStart, postOrderStart + leftSize - 1);
        rootNode.right = buildTreeByInorderAndPostOrder(inorder, rootNodeIndex + 1, inOrderEnd, postorder,
                postOrderStart + leftSize, postOrderEnd - 1);
        return rootNode;
    }
}


