package com.atguigu.leetcode.ChapterOne.linkedlistdoublepointer;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListNodeUtils {
    public static ListNode generateListNodes(List<Integer> valList) {
        LinkedHashMap<Integer, ListNode> linkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i < valList.size(); i++) {
            ListNode listNode = new ListNode(valList.get(i));
            linkedHashMap.put(i, listNode);
        }
        ListNode headNode = linkedHashMap.get(0);
        ListNode temp = headNode;
        for (Map.Entry<Integer, ListNode> entry : linkedHashMap.entrySet()) {
            if (entry.getKey() == 0) {
                continue;
            }
            ListNode currentNode = entry.getValue();
            temp.setNext(currentNode);
            temp = currentNode;
        }
        return headNode;
    }

    public static ListNode generateListNodes2(List<Integer> valList) {
        LinkedList<Integer> linkedList = new LinkedList<>(valList);
        return buildRootNode(linkedList);
    }

    private static ListNode buildRootNode(LinkedList<Integer> linkedList) {
        if(linkedList.isEmpty()) {
            return null;
        }
        Integer firstElement = linkedList.removeFirst();
        ListNode rootNode = new ListNode(firstElement);
        rootNode.next = buildRootNode(linkedList);
        return rootNode;
    }

    public static void showListNodes(ListNode listNode) {
        ListNode tempNode = listNode;
        while (tempNode != null) {
            System.out.println(tempNode);
            tempNode = tempNode.next;
        }
    }
}