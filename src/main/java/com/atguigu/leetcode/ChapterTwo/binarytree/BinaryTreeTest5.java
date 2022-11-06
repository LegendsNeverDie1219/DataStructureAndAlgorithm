package com.atguigu.leetcode.ChapterTwo.binarytree;

import com.atguigu.leetcode.ChapterOne.linkedlistdoublepointer.ListNode;
import com.atguigu.leetcode.ChapterOne.linkedlistdoublepointer.ListNodeUtils;
import com.atguigu.leetcode.ChapterTwo.TreeNode;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/2 17:10
 */
public class BinaryTreeTest5 {

    private int maxDepth = 0;
    private int depth = 0;

    //迭代遍历数组
    void traverseArrayByIterator(int[] arr) {
        for (int i = 0; i < arr.length; i++) {

        }
    }

    @Test
    public void testTraverseArrayByRecursive() {
        int[] arr = new int[]{1, 2, 3, 4};
        traverseArrayByRecursive(arr, 0);
    }

    // 递归遍历数组
    void traverseArrayByRecursive(int[] arr, int index) {
        if (index == arr.length) {
            return;
        }
        //前序位置
        //System.out.println(arr[index]);
        traverseArrayByRecursive(arr, index + 1);
        // 后续位置.
        System.out.println(arr[index]);
    }

    // 迭代遍历链表
    void traverseListNodeByIterator(ListNode head) {
        // for循环
        for (ListNode temp = head; temp != null; temp = temp.next) {

        }
        // while 循环
        ListNode temp = head;
        while (temp != null) {
            temp = temp.next;
        }
    }

