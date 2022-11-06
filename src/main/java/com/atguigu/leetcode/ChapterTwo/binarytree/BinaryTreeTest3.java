package com.atguigu.leetcode.ChapterTwo.binarytree;

import com.atguigu.leetcode.ChapterTwo.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/2 11:56
 */
public class BinaryTreeTest3 {
    /**
     * 652. 寻找重复的子树
     * 给定一棵二叉树 root，返回所有重复的子树。
     * <p>
     * 对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
     * <p>
     * 如果两棵树具有相同的结构和相同的结点值，则它们是重复的。
     */
    List<TreeNode> resultList = new ArrayList<>();
    HashMap<String, Integer> memory = new HashMap<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        traverse(root);
        return resultList;
    }

    private String traverse(TreeNode root) {
        // 1.先要知道自己某一个节点以及子树长什么样子
        // 即需要先递归遍历序列化自己的左子树, 再递归遍历序列化自己的右子树, 左子树 + 右子树 + 自己节点值 = 自己以及子树的模样.
        // 2. 还要知道其他节点及其子树长什么样子.
        // 即需要一个外部数据结构充当备忘录. HashMap<String,Integer> key存储每个节点的序列化后的字符串,value 存储出现的次数
        // 只需要把出现次数> 1的 key 加入到List<String> resultList中. 并且只需重复的只需要加入一次.
        if (root == null) {
            return "#";
        }
        String leftSubTreeStr = traverse(root.left);
        String rightSubTreeStr = traverse(root.right);

        String currNodeStr = leftSubTreeStr + "," + rightSubTreeStr + "," + root.val;

        Integer frequency = memory.getOrDefault(currNodeStr, 0);

        // currNodeStr 第二次出现.
        if (frequency == 1) {
            resultList.add(root);
        }
        memory.put(currNodeStr, frequency + 1);
        return currNodeStr;
    }

}
