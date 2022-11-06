package com.atguigu.leetcode.ChapterTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/1 21:58
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public List<TreeNode> neighborNodes = new ArrayList<>();

    public TreeNode() {
    }

    public TreeNode (int val) {
        this.val =val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }
}
