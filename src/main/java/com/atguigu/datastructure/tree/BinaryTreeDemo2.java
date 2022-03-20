package com.atguigu.datastructure.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/21 21:38
 */
public class BinaryTreeDemo2 {
    public static void main(String[] args) {
        // 手动构建一个二叉树
        HeroNode root = new HeroNode(1, "呼保义", "宋江");
        constructureBinaryTree(root);

        BinaryTree binaryTree = new BinaryTree(root);
        System.out.println("前序遍历");
        binaryTree.preOrder();

        System.out.println("中序遍历");
        binaryTree.infixOrder();

        System.out.println("后序遍历");
        binaryTree.postOrder();

        //前序遍历
        //前序遍历的次数 ：4
//		System.out.println("前序遍历方式~~~");
//		HeroNode resNode = binaryTree.preOrderSearch(5);
//		if (resNode != null) {
//			System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
//		} else {
//			System.out.printf("没有找到 no = %d 的英雄", 5);
//		}

        //中序遍历查找
        //中序遍历3次
//		System.out.println("中序遍历方式~~~");
//		HeroNode resNode = binaryTree.infixOrderSearch(5);
//		if (resNode != null) {
//			System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
//		} else {
//			System.out.printf("没有找到 no = %d 的英雄", 5);
//		}

        //后序遍历查找
        //后序遍历查找的次数  2次
//		System.out.println("后序遍历方式~~~");
//		HeroNode resNode = binaryTree.postOrderSearch(5);
//		if (resNode != null) {
//			System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
//		} else {
//			System.out.printf("没有找到 no = %d 的英雄", 5);
//		}

        //测试一把删除结点

        System.out.println("删除前,前序遍历");
        binaryTree.preOrder(); //  1,2,3,5,4
        binaryTree.delNode(5);
        //binaryTree.delNode(3);
        System.out.println("删除后，前序遍历");
        binaryTree.preOrder(); // 1,2,3,4

    }

    private static void constructureBinaryTree(HeroNode root) {
        HeroNode node2 = new HeroNode(2, "玉麒麟", "卢俊义");
        HeroNode node3 = new HeroNode(3, "智多星", "吴用");
        HeroNode node4 = new HeroNode(4, "入云龙", "公孙胜");
        HeroNode node5 = new HeroNode(5, "大刀", "关胜");
        HeroNode node6 = new HeroNode(6, "豹子头", "林冲");
        HeroNode node7 = new HeroNode(7, "霹雳火", "秦明");
        root.setLeft(node2);
        root.setRight(node3);

        node2.setLeft(node4);
        node2.setRight(node5);

        node3.setLeft(node6);
        node3.setRight(node7);
    }

    // 二叉树
    private static class BinaryTree {
        // 二叉树的根节点
        private  HeroNode root;

        public BinaryTree(HeroNode root) {
            this.root = root;
        }

        public void preOrder() {
            if (root == null) {
                System.out.println("当前二叉树根节点为空, 无法遍历");
            } else {
                root.preOrder();
            }
        }

        public void infixOrder() {
            if (root == null) {
                System.out.println("当前二叉树根节点为空, 无法遍历");
            } else {
                root.infixOrder();
            }
        }

        public void postOrder() {
            if (root == null) {
                System.out.println("当前二叉树根节点为空, 无法遍历");
            } else {
                root.postOrder();
            }
        }

        //前序遍历
        public HeroNode preOrderSearch(int no) {
            if(root != null) {
                return root.preOrderSearch(no);
            } else {
                return null;
            }
        }
        //中序遍历
        public HeroNode infixOrderSearch(int no) {
            if(root != null) {
                return root.infixOrderSearch(no);
            }else {
                return null;
            }
        }
        //后序遍历
        public HeroNode postOrderSearch(int no) {
            if(root != null) {
                return this.root.postOrderSearch(no);
            }else {
                return null;
            }
        }

        //删除结点
        public void delNode(int no) {
            if(root != null) {
                //如果只有一个root结点, 这里立即判断root是不是就是要删除结点
                if(root.getNo() == no) {
                    root = null;
                } else {
                    //递归删除
                    root.delNode(no);
                }
            }else{
                System.out.println("空树，不能删除~");
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
        // 节点的right域
        private HeroNode right;

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
            if(this.left != null && this.left.no == no) {
                this.left = null;
                return;
            }
            //3.如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
            if(this.right != null && this.right.no == no) {
                this.right = null;
                return;
            }
            //4.我们就需要向左子树进行递归删除
            if(this.left != null) {
                this.left.delNode(no);
            }
            //5.则应当向右子树进行递归删除
            if(this.right != null) {
                this.right.delNode(no);
            }
        }

    }
}
