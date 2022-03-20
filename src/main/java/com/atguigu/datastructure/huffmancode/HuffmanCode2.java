package com.atguigu.datastructure.huffmancode;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/27 13:56
 */
public class HuffmanCode2 {
    /**
     * 赫夫曼树: 所有叶子节点的带权路径长度之和 最小的(wpl) 的二叉树
     * 即 权值越大的叶子节点 离 根节点越近.
     * 权: 每个节点的表示的数值
     * 路径长度: 节点所在层数-1
     * 带权路径 = 权 * 路径长度
     */
    private static final Map<Byte, String> huffmanMap = new HashMap<>();

    public static void main(String[] args) {
        // 1.字符串转化为 字节数组
        String sourceStr = "i like like like java do you like a java";
        byte[] contentByte = sourceStr.getBytes(StandardCharsets.UTF_8);

        byte[] huffmanBytes = huffmanZip(contentByte);

        byte[] sourceBytes = huffmanUnZip(huffmanBytes, huffmanMap);
        System.out.println("sourceBytes=" + Arrays.toString(sourceBytes));
    }

    @Test
    public void testZipFile() {


    }

    @Test
    public void testUnzipFile() {
        String srcFile = "D:\\src.bmp";
        String zipFile = "D:\\dst.zip";
        String targetFile = "D:\\src2.bmp";
        zipFile(srcFile, zipFile);

        unzipFile(zipFile, targetFile);
    }

