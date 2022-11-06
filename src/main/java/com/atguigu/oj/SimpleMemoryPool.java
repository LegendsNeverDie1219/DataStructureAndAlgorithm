package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/8 21:43
 */
public class SimpleMemoryPool {
    static TreeMap<Integer, Integer> allocateBlock; //<start Address,length>
    static TreeMap<Integer, Integer> pieceBlock; // <start Address,length>
    static int nowFlag; // 当前可用内存的起始地址
    static int totalSize = 100;

    static class AllocatedMemory {
        AllocatedMemory() {
            nowFlag = 0;
        }

        // 返回分配的内存首地址(string) 失败返回字符串 "error"
        String request(int size) {
            if (size == 0 || size > totalSize) {
                return "error";
            }
            // 1.首先去碎片内存块 看下是否有空间可以分配,
            if (!pieceBlock.isEmpty()) {
                // todo
                Iterator<Map.Entry<Integer, Integer>> iterator = pieceBlock.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, Integer> entry = iterator.next();
                    if (entry.getValue() >= size) {
                        Integer key = entry.getKey();

                        // 增加新的碎片
                        if (entry.getValue() > size) {
                            pieceBlock.put(key + size, pieceBlock.get(key) - size);
                        }
                        // 分配内存加一, 碎片内存减一.
                        // todo
                        iterator.remove();
                        allocateBlock.put(key, size);
                        return String.valueOf(key);
                    }
                }
            }
            if (size > (totalSize-nowFlag)) {
                return "error";
            }
            // 2. 碎片内存块没有记录, 或者不行, 再重新的分配内存块, 分配一块内存. 分配内存, 更新可用内存的首地址.
            // todo
            allocateBlock.put(nowFlag,   size);
            nowFlag = nowFlag + size;
            return String.valueOf(nowFlag -size);
        }

        boolean release(int startAddress) {
            if (!allocateBlock.containsKey(startAddress)) {
                // 首地址不存在.
                return false;
            }
            Integer length = allocateBlock.get(startAddress);

            pieceBlock.put(startAddress, length);
            allocateBlock.remove(startAddress);
            // 连接相邻碎片
            linkNeighborPieces();
            return true;
        }

        // 连接相邻碎片.. <0,10> <10,20> -> <30,20>
        // 连接相邻碎片.                    <0,30>
        void linkNeighborPieces() {
            Integer[] keys = pieceBlock.keySet().toArray(new Integer[0]);
            Integer[] values = pieceBlock.values().toArray(new Integer[0]);
            int i = 0;  // 指针i 指向其中一个碎片 起始为 第一个碎片.
            int j = 1; // 指针j 指向相邻的那个碎片. 起始为第2个碎片.
            int size = pieceBlock.size();
            while (i < size - 1 && j < size) {
                // 如果相邻碎片之间没有空隙.则可以合并.
                if (keys[i] + values[i] == keys[j]) {
                    int newValue = values[i] + values[j];
                    // todo
                    values[i] = newValue;
                    pieceBlock.put(keys[i], newValue);
                    pieceBlock.remove(keys[j]);
                    j++;
                } else {
                    // 指针i,j向右前进. 保证相邻.
                    i = j;
                    j++;
                }
            }
        }
    }

    /**
     * 5
     * REQUEST=10
     * REQUEST=20;
     * RELEASE=0
     * REQUEST=20;
     * REQUEST=10
     * <p>
     * 输出样例:
     * 0
     * 10
     * 空
     * 30
     * 0
     */
    public static void main(String[] args) {
        allocateBlock = new TreeMap<>();
        pieceBlock = new TreeMap<>();
        AllocatedMemory allocatedMemory = new AllocatedMemory();
        Scanner scanner = new Scanner(System.in);
        int line = Integer.parseInt(scanner.nextLine());
        String[][] ins = new String[line][2];

        for (int i = 0; i < line; i++) {
            ins[i] = scanner.nextLine().split("=");
            if (ins[i][0].startsWith("REQUEST")) {
                System.out.println(allocatedMemory.request(Integer.parseInt(ins[i][1])));
            } else {
                boolean ret = allocatedMemory.release(Integer.parseInt(ins[i][1]));
                if (!ret) {
                    System.out.println("error");
                }
            }
        }
        scanner.close();
    }
}
