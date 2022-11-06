package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/16 22:13
 */
public class PrimeTrunkCode {
    /**
     * 行李箱解锁
     * 某行李箱支持4位数字密码,每位数字在0和9之间,
     * 开锁方式:
     * 每次操作改变其中一位数字,(注意是改变, 比如0023 改变第4位后,为0029)
     * 每次操作后的数字必须始终是素数.
     * 现在给定行李箱的初始密码与解锁密码(都是素数) 请找出最快的开锁方式,输出改变次数.如果无法解锁,输出-1.
     * 输入
     * 两个4位数字字符,分别表示初始密码,与解锁密码,以单个空格分隔.
     * 输出:
     * 一个整数,表示开锁的最少改变次数,无法解锁则输出-1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String initState = scanner.next();
        String dstState = scanner.next();
        scanner.close();
        int result = unlock(initState, dstState);
        System.out.println(result);
    }


    private static int unlock(String initState, String dstState) {
        // 思路: 采用BFS找到最短路径/ 最少开锁次数
        Queue<String> queue = new LinkedList<>();
        queue.offer(initState);
        Set<String> visited = new HashSet<>();
        visited.add(initState);
        int step = 0;
        while (!queue.isEmpty()) {
            // 当前层节点总数
            int currentLayerNodeNum = queue.size();
            for (int i = 0; i < currentLayerNodeNum; i++) {
                String currentNode = queue.poll();
                assert currentNode != null;
                if (currentNode.equals(dstState)) {
                    return step;
                }
                List<String> neighbors = findCurrentNodeNeighbors(currentNode);
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor) && isPrime(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private static boolean isPrime(String neighbor) {
        int num = Integer.parseInt(neighbor);
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static List<String> findCurrentNodeNeighbors(String currentNode) {
        List<String> neighbors = new ArrayList<>();
        char[] currentNodeArr = null;
        for (int i = 0; i < 4; i++) {
            currentNodeArr = currentNode.toCharArray();
            for (int j = 0; j < 10; j++) {
                char oneChar = String.valueOf(j).charAt(0);
                if (currentNodeArr[i] != oneChar) {
                    currentNodeArr[i] = oneChar;
                    neighbors.add(String.valueOf(currentNodeArr));
                }
            }
        }
        return neighbors;
    }
}
