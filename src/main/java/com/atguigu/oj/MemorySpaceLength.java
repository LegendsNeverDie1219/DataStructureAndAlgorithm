package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/8 20:30
 */
public class MemorySpaceLength {
    /**
     * 输入:
     * 3
     * 2 4
     * 3 7
     * 4 6
     * 输出:
     * 5
     * 提示样例:
     * [2,4) 和[3,7) 合并为[2,7), [2,7) 和[4,6) 合并为[2,7) 总长度为5
     * <p>
     * 输入:
     * 3
     * 3 7
     * 2 4
     * 10 30
     * 输出样例:
     * 25
     * [2,7) 和[10,30) 不重合, 总长度为 7-2 + 30-10 = 25
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();

        // todo 对区间进行预处理: 按照区间的起点进行升序排序.
        Map<Integer, Integer> intervalMap = new TreeMap<>();
        for (int i = 0; i < row; i++) {
            int key = scanner.nextInt();
            int value = scanner.nextInt();
            // 区间的起点相同, 但已有区间的end > 新区间的end.  跳过.
            if (intervalMap.containsKey(key) && intervalMap.get(key) >= value) {
                continue;
            }
            intervalMap.put(key, value);
        }

        scanner.close();
        int result = getValidLength(intervalMap);
        System.out.println(result);
    }

    /**
     * 获取有效的长度
     *
     * @param intervalMap 区间的map
     * @return 有效长度.
     */
    private static int getValidLength(Map<Integer, Integer> intervalMap) {
        Map.Entry<Integer, Integer> lastIntervalEntry = null;
        Iterator<Map.Entry<Integer, Integer>> entryIterator = intervalMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = entryIterator.next();
            Integer intervalStart = entry.getKey();
            // 如果当前区间的start > 上一个区间的end. 则不存在区间重叠,不需要合并
            if (Objects.isNull(lastIntervalEntry) || intervalStart .compareTo(lastIntervalEntry.getValue()) > 0 ) {
                // 不需要合并,更新lastIntervalEnd
                lastIntervalEntry = entry;
                continue;
            }
            //当前区间的start <= 上一个区间的end.
            int maxValue = Math.max(lastIntervalEntry.getValue(), entry.getValue());
            lastIntervalEntry.setValue(maxValue);

            entryIterator.remove();
        }



        int length = 0;
        Set<Map.Entry<Integer, Integer>> filteredSet = intervalMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : filteredSet) {
            Integer start = entry.getKey();
            Integer end = entry.getValue();
            length += (end - start);
        }
        return length;
    }
}
