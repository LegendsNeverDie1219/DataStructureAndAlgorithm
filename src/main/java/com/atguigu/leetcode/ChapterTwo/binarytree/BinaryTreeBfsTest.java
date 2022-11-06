package com.atguigu.leetcode.ChapterTwo.binarytree;

import com.atguigu.leetcode.ChapterTwo.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/3 9:20
 */
public class BinaryTreeBfsTest {
    /**
     * 问题的本质就是让你在一幅「图」中找到从起点start到终点target的最近距离，
     * 这个例子听起来很枯燥，但是 BFS 算法问题其实都是在干这个事儿。
     *
     * 这个广义的描述可以有各种变体，比如走迷宫，有的格子是围墙不能走，从起点到终点的最短距离是多少？
     * 如果这个迷宫带「传送门」可以瞬间传送呢？
     */
    /**
     * 广度优先搜索
     *
     * @param startNode 起始位置
     * @param endNode   终点位置
     * @return 最短距离
     */
    int bfs(TreeNode startNode, TreeNode endNode) {
        int step = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(startNode);
        Set<TreeNode> visited = new HashSet<>();
        // 避免走回头路
        visited.add(startNode);


        while (!queue.isEmpty()) {
            int oneLayerSize = queue.size();


            for (int i = 0; i < oneLayerSize; i++) {
                TreeNode currentNode = queue.poll();
                // 当前节点是叶子节点的时候 更新minDistance;
                // if (currentNode.left == null && currentNode.right == null) {
                //     break;
                // }
                // todo 这里判断是否到达终点.
                if (currentNode == endNode) {
                    return step;
                }
                // 将currentNode的相邻节点加入队列中.
                for (TreeNode treeNode : currentNode.neighborNodes) {
                    // 避免走回头路
                    if (!visited.contains(treeNode)) {
                        queue.offer(treeNode);
                    }
                }
            }
            // todo 经过for循环该层的节点已经比较完了.需要进行下一层的比较.
            step++;
        }
        return step;
    }

    /**
     * 给定一棵二叉树 找到他的最小深度
     */
    public int minDepth(TreeNode rootNode) {
        if (rootNode == null) {
            return 0;
        }
        int minDepth = 1;
        // 因为是二叉树, 所以一个节点 只会指向它的左右子节点, 而左右子节点不会指向父节点, 所以不需要添加 Set<TreeNode> visited
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(rootNode);

        while (!queue.isEmpty()) {
            int oneLayerSize = queue.size();
            for (int i = 0; i < oneLayerSize; i++) {
                TreeNode currentNode = queue.poll();
                if (currentNode.left == null && currentNode.right == null) {
                    return minDepth;
                }

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }

                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            minDepth++;
        }
        return minDepth;
    }

    /**
     * 752. 打开转盘锁
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
     * 每个拨轮可以自由旋转：例如把 '9' 变为'0'，'0'
     * 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     * <p>
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     * <p>
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     * <p>
     * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
     * <p>
     * 示例 1:
     * <p>
     * 输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
     * 输出：6
     * 解释：
     * 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
     * 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
     * 因为当拨动到 "0102" 时这个锁就会被锁定。
     * 示例 2:
     * <p>
     * 输入: deadends = ["8888"], target = "0009"
     * 输出：1
     * 解释：把最后一位反向旋转一次即可 "0000" -> "0009"。
     * 示例 3:
     * <p>
     * 输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
     * 输出：-1
     * 解释：无法旋转到目标数字且不被锁定。
     */
    @Test
    public void testOpenLock() {
        String[] deadends = {"0201", "0101", "0102", "1212", "2002"};
        String target = "0202";
        System.out.println(openLock(deadends, target));
    }

    public int openLock(String[] deadends, String target) {
        int openLockNumber = 0;
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        Set<String> visitedSequences = new HashSet<>();
        visitedSequences.add("0000");

        List<String> deadSequences = Arrays.stream(deadends).collect(Collectors.toList());


        while (!queue.isEmpty()) {
            int tryNumber = queue.size();

            for (int i = 0; i < tryNumber; i++) {
                String currentSequence = queue.poll();
                if (target.equals(currentSequence)) {
                    return openLockNumber;
                }
                if (deadSequences.contains(currentSequence)) {
                    continue;
                }

                // 将一个密码序列的 8个相邻序列加入到队列中.
                for (int j = 0; j < 4; j++) {
                    String plusOneStr = calculPlusOneSequence(j, currentSequence);

                    // 防止从回头路.
                    if (!visitedSequences.contains(plusOneStr)) {
                        queue.offer(plusOneStr);
                        visitedSequences.add(plusOneStr);
                    }

                    String minusOneStr = calculMinusOneSequence(j, currentSequence);
                    if (!visitedSequences.contains(minusOneStr)) {
                        queue.offer(minusOneStr);
                        visitedSequences.add(minusOneStr);
                    }
                }
            }
            openLockNumber++;
        }
        return -1;
    }

    private String calculMinusOneSequence(int j, String currentSequence) {
        char[] chars = currentSequence.toCharArray();
        if (chars[j] == '0') {
            chars[j] = '9';
        } else {
            chars[j] -= 1;
        }
        return String.valueOf(chars);
    }

    private String calculPlusOneSequence(int j, String currentSequence) {
        char[] chars = currentSequence.toCharArray();
        if (chars[j] == '9') {
            chars[j] = '0';
        } else {
            chars[j] += 1;
        }
        return String.valueOf(chars);
    }
}
