package com.atguigu.oj;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/18 23:02
 */
public class SortedByWeight {
    /**
     * num个运动员 编号从1开始
     * <p>
     * 先按照身高升序排序
     * 再按照体重进行升序排序
     * 在按照编号进行升序排序.
     * <p>
     * 输入:
     * 第一行是学生r人数的num
     * 第二行是num个整数数组 数组元素值为身高 下标+1 为编号
     * 第二行nums个整数数组. 数组元素值为体重 下标+1 为编号
     * 输入;
     * 4
     * 100 100 120 130
     * 40 30 60 50
     * 输出:
     * 2 1 3 4
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentCnt = scanner.nextInt();
        int[] heights = new int[studentCnt];
        int[] weights = new int[studentCnt];
        for (int i = 0; i < studentCnt; i++) {
            heights[i] = scanner.nextInt();
        }

        for (int i = 0; i < studentCnt; i++) {
            weights[i] = scanner.nextInt();
        }

        scanner.close();

        int[] ids = sortStudent(heights, weights);

        String idsStr = Arrays.stream(ids).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(idsStr);
    }


    private static int[] sortStudent(int[] heights, int[] weights) {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < heights.length; i++) {
            Student student = new Student();
            student.no = i + 1;
            student.height = heights[i];
            student.weight = weights[i];
            studentList.add(student);
        }

        int[] ints = studentList.stream()
                .sorted(Comparator.comparing(Student::getHeight)
                        .thenComparing(Student::getWeight)
                        .thenComparing(Student::getNo)).mapToInt(Student::getNo).toArray();
        return ints;
    }

    @Setter
    @Getter
    private static class Student {
        int no;
        int height;
        int weight;
    }
}