    public static void zipFile(String srcFile, String zipFile) {
        try (InputStream inputStream = new FileInputStream(srcFile);
             // 类似于 Stringbuilder
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             OutputStream out = new FileOutputStream(zipFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            byte[] byteArray = new byte[1024 * 8];
            int length = inputStream.read(byteArray);
            while (length != -1) {
                byteArrayOutputStream.write(byteArray, 0, length);
                length = inputStream.read(byteArray);
            }

            // byte[] bytes = byteArrayOutputStream.toString().getBytes(StandardCharsets.UTF_8);
            byte[] contentByte = byteArrayOutputStream.toByteArray();

            byte[] huffmanBytes = huffmanZip(contentByte);

            objectOutputStream.writeObject(huffmanBytes);
            objectOutputStream.writeObject(huffmanMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzipFile(String zipFile, String targetFile) {
        try (InputStream inputStream = new FileInputStream(zipFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
             OutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] contentByte = (byte[]) objectInputStream.readObject();
            Map<Byte, String> huffmanMap = (Map<Byte, String>) objectInputStream.readObject();

            byte[] bytes = huffmanUnZip(contentByte, huffmanMap);

            outputStream.write(bytes);
            outputStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static byte[] huffmanUnZip(byte[] huffmanBytes, Map<Byte, String> huffmanMap) {
        // 1.首先将huffmanBytes 还原为对应的二进制赫夫曼编码形式
        // 1010100010111111110010001011 (其中,需要最后一个byte元素不需要补高位 )
        StringBuilder stringBuilder = new StringBuilder();
        boolean needToFillHigh = true;
        String str;
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte byteData = huffmanBytes[i];
            if (i == huffmanBytes.length - 1) {
                needToFillHigh = false;
                str = byteData2BinaryString(byteData, needToFillHigh);
            } else {
                str = byteData2BinaryString(byteData, needToFillHigh);
            }
            stringBuilder.append(str);
        }
        String binaryStr = stringBuilder.toString();
        // 2.然后查询赫夫曼编码表,将二进制编码形式转化为原始的字节数组
        // 编码表key-value 反转 结果为   二进制字符串 -> 字符.
        Map<String, Byte> reversedMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanMap.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }

        List<Byte> sourceByteList = new ArrayList<>();
        // 需要左右指针 ,左指针为i, 右指针为j
        for (int i = 0; i < binaryStr.length(); ) {
            int j = i + 1;
            boolean flag = true;
            String substr = "";
            // todo 左闭右开, 所以是 <=
            while (flag && j <= binaryStr.length()) {
                substr = binaryStr.substring(i, j);
                // 说明找到了对应的二进制字符串.
                if (reversedMap.containsKey(substr)) {
                    flag = false;
                } else {
                    j++;
                }
            }

            Byte byteData = reversedMap.get(substr);
            sourceByteList.add(byteData);
            //左指针 右移到 右指针的位置. 取消循环
            i = j;
        }

        byte[] sourceByteArr = new byte[sourceByteList.size()];
        for (int i = 0; i < sourceByteList.size(); i++) {
            sourceByteArr[i] = sourceByteList.get(i);
        }
        return sourceByteArr;
    }

    private static String byteData2BinaryString(byte byteData, boolean needToFillHigh) {
        //  Integer.toBinaryString(127) 1111 111
        //  Integer.toBinaryString(10) 1010
        // Integer.toBinaryString(-1)  11111111111111111111111111111111
        // Integer.toBinaryString(-88)  11111111111111111111111110101000
        int temp = byteData;
        // 如果是最后一个byte元素的话,则不需要补高位
        if (needToFillHigh) {
            // 如果正数的数字的话,则还需要补高位, 做法是 该int类型过的数字 跟 256(10000 0000) 指向按位或运算.
            // 1 0000 0000(256)
            //    100 1101(77)
            // 1 0100 1101(333)
            temp |= 256;
        }


        String binaryString = Integer.toBinaryString(temp);

        // 即如果是超过8位的时候(负数,或者补高位之后的正数),,则需要截串截成 8位
        if (needToFillHigh) {
            binaryString = binaryString.substring(binaryString.length() - 8);
        }
        return binaryString;
    }

    private static byte[] huffmanZip(byte[] contentByte) {
        // 1.字节数组中的元素 转化为 List<Node>
        List<Node> listNodes = getListNodes(contentByte);

        // 2.输入: List<Node> ,转化为一棵赫夫曼树. 返回根节点.
        Node huffmanTreeRoot = createHuffmanTree(listNodes);
        // System.out.println("前序遍历");
        // huffmanTreeRoot.preOrder();

        // 3.根据赫夫曼树(root节点) 生成对应的赫夫曼编码表(Map<Byte,String> key 为 某一个byte类型的字符,value为赫夫曼树下该节点的路径(即该字符对应的前缀编码))
        // {32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111,
        // 108=000, 111=0011}
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        // System.out.println("~生成的赫夫曼编码表= " + huffmanCodes);

        // 4.根据赫夫曼编码表,压缩原来的字节数组, 得到赫夫曼编码字节数组
        //System.out.println("huffmanCodeBytes=" + Arrays.toString(huffmanCodesBytes));
        return zip(contentByte, huffmanCodes);
    }

    private static byte[] zip(byte[] contentByte, Map<Byte, String> huffmanCodes) {
        // 1.遍历原始数组, 根据赫夫曼编码表得到数组中该字符 对应的赫夫曼编码
        // 2.拼串
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : contentByte) {
            String pathStr = huffmanCodes.get(b);
            stringBuilder.append(pathStr);
        }
        String huffmanCodeStr = stringBuilder.toString();

        int newArrayLength = huffmanCodeStr.length() / 8;
        if (huffmanCodeStr.length() % 8 != 0) {
            newArrayLength++;
        }

        byte[] huffmanCodeByteArray = new byte[newArrayLength];
        int index = 0;
        // 3.每8位是一个字节.进行分割,分割之后,将这八位转化为一个十进制的数字,存入到新的数组中(新数组的长度 = str/ 8  or str/ 8 + 1)
        for (int i = 0; i < huffmanCodeStr.length(); i += 8) {
            String subString = "";
            if (i + 8 > huffmanCodeStr.length()) {
                subString = huffmanCodeStr.substring(i);
            } else {
                subString = huffmanCodeStr.substring(i, i + 8);
            }
            byte data = (byte) Integer.parseInt(subString, 2);
            huffmanCodeByteArray[index] = data;
            index++;
        }
        return huffmanCodeByteArray;
    }

    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        getCodes(root.getLeft(), "0", new StringBuilder());
        getCodes(root.getRight(), "1", new StringBuilder());

        return huffmanMap;
    }

    /**
     * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     *
     * @param currentNode   传入结点
     * @param path          路径： 左子结点是 0, 右子结点 1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node currentNode, String path, StringBuilder stringBuilder) {
        // 1.根节点非空判断
        if (currentNode == null) {
            return;
        }
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(path);
        // 如果当前节点不是叶子结点的时候,左递归查找路径,右递归查找路径.
        // 当前节点是叶子节点,则需要将字符 以及 赫夫曼树下该叶子结点的路径 的key-value 存入map中.
        if (currentNode.data == null) {
            // 左递归查找路径
            getCodes(currentNode.getLeft(), "0", stringBuilder2);
            // 右递归查找路径
            getCodes(currentNode.getRight(), "1", stringBuilder2);
        } else {
            huffmanMap.put(currentNode.getData(), stringBuilder2.toString());
        }
    }

    private static List<Node> getListNodes(byte[] contentByte) {
        // 1.统计数组中每个字符以及每个字符出现的次数
        Map<Byte, Integer> countMap = new HashMap<>();
        for (byte b : contentByte) {
            if (countMap.containsKey(b)) {
                Integer count = countMap.get(b);
                countMap.put(b, ++count);
            } else {
                countMap.put(b, 1);
            }
        }
        // 2.构建一个List<Node>
        return countMap.entrySet().stream().map(entry -> {
            Byte character = entry.getKey();
            Integer weight = entry.getValue();
            return new Node(weight, character);
        }).collect(Collectors.toList());
    }

    /**
     * 1)对数组中的数据进行从小到大的排序,每一个数据都可以看成是一个节点, 每一个节点都可以看成是一个最简单的二叉树
     * 2)取出根节点权值最小的两颗二叉树
     * 3)组成一个新的二叉树,新的二叉树的根节点的权值= 前面两颗二叉树根节点的权值之和/
     * 4)再将这颗二叉树,以根节点的权值大小, 再次进行排序. 不断重复 1-2-3-4, 知道数组中所有的元素都被处理.
     *
     * @param listNodes listNodes
     */
    private static Node createHuffmanTree(List<Node> listNodes) {

        while (listNodes.size() > 1) {
            List<Node> sortedList =
                    listNodes.stream().sorted(Comparator.comparingInt(node -> node.weight)).collect(Collectors.toList());
            Node nodeOne = sortedList.get(0);
            Node nodeTwo = sortedList.get(1);

            Node newNode = new Node(nodeOne.getWeight() + nodeTwo.getWeight(), null);
            newNode.setLeft(nodeOne);
            newNode.setRight(nodeTwo);

            listNodes.remove(nodeOne);
            listNodes.remove(nodeTwo);
            listNodes.add(newNode);
        }
        // 剩下的就是根节点.
        return listNodes.get(0);
    }

    //前序遍历的方法
    private static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("赫夫曼树为空");
        }
    }

    @Setter
    @Getter
    //private static class Node implements Comparable<Node> {
    private static class Node {
        // 存放节点的权值
        private int weight;
        // 存放叶子节点的字符(非叶子节点 为null)
        private Byte data;
        private Node left;
        private Node right;

        public Node(int weight, Byte data) {
            this.weight = weight;
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "weight=" + weight +
                    ", data=" + data +
                    '}';
        }

        // @Override
        // public int compareTo(Node node) {
        //     return this.weight = node.weight;
        // }
        //前序遍历
        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }
    }
}
