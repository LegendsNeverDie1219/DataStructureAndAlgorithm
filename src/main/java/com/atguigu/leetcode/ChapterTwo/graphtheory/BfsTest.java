package com.atguigu.leetcode.ChapterTwo.graphtheory;

import com.atguigu.leetcode.ChapterTwo.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/6 21:14
 */
public class BfsTest {
    // 层序遍历这棵二叉树
    void levelTraverseBinaryTree(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;

        while (!queue.isEmpty()) {
            int oneLevelNodeNum = queue.size();

            for (int i = 0; i < oneLevelNodeNum; i++) {
                TreeNode currentNode = queue.poll();
                System.out.printf("当前节点 %s 在二叉树的 第 %d 层 ", currentNode, depth);

                // 因为是二叉树,指针只能从父节点 -> 左右子节点, 所有不会存在走回头路的情况.
                assert currentNode != null;
                // 将当前节点的下一层节点放入队列
                if (currentNode.left != null) {
                    queue.offer(currentNode);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode);
                }
            }
            depth++;
        }
    }

    // 输入一棵n叉树的根节点，层序遍历这棵n叉树
    void levelTraverseNTree(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;

        while (!queue.isEmpty()) {
            int oneLevelNodeNum = queue.size();

            for (int i = 0; i < oneLevelNodeNum; i++) {
                TreeNode currentNode = queue.poll();
                System.out.printf("当前节点 %s 在N叉树的 第 %d 层 ", currentNode, depth);

                // 因为是n叉树,指针只能从父节点 -> 左右子节点, 所有不会存在走回头路的情况.
                assert currentNode != null;
                for (TreeNode neighborNode : currentNode.neighborNodes) {
                    if (neighborNode != null) {
                        queue.offer(neighborNode);
                    }
                }
            }
            depth++;
        }
    }

    // 更普遍广度优先算法 加一个visited数组 防止走回头路.
    void bfs(String[] deadends, String target) {
        int step = 0;
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        Set<String> visitedSequences = new HashSet<>();
        visitedSequences.add("0000");

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String currentSequence = queue.poll();
                System.out.printf("当前序列 %s 是走了 %d 步得到的. ", currentSequence, step);
                String[] neighborSequences = getCurrentSequenceNeighbors(currentSequence);
                for (String neighborSequence : neighborSequences) {
                    // 防止走回头路.
                    if (!visitedSequences.contains(neighborSequence)) {
                        queue.offer(neighborSequence);
                        visitedSequences.add(neighborSequence);
                    }
                }
            }
            step++;
        }
    }

    private String[] getCurrentSequenceNeighbors(String currentSequence) {
        return new String[0];
    }

    // 层序遍历这棵二叉树, 不使用while+for的方式, 则需要新定义一个模型类.
    void levelTraverseBinaryTreeNoForLoop(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<Status> queue = new LinkedList<>();
        queue.offer(new Status(root,1));

        while (!queue.isEmpty()) {
            Status status = queue.poll();
            TreeNode currentNode = status.treeNode;
            int nodeDepth = status.nodeDepth;
            System.out.printf("当前节点 %s 在二叉树的 第 %d 层 ", status.treeNode, nodeDepth);

            // 因为是二叉树,指针只能从父节点 -> 左右子节点, 所有不会存在走回头路的情况.

            if (currentNode.left != null) {
                queue.offer(new Status(currentNode.left,nodeDepth +1));
            }
            if (currentNode.right != null) {
                queue.offer(new Status(currentNode.right,nodeDepth +1));
            }
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Status {
        // 当前节点信息
        private TreeNode treeNode;
        // 当前节点的层数信息.
        private int nodeDepth;
    }
}
