package com.atguigu.leetcode.ChapterTwo.binarytree;

import com.atguigu.leetcode.ChapterTwo.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/2 14:08
 */
public class BinaryTreeSerializeUtils {
    public static final String NULL = "null";
    public static final String SEPARATOR = ",";

    @Test
    public void test() {
        String data = "1,2,#,4,#,#,3,#,#,";
        String[] nodes = data.split(",");
        System.out.println(nodes.length);
        for (String node : nodes) {
            System.out.println(node);
        }
    }


    @Test
    public void testSerializeAndDeserialize1() {
        TreeNode root = getRoot();
        String serialize = serializeByPreOrder(root);
        System.out.println(serialize);

        TreeNode deserializeRoot = deserializeByPreOrder(serialize);
        System.out.println(deserializeRoot);
    }

    private static TreeNode getRoot() {
        TreeNode root = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);

        root.left = node2;
        root.right = node3;
        node3.left = node4;
        node3.right = node5;
        return root;
    }

    // 把一棵二叉树序列化成字符串
    public static String serializeByPreOrder(TreeNode root) {
        StringBuilder stringBuilder = new StringBuilder();
        // 所谓序列化就是将二维的二叉树打平, 变成一维的线性字符串结构 考察的是二叉树的遍历方式
        // 方式一: 前序遍历.(把核心逻辑写在两个递归遍历的前面)
        traverse(root, stringBuilder);
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private static void traverse(TreeNode root, StringBuilder stringBuilder) {
        if (root == null) {
            stringBuilder.append(NULL).append(SEPARATOR);
            return;
        }
        stringBuilder.append(root.val).append(SEPARATOR);
        traverse(root.left, stringBuilder);
        traverse(root.right, stringBuilder);
    }

    // 把字符串反序列化成二叉树
    public static TreeNode deserializeByPreOrder(String data) {
        String[] strArr = data.split(SEPARATOR);
        // 把数组中的转化为LinkedList
        LinkedList<String> strQueue = Arrays.stream(strArr).collect(Collectors.toCollection(LinkedList::new));

        return buildBinaryTreeByLinkedList(strQueue);
    }

    private static TreeNode buildBinaryTreeByLinkedList(LinkedList<String> stringLinkedList) {
        // base case
        if (stringLinkedList.isEmpty()) {
            return null;
        }
        String firsElement = stringLinkedList.removeFirst();
        // base case
        if (firsElement.equals(NULL)) {
            return null;
        }
        TreeNode treeNode = new TreeNode(Integer.parseInt(firsElement));


        treeNode.left = buildBinaryTreeByLinkedList(stringLinkedList);
        treeNode.right = buildBinaryTreeByLinkedList(stringLinkedList);
        return treeNode;
    }

    @Test
    public void testSerializeAndDeserialize2() {
        TreeNode root = getRoot();
        String serializeStr = serializeByPostOrder(root);
        System.out.println(serializeStr);

        TreeNode deserializeRoot = deserializeByPostOrder(serializeStr);
        System.out.println(deserializeRoot);
    }

    public static TreeNode deserializeByPostOrder(String str) {
        String[] split = str.split(SEPARATOR);
        LinkedList<String> linkedList = Arrays.stream(split).collect(Collectors.toCollection(LinkedList::new));

        return buildBinaryTreeByLinkedList2(linkedList);
    }

    private static TreeNode buildBinaryTreeByLinkedList2(LinkedList<String> linkedList) {
        if (linkedList.isEmpty()) {
            return null;
        }
        String lastElement = linkedList.removeLast();
        if (lastElement.equals(NULL)) {
            return null;
        }

        TreeNode rootNode = new TreeNode(Integer.parseInt(lastElement));
        rootNode.right = buildBinaryTreeByLinkedList2(linkedList);
        rootNode.left = buildBinaryTreeByLinkedList2(linkedList);

        return rootNode;
    }


    public static String serializeByPostOrder(TreeNode root) {
        // 采用后续位置遍历的方式进行序列化
        StringBuilder stringBuilder = new StringBuilder();
        traverse2(root, stringBuilder);
        return stringBuilder.substring(0, stringBuilder.length() - 1);

    }

    private static void traverse2(TreeNode root, StringBuilder stringBuilder) {
        if (root == null) {
            stringBuilder.append(NULL).append(SEPARATOR);
            return;
        }
        traverse2(root.left, stringBuilder);
        traverse2(root.right, stringBuilder);

        stringBuilder.append(root.val).append(SEPARATOR);
    }
}
