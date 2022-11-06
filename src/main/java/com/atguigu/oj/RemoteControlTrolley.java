package com.atguigu.oj;

import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/20 22:19
 */
public class RemoteControlTrolley {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String commands = scanner.nextLine();
        scanner.close();
        String result = execCommand(commands);
        System.out.println(result);
    }

    // GLR
    // GG
    //(0,2)
    private static String execCommand(String commands) {
        char[] charArray = commands.toCharArray();
        FaceType currentDirection = FaceType.TOP;
        int x = 0;
        int y = 0;
        for (char character : charArray) {
            if (character == 'G') {
                if (FaceType.TOP.equals(currentDirection)) {
                    y++;
                } else if (FaceType.LEFT.equals(currentDirection)) {
                    x--;
                } else if (FaceType.RIGHT.equals(currentDirection)) {
                    x++;
                } else {
                    y--;
                }
            } else if (character == 'L') {
                if (FaceType.TOP.equals(currentDirection)) {
                    currentDirection =FaceType.LEFT;
                } else if (FaceType.LEFT.equals(currentDirection)) {
                    currentDirection = FaceType.DOWN;
                } else if (FaceType.RIGHT.equals(currentDirection)) {
                    currentDirection = FaceType.TOP;
                } else {
                    currentDirection = FaceType.RIGHT;
                }

            } else if (character == 'R') {
                if (FaceType.TOP.equals(currentDirection)) {
                    currentDirection = FaceType.RIGHT;
                } else if (FaceType.LEFT.equals(currentDirection)) {
                    currentDirection =FaceType.TOP;
                } else if (FaceType.RIGHT.equals(currentDirection)) {
                    currentDirection = FaceType.DOWN;
                } else {
                    currentDirection =FaceType.LEFT;
                }
            }
        }
        return "(" + x + "," + y + ")";


    }

    private enum FaceType {
        // 上
        TOP,
        // 下
        DOWN,
        // 左
        LEFT,
        // 右
        RIGHT;
    }
}
