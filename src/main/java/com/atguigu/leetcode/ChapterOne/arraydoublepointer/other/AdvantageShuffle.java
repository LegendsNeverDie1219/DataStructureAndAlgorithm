package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 优势洗牌
 *
 * @author Administrator
 * @date 2022/4/3 15:54
 */
public class AdvantageShuffle {
    /**
     * 给定两个⼤⼩相等的数组 A 和 B，A 相对于 B 的优势可以⽤满⾜ A[i] > B[i] 的索引 i 的数⽬来描述。
     * 请你返回 A 的任意排列，使其相对于 B 的优势最⼤化。
     * 示例 1：
     * 输⼊：A = [2,7,11,15], B = [1,10,4,11]
     * 输出：[2,11,7,15]
     */
    /**
     * 田忌赛马
     *
     * @param tianJiHorses 田忌的马匹以及对应的战斗力
     * @param qiWangHorses 齐王的马匹以及对应的战斗力
     * @return 最优的上场次序.
     * 思路:
     * 1.首先是给田忌和齐王的马匹进行排序
     * 2.然后按照战斗力从高到底的顺序依次比较,如果 田忌的可以马匹可以赢,则直接上
     * 否则换一个最差的马来充数,保存实力.
     * 其中在实现的时候不能直接对齐王的马进行排序.要借助优先队列PriorityQueue来进行排序.
     * 在创建一个优先队列的时候,可以指定元素的排列顺序
     * 之后,如果要往队列中添加元素,并不是添加到队列的尾部,而是按照指定的排序,插入到合适的位置
     * 常用方法:
     *  offer 添加元素
     *  poll 移除队列头部元素
     *  peek 查看队列头部元素
     */
    public int[] advantageShuffle(int[] tianJiHorses, int[] qiWangHorses) {
        // 将齐王的马按照战斗力降序排序
        PriorityQueue<int[]> qiWangPriorityQueue = new PriorityQueue<>(qiWangHorses.length, new Comparator<int[]>() {
            @Override
            public int compare(int[] itemOne, int[] itemTwo) {
                return itemTwo[1] - itemOne[1];
            }
        });
        
        for (int i = 0; i < qiWangHorses.length; i++) {
            qiWangPriorityQueue.offer(new int[] {i,qiWangHorses[i]});
        }
        // 将田忌的马按照战斗力进行升序排序 
        Arrays.sort(tianJiHorses);
        // left 指向田忌最垃圾的马
        int left = 0; 
        // right 指向田忌最好的马
        int right = tianJiHorses.length-1;
        int[] resArr =new int[tianJiHorses.length];
        while (!qiWangPriorityQueue.isEmpty()) {
            int[] bestHorseArr = qiWangPriorityQueue.poll();
            int bestHorseNo = bestHorseArr[0];
            int bestHorsePower = bestHorseArr[1];
            if (tianJiHorses[right] > bestHorsePower) {
                // 如果田忌剩下的最好的马 能胜过 齐王剩下的最好的马，那就⾃⼰上
                resArr[bestHorseNo] = tianJiHorses[right];
                right--;
            } else {
                // 否则,用最垃圾的马顶替一下.
                resArr[bestHorseNo] = tianJiHorses[left];
                left++;
            }
        }
        return resArr;
    }

    @Test
    public void test() {
        int[] arrOne = new int[] {7,2,15,11};
        int[] arrTwo = new int[] {1,10,4,11};
        System.out.println(Arrays.toString(advantageShuffle(arrOne, arrTwo)));
    }
}
