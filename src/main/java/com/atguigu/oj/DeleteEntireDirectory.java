package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/1 16:07
 */
public class DeleteEntireDirectory {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        int lineNumber = Integer.parseInt(scanner.nextLine());
        String[] dirTreeLines = new String[lineNumber];
        for (int i = 0; i < lineNumber; i++) {
            dirTreeLines[i] = scanner.nextLine();
        }
        scanner.close();
        String delDirs = delAllDirectory(dirTreeLines);
        System.out.println(delDirs);
    }

    /**
     * 根据字符串目录行数,删除所有的目录.
     *
     * @param dirTreeLines 目录字符串 数组
     * @return 删除目录的顺序的字符串.
     */
    private static String delAllDirectory(String[] dirTreeLines) {
        // 1.获取目录的根节点.
        int rootIndex = 0;
        for (int index = 0; index < dirTreeLines.length; index++) {
            if (!dirTreeLines[index].contains("|-")) {
                // 则该字符串目录就是根节点.记录下来
                rootIndex = index;
                break;
            }
        }
        /**
         * 10
         * <p>
         * |-B          非顶层目录.并且它前面没有对应过的父目录, 该行输入被忽略.
         * A            A为顶层目录
         * |-B          B为第2层目录,它紧跟的上一层目录为A,因此B为A的子目录.
         * |-|-C        C为第三层,他紧跟的上一层目录为B,因此其父目录为B.
         * |-|-D        D为第三层,他紧跟的上一层目录为B,因此其父目录为B.
         * |-|-D        同名,被忽略
         * |-|-|-|-D    D为第5层,前面没有第4层目录,因此无对应的父目录,忽略.
         * |-|-E        E为第三层,他紧跟的上一层目录为B,因此其父目录为B.
         * |-|-|-F      F为第四层,他紧跟的上一层目录为E,因此其父目录为E.
         * |-lib32      lib32为第2层目录,它紧跟的上一层目录为A,因此B为A的子目录.
         * 输出样例
         * C D F E B lib32 A
         * <p>
         * 删除时, A有子目录 B lib32 先删除库B. 其子目录C D E 需要先被删除. C.D无子目录可以直接删除. E 有子目录F 先删除F.
         * C  D F E B lib32 A
         */
        DirectoryNode rootNode = new DirectoryNode(dirTreeLines[rootIndex], 0);
        DirectoryNode lastNode = rootNode;
        // 2.根据根节点构建多叉树.
        for (int index = rootIndex + 1; index < dirTreeLines.length; index++) {
            String currentNodeStr = dirTreeLines[index];
            // |-B
            String[] nodeStrArr = currentNodeStr.split("-");
            // B
            String currentNodeValue = nodeStrArr[nodeStrArr.length - 1];
            // 1
            int currentNodeDepth = nodeStrArr.length - 1;
            int lastNodeDepth = lastNode.depth;
            // 当前节点深度和上一个节点的深度 之差 不能超过1  例如: |-|-D  和|-|-|-|-D   4-2 >1 不合法.
            if (currentNodeDepth - lastNodeDepth <= 1) {
                // 上一个节点指针后移
                lastNode = buildNodeTree(lastNode, currentNodeValue, currentNodeDepth,
                        currentNodeDepth - lastNodeDepth);
            }
        }


        List<String> resultStrList = new ArrayList<>();
        // 3. 多叉树后序位置递归操作
        addNodeValueToResultList(rootNode, resultStrList);
        // 4. 返回结果.
        return String.join(" ", resultStrList);
    }

    private static void addNodeValueToResultList(DirectoryNode rootNode, List<String> resultStrList) {
        if (rootNode == null) {
            return;
        }
        // 对应该节点来说,要先添加子目录的value. 再添加自己节点的value
        List<DirectoryNode> subDirectoryNodes = rootNode.subDirectoryNodes;
        if (subDirectoryNodes != null && !subDirectoryNodes.isEmpty()) {
            // todo  多叉树的递归遍历
            for (DirectoryNode directoryNode : subDirectoryNodes) {
                addNodeValueToResultList(directoryNode, resultStrList);
            }
        }
        resultStrList.add(rootNode.value);
    }

    /**
     * @param lastNode     上一个节点
     * @param curNodeValue 当前节点的value
     * @param curNodeDepth 当前节点的深度
     * @param scene        场景
     * @return 当前节点.
     */
    private static DirectoryNode buildNodeTree(DirectoryNode lastNode, String curNodeValue, int curNodeDepth,
                                               int scene) {
        DirectoryNode currentNode = new DirectoryNode(curNodeValue, curNodeDepth);
        if (scene == 1) {
            // 上一个节点和当前节点是父子关系   // 即 |-B  |-|-C
            currentNode.parent = lastNode;
            List<DirectoryNode> directoryNodes = new ArrayList<>();
            directoryNodes.add(currentNode);
            lastNode.subDirectoryNodes = directoryNodes;
            return currentNode;
        }

        if (scene == 0) {
            // 上一个节点 和当前节点是兄弟关系  |-|-C,  |-|-D
            DirectoryNode parentNode = lastNode.parent;
            addNode(parentNode, currentNode);
            return currentNode;
        }

        if (scene < 0) {
            // currentNode:|-lib32  1  lastNode ;|-|-|-F  3
            int lastNodeDepth = lastNode.depth;
            while (curNodeDepth < lastNodeDepth) {
                lastNode = lastNode.parent;
                lastNodeDepth--;
            }
            addNode(lastNode.parent, currentNode);
        }
        return currentNode;
    }

    private static void addNode(DirectoryNode parentNode, DirectoryNode currentNode) {
        currentNode.parent = parentNode;

        List<DirectoryNode> subDirectoryNodes = parentNode.subDirectoryNodes;
        // 其中 |-|-D  ,|-|-D 这种情况要处理
        List<DirectoryNode> sameNameNodes =
                subDirectoryNodes.stream().filter(Objects::nonNull)
                        .filter(item -> item.value.equals(currentNode.value)).collect(Collectors.toList());
        if (sameNameNodes.size() == 0) {
            subDirectoryNodes.add(currentNode);
        }
    }

    static class DirectoryNode {
        DirectoryNode parent;
        String value;
        int depth;
        List<DirectoryNode> subDirectoryNodes;

        public DirectoryNode(String value, int depth) {
            this.value = value;
            this.depth = depth;
        }
    }
}
