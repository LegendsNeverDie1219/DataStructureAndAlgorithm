package com.atguigu.datastructure.tree.threadedbinarytree;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/23 22:13
 */
public class ThreadedBinaryTreeDemo2 {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "及时雨", "宋江");
        //constrBinaryTree(root);
        HeroNode node2 = new HeroNode(3, "玉麒麟", "卢俊义");
        HeroNode node3 = new HeroNode(6, "智多星", "吴用");
        HeroNode node4 = new HeroNode(8, "入云龙", "公孙胜");
        HeroNode node5 = new HeroNode(10, "大刀", "关胜");
        HeroNode node6 = new HeroNode(14, "豹子头", "林冲");
        root.setLeft(node2);
        root.setRight(node3);

        node2.setLeft(node4);
        node2.setRight(node5);

        node3.setLeft(node6);

        ThreadBinaryTree threadBinaryTree = new ThreadBinaryTree(root);
        threadBinaryTree.threadNode();

        HeroNode leftNode = node5.getLeft();
        HeroNode rightNode = node5.getRight();
        System.out.println("10号结点的前驱结点是 ="  + leftNode); //3
        System.out.println("10号结点的后继结点是="  + rightNode); //1

        threadBinaryTree.listThreadNodes();


    }

    private static void constrBinaryTree(HeroNode root) {
        HeroNode node2 = new HeroNode(3, "玉麒麟", "卢俊义");
        HeroNode node3 = new HeroNode(6, "智多星", "吴用");
        HeroNode node4 = new HeroNode(8, "入云龙", "公孙胜");
        HeroNode node5 = new HeroNode(10, "大刀", "关胜");
        HeroNode node6 = new HeroNode(14, "豹子头", "林冲");
        root.setLeft(node2);
        root.setRight(node3);

        node2.setLeft(node4);
        node2.setRight(node5);

        node3.setLeft(node6);

    }

    private static class ThreadBinaryTree {
        // 二叉树的根节点
        private final HeroNode root;
        // 当前节点的前驱节点.(前驱节点,后继节点都是建立在某种遍历的基础之上的.)
        private HeroNode preNode;


        public ThreadBinaryTree(HeroNode root) {
            this.root = root;
        }

        public void threadNode() {
            this.threadNode(root);
        }

        // 以中序线索化二叉树为例
        public void threadNode(HeroNode currentNode) {
            // 判断当前节点是否为空,为空,则就不能线索化了.
            if (currentNode == null) {
                //System.out.println("无法线索化该节点");
                return;
            }
            // 1.左子树递归线索化
            threadNode(currentNode.getLeft());
            // 2.线索化当期节点
            // left域: 当前节点的左指针为空,则让其左指针指向当前节点的前驱节点
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(preNode);
                currentNode.setLeftType(1);
            }

            // 当前节点right域的线索化是在下一次 递归中
            // 如果上一个节点的右指针为空, 则让其右指针 指向 当前节点
            if (preNode != null && preNode.getRight() == null) {
                preNode.setRight(currentNode);
                preNode.setRightType(1);
            }
            //System.out.println(currentNode.getLeft().toString() + "___"+ currentNode.getRight().toString());
            // preNode 后移
            preNode = currentNode;

            // 3.右子树递归线索化
            threadNode(currentNode.getRight());
        }

        public void listThreadNodes() {
            HeroNode currentNode = root;
            while (currentNode != null) {
                // 1. 首先判断当前节点的做左指针类型是否为1, 如果不是,则循环查找,直到找到中序遍历的第一个节点
                while (currentNode.getLeftType() != 1) {
                    currentNode = currentNode.getLeft();
                }
                // 2.打印该节点
                System.out.println(currentNode);
                // 3.判断当前节点的右指针类型是否为1, 如果是,则循环并打印并后移,否则, 当前节点右移.指向其右子树.
                while (currentNode.getRightType() == 1) {
                    System.out.println(currentNode.getRight());
                    currentNode = currentNode.getRight();
                }
                currentNode = currentNode.getRight();
            }
        }
    }

    // 节点对象
    @Setter
    @Getter
    private static class HeroNode {
        private int no;

        private String nickName;

        private String name;

        // 节点的left域
        private HeroNode left;

        // 左指针的类型
        // (即当前节点的左指针类型为0 ,则指向的是其左子树, 为1, 则指向的是其前驱节点)
        private int leftType;

        // 节点的right域
        // 即当前节点的右指针类型为0 .则指向的是其右子树,为1,则指向的是其后继节点.
        private HeroNode right;

        private int rightType;

        public HeroNode(int no, String nickName, String name) {
            this.no = no;
            this.nickName = nickName;
            this.name = name;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", nickName='" + nickName + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }


        /**
         * 前序遍历
         * 先输出当前节点
         * 然后 如果当前节点的左子节点不为空,则左子树 递归前序遍历
         * 接着 如果当前节点的左子节点不为空, 则右子树 递归前序遍历
         */
        public void preOrder() {
            System.out.println(this);
            if (this.getLeft() != null) {
                this.getLeft().preOrder();
            }
            if (this.getRight() != null) {
                this.getRight().preOrder();
            }
        }

        /**
         * 中序遍历
         * 先判断当前节点的左子节点是否为空, 如果不为空, 则左子树 递归中序遍历
         * 再输出当前节点
         * 接着判断当前节点的右子节点是否为空, 如果不为空, 则右子树 递归中序遍历
         */
        public void infixOrder() {
            if (this.getLeft() != null) {
                this.getLeft().infixOrder();
            }
            System.out.println(this);

            if (this.getRight() != null) {
                this.getRight().infixOrder();
            }
        }

        /**
         * 后序遍历
         * 先判断当前节点的左子节点是否为空,如果不为空,则左子树 递归后续遍历
         * 再判断当前节点的右子节点是否为空, 如果不为空,则右子树 递归后续遍历
         * 接着输出当前节点.
         */
        public void postOrder() {
            if (this.getLeft() != null) {
                this.getLeft().postOrder();
            }

            if (this.getRight() != null) {
                this.getRight().postOrder();
            }

            System.out.println(this);
        }

        //前序遍历查找

        /**
         * @param no 查找no
         * @return 如果找到就返回该Node ,如果没有找到返回 null
         */
        public HeroNode preOrderSearch(int no) {
            System.out.println("进入前序遍历");
            //比较当前结点是不是
            if (this.no == no) {
                return this;
            }
            //1.则判断当前结点的左子节点是否为空，如果不为空，则递归前序查找
            //2.如果左递归前序查找，找到结点，则返回
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.preOrderSearch(no);
            }
            if (resNode != null) {//说明我们左子树找到
                return resNode;
            }
            //1.左递归前序查找，找到结点，则返回，否继续判断，
            //2.当前的结点的右子节点是否为空，如果不空，则继续向右递归前序查找
            if (this.right != null) {
                resNode = this.right.preOrderSearch(no);
            }
            return resNode;
        }

        //中序遍历查找
        public HeroNode infixOrderSearch(int no) {
            //判断当前结点的左子节点是否为空，如果不为空，则递归中序查找
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.infixOrderSearch(no);
            }
            if (resNode != null) {
                return resNode;
            }
            System.out.println("进入中序查找");
            //如果找到，则返回，如果没有找到，就和当前结点比较，如果是则返回当前结点
            if (this.no == no) {
                return this;
            }
            //否则继续进行右递归的中序查找
            if (this.right != null) {
                resNode = this.right.infixOrderSearch(no);
            }
            return resNode;

        }

        //后序遍历查找
        public HeroNode postOrderSearch(int no) {

            //判断当前结点的左子节点是否为空，如果不为空，则递归后序查找
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.postOrderSearch(no);
            }
            if (resNode != null) {//说明在左子树找到
                return resNode;
            }

            //如果左子树没有找到，则向右子树递归进行后序遍历查找
            if (this.right != null) {
                resNode = this.right.postOrderSearch(no);
            }
            if (resNode != null) {
                return resNode;
            }
            System.out.println("进入后序查找");
            //如果左右子树都没有找到，就比较当前结点是不是
            if (this.no == no) {
                return this;
            }
            return resNode;
        }

        //递归删除结点
        //1.如果删除的节点是叶子节点，则删除该节点
        //2.如果删除的节点是非叶子节点，则删除该子树
        public void delNode(int no) {

            //思路
		/*
		 * 	1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断当前这个结点是不是需要删除结点.
			2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将this.left = null; 并且就返回(结束递归删除)
			3. 如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
			4. 如果第2和第3步没有删除结点，那么我们就需要向左子树进行递归删除
			5.  如果第4步也没有删除结点，则应当向右子树进行递归删除.

		 */
            //2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将this.left = null; 并且就返回(结束递归删除)
            if (this.left != null && this.left.no == no) {
                this.left = null;
                return;
            }
            //3.如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
            if (this.right != null && this.right.no == no) {
                this.right = null;
                return;
            }
            //4.我们就需要向左子树进行递归删除
            if (this.left != null) {
                this.left.delNode(no);
            }
            //5.则应当向右子树进行递归删除
            if (this.right != null) {
                this.right.delNode(no);
            }
        }

    }
}
