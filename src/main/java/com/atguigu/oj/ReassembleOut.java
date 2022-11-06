package com.atguigu.oj;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/17 22:11
 */
public class ReassembleOut {
    /**
     * 某协议的数据包 在发送的时候会被分为若干数据分段, 接收到的数据分段可能出现重复,丢失,乱序.
     * 每个数据分段的组成格式为: 数据类型 分段序号 结束标志 内容长度  净荷内容
     * <p>
     * 数据类型:  数据分段的数据类型, 取值范围[0,9]
     * 分段序号 : 数据分段在数据包中的序号. 取值范围[0,10000]
     * 结束标志: 表示此数据库分段是否为数据包的最后一个分段 是最后一个 则为1 ,否则为0
     * 净荷长度:  净荷内容的长度 ,取值范围是[2,5]
     * 净荷内容: 此数据分段的实际内容
     * <p>
     * 现在给定一批接收到的数据分段和某一数据类型, 请重组该数据类型的数据包.
     * <p>
     * 成功重组必须是完整的数据, 分段序号 从0开始且是连续的.并且仅序号最大的数据分段的结束标志为1.
     * 如果能成功重组, 按分段序号进行升序, 将该数据类型的净荷内容 ,拼接为一个字符串并输出.如果分段序号有重复则去重.
     * 如果不能重组成功,则输出字符串NA.
     * <p>
     * 输入:
     * 首行输入一个整数,N,表示数据分段的个数
     * 接下来N行, 每行输入一个数据分段.
     * 最后一行是一个整数, 代表需要重组的数据类型.
     * <p>
     * 输出: 重组后的数据包内容, 或字符串NA.
     * <p>
     * 7
     * 0 1 0 5 hello
     * 1 2 1 2 CC
     * 0 3 1 5 world
     * 0 0 0 3 Say
     * 1 0 0 2 AA
     * 0 2 0 2 to
     * 1 1 0 2 BB
     * <p>
     * Sayhellotoworld
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dataFragmentCnt = scanner.nextInt();
        DataFragment[] dataFragments = new DataFragment[dataFragmentCnt];
        for (int i = 0; i < dataFragmentCnt; i++) {
            int dataType = scanner.nextInt();
            int index = scanner.nextInt();
            int endFlag = scanner.nextInt();
            int len = scanner.nextInt();
            String data = scanner.next();
            dataFragments[i] = new DataFragment(dataType, index, endFlag, len, data);
        }

        int dstType = scanner.nextInt();
        scanner.close();
        System.out.println(recombine(dataFragments, dstType));
    }

    private static String recombine(DataFragment[] dataFragments, int dstType) {
        // 1.首先进行过滤指定数据类型的,然后对DataFragment进行去重(重写equals,hashcode), 接着按照index进行升序排序,得到一个处理后的list
        // 2. 然后对处理后的list机型校验. 其中最后一个元素的endFlag为1 ,其他都只能为0. 并且index 要和当前的i保持一致,(保证从0开始连续)
        // 3.list 映射到data ,然后data 连接成成字符串.
        List<DataFragment> dataFragmentList =
                Arrays.stream(dataFragments).filter(dataFragment -> dataFragment.dataType == dstType).distinct()
                        .sorted(Comparator.comparingInt(obj -> obj.index)).collect(Collectors.toList());

        if (dataFragmentList.size() == 0) {
            return "NA";
        }

        for (int i = 0; i < dataFragmentList.size(); i++) {
            DataFragment currentDataFragment = dataFragmentList.get(i);

            if (i == dataFragmentList.size() - 1) {
                // 是最后一个元素但是endFlag不为1
                if (currentDataFragment.endFlag != 1) {
                    return "NA";
                }
            } else {
                if (currentDataFragment.endFlag != 0) {
                    return "NA";
                }
            }

            if (i != currentDataFragment.index) {
                return "NA";
            }
            if (currentDataFragment.data.length() != currentDataFragment.len) {
                return "NA";
            }
        }

        return dataFragmentList.stream().map(dataFragment -> dataFragment.data).collect(Collectors.joining());


    }

    private static class DataFragment {
        int dataType;
        int index;
        int endFlag;
        int len;
        String data;

        public DataFragment(int dataType, int index, int endFlag, int len, String data) {
            this.dataType = dataType;
            this.index = index;
            this.endFlag = endFlag;
            this.len = len;
            this.data = data;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            DataFragment that = (DataFragment) obj;
            return dataType == that.dataType && index == that.index && endFlag == that.endFlag && len == that.len && Objects.equals(data, that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dataType, index, endFlag, len, data);
        }
    }
}
