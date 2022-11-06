package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/22 9:25
 */
public class PointofSaleDistibuutionSurvey {
    /**
     * 输入
     * 23 销售负责人总数
     * 13  有下属的负责人的人数
     * 21 1 23 每行给出一个负责人, 格式为 负责人的ID,下属人数,下属的IDs
     * 01 4 03 02 04 05
     * 03 3 06 07 08
     * 06 2 12 13
     * 13 1 21
     * 08 2 15 16
     * 02 2 09 10
     * 11 2 19 20
     * 17 1 22
     * 05 1 11
     * 07 1 14
     * 09 1 17
     * 10 1 18
     * 01  待统计的负责人的ID
     * <p>
     * 输出:
     * [4,9] 指定负责人名下人数最多的层级, 以及该层级的人数.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int totalSales = cin.nextInt();
        int supNum = cin.nextInt();
        Map<Integer, List<Integer>> relationList = new HashMap<>();
        for (int i = 0; i < supNum; i++) {
            int sup = cin.nextInt();
            int subCnt = cin.nextInt();
            List<Integer> subIds = new ArrayList<>(subCnt);
            for (int j = 0; j < subCnt; j++) {
                subIds.add(cin.nextInt());// 下属
            }
            relationList.put(sup, subIds);
        }

        int appointSale = cin.nextInt();
        cin.close();
        int[] results = pointSaleSurvery(totalSales, relationList, appointSale);
        String[] strResult = Arrays.stream(results).mapToObj(String::valueOf).toArray(String[]::new);
        System.out.println("[" + String.join(" ", strResult) + "]");
    }

    private static int[] pointSaleSurvery(int totalSales, Map<Integer, List<Integer>> relationList, int appointSale) {
        // 思路: 多叉树的广度优先遍历(不需要设置visited): 统计当前遍历时所处的层数 以及当前层的节点总数.
        Queue<Integer> queue = new LinkedList<>();
        queue.add(appointSale);
        // 当前节点所处的层就是第一层
        int layer = 1;
        int maxNodeNum = 0;
        int maxNodeNumLayer = 1;
        while (!queue.isEmpty()) {
            int currentLayerNodeNum = queue.size();
            if (currentLayerNodeNum > maxNodeNum) {
                maxNodeNum = currentLayerNodeNum;
                maxNodeNumLayer = layer;
            }
            for (int i = 0; i < currentLayerNodeNum; i++) {
                Integer currentNode = queue.poll();
                if (relationList.containsKey(currentNode)) {
                    List<Integer> neighborNodes = relationList.get(currentNode);
                    neighborNodes.forEach(queue::offer);
                }
            }
            layer++;
        }
        return new int[]{maxNodeNumLayer, maxNodeNum};
    }
}
