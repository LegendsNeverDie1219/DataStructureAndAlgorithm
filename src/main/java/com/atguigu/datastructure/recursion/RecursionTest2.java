package com.atguigu.datastructure.recursion;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/17 22:56
 */
public class RecursionTest2 {
    // 递归的四个规则
    // 1. 每当执行一个方法的时候,都会在栈内存中开辟一块独立的空间(栈)
    // 2. 其中, 如果方法内的变量是基本数据类型,则每一个栈中 的变量都是独立的. 如果是引用类型,则共用
    // 3.递归总是向着退出递归条件的方向逼近,否则是死循环,StackOverFlow
    // 4.当一个方法执行完,或者是遇到了return. 就会把结果 返回给调用的地方.
    public static void main(String[] args) {
        test(4);
        int res = factorial(4);
        System.out.println(res);
    }
    // 阶乘问题  24
    private static int factorial(int n) {
         if (n == 1) {
             return 1;
         } else {
            return  factorial(n-1) * n;
         }
    }

    // 打印问题
    // 打印: 2,3, 4
    private static void test(int n) {
        if (n > 2) {
            test(n -1);
        }
        System.out.println(n);
    }

    // 打印: 2
    private static void test2(int n) {
        if (n > 2) {
            test(n-1);
        } else {
            System.out.println(n);
        }
    }
}