    @Test
    public void testTraverseListNodeByRecursive() {
        ListNode listNode = ListNodeUtils.generateListNodes2(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        traverseListNodeByRecursive(listNode);
    }

    // 递归遍历链表
    void traverseListNodeByRecursive(ListNode head) {
        if (head == null) {
            return;
        }
        // 前序位置
        // System.out.println(head);
        traverseListNodeByRecursive(head.next);
        // 后续位置
        System.out.println(head);
    }

    /**
     * 手把手刷二叉树系列完结篇
     * https://mp.weixin.qq.com/s/AzQzw-pC8A-0kS0NJn2eWw
     * ，二叉树这种结构无非就是二叉链表，不过没办法简单改写成迭代形式，所以一般说二叉树的遍历框架都是指递归的形式。
     * 前序遍历,中序遍历, 后续遍历是遍历二叉树节点 的三个不同的时间点.
     * 前序位置的代码: 在刚进入  当前节点的时候,  执行的代码
     * 中序位置的代码: 在一个二叉树节点的左子树都遍历完, 才会执行的代码
     * 后续位置的代码: 在即将离开  当前节点的时候, 执行的代码.
     */
    // 递归遍历二叉树
    void traverseBinaryTreeByRecursive(TreeNode root) {
        if (root == null) {
            return;
        }
        // 前序位置
        traverseBinaryTreeByRecursive(root.left);
        // 中序位置
        traverseBinaryTreeByRecursive(root.right);
        // 后序位置
    }

    /**
     * 获取某一个二叉树的最大深度
     */
    @Test
    public void testMaxDepth() {
        String str = "3,9,null,null,20,15,null,null,7,null,null";
        //           3,9,null,null,20,15,null,null,7,null,null,
        TreeNode rootNode = BinaryTreeSerializeUtils.deserializeByPreOrder(str);
        System.out.println(rootNode);

        int depth = maxDepthByTraverse(rootNode);
        System.out.println("depth: " + depth);
        //
        // int depth2 = maxDepthByDecomposition(rootNode);
        // System.out.println("depth2: " + depth2);
    }

    // 通过分解的方式
    // 定义：输入根节点，返回这棵二叉树的最大深度
    private int maxDepthByDecomposition(TreeNode rootNode) {
        if (rootNode == null) {
            return 0;
        }
        // 利用定义，计算该节点左子树的最大深度
        int leftTreeMaxDepth = maxDepthByDecomposition(rootNode.left);
        // 计算出该节点右子树的最大深度
        int rightTreeMaxDepth = maxDepthByDecomposition(rootNode.right);


        // 该节点的深度
        return Math.max(leftTreeMaxDepth, rightTreeMaxDepth) + 1;
    }

    // 通过遍历的方式
    private int maxDepthByTraverse(TreeNode rootNode) {

        traverseTreeGetMaxDepth(rootNode);
        return maxDepth;

    }

    private void traverseTreeGetMaxDepth(TreeNode rootNode) {
        if (rootNode == null) {
            // 到达叶子节点，更新最大深度
            maxDepth = Math.max(maxDepth, depth);
            return;
        }
        // 前序位置 ;刚进入该节点执行的代码, 即层数加1
        depth++;
        traverseTreeGetMaxDepth(rootNode.left);
        traverseTreeGetMaxDepth(rootNode.right);
        // 后续位置: 即 将要离开该节点执行的代码. 即 将要回退到二叉树的上一层
        depth--;
    }

    /**
     * 后续位置:
     * 前序位置的代码的执行是自顶而上的,后续位置的代码 执行时自底而上的
     * 这意味着: 前序位置的代码只能从函数的入参中获取数据
     * 而后续位置的代码不仅可以获取函数入参的数据, 还可以获取底层子树 通过函数返回值 返回的数据.
     * <p>
     * 、如果把根节点看做第 1 层，如何打印出每一个节点所在的层数？
     */
    public void printTreeNodeLevel(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        System.out.printf("节点 %s 在 %d 层", root, level);
        System.out.println();
        printTreeNodeLevel(root.left, level + 1);
        printTreeNodeLevel(root.right, level + 1);
    }

    @Test
    public void testPrintTreeNodeLevel() {
        TreeNode rootNode = BinaryTreeSerializeUtils.deserializeByPreOrder(new String("1,2,3,null,null,4,null,null,5," +
                "6,7,null,null,null"));
        printTreeNodeLevel(rootNode, 1);
    }

    /**
     * 、如何打印出每个节点的左右子树各有多少节点？
     * 函数定义: 获取每一个节点的 左右子节点数量之和.
     */
    public int getOneNodeOfNodeNumberSum(TreeNode rootNode) {
        if (rootNode == null) {
            return 0;
        }
        int leftSubTreeNodeNumber = getOneNodeOfNodeNumberSum(rootNode.left);
        int rightSubTreeNodeNumber = getOneNodeOfNodeNumberSum(rootNode.right);

        System.out.printf("%s 节点 的左子树有 %d个节点, 右子树有 %d个节点.", rootNode, leftSubTreeNodeNumber, rightSubTreeNodeNumber);
        System.out.println();

        return leftSubTreeNodeNumber + rightSubTreeNodeNumber + 1;
    }

    @Test
    public void testGetOneNodeOfNodeNumberSum() {
        List<String> strList = Lists.newArrayList("null", "null", "3", "null", "null", "4",
                "2", "null", "null", "7", "null", "6", "null", "5", "1");
        String str = String.join(",", strList);
        TreeNode rootNode = BinaryTreeSerializeUtils.deserializeByPostOrder(str);
        int oneNodeOfNodeNumberSum = getOneNodeOfNodeNumberSum(rootNode);
        System.out.println(oneNodeOfNodeNumberSum);
    }

    /**
     * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
     * <p>
     * 示例 :
     * 给定二叉树
     * <p>
     * 1
     * / \
     * 2   3
     * / \
     * 4   5
     * 返回3, 它的长度是路径 [4,2,1,3] 或者[5,2,1,3]。
     * <p>
     * 注意：两结点之间的路径长度是以它们之间边的数目表示。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    private int thisTreeDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        maxDepthByDecomposition2(root);
        System.out.println(thisTreeDiameter);
        return thisTreeDiameter;
    }


    private int maxDepthByDecomposition2(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftTreeMaxDepth = maxDepthByDecomposition2(node.left);
        int rightTreeMaxDepth = maxDepthByDecomposition2(node.right);

        int curNodeAsRootDiameter = leftTreeMaxDepth + rightTreeMaxDepth;

        thisTreeDiameter = Math.max(curNodeAsRootDiameter,thisTreeDiameter);

        return Math.max(leftTreeMaxDepth, rightTreeMaxDepth) + 1;
    }


    public static void main(String[] args) {
        List<String> list = Arrays.asList("1","2","3","4");
        String result= list.stream().collect(Collectors.joining());
        List<String> collect = list.stream().collect(Collectors.toList());

        System.out.println(result);
    }


}
