package com.atguigu.datastructure.avl;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/1 21:59
 */
public class AvlBinarySortTreeDemo2 {
    public static void main(String[] args) {
        //int[] arr = {4,3,6,5,7,8};
        //int[] arr = { 10, 12, 8, 9, 7, 6 };
        int[] arr = {10, 11, 7, 6, 8, 9};
        //创建一个 AVLTree对象
        AvlBinarySortTree avlTree = new AvlBinarySortTree();
        //添加结点
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new TreeNode(arr[i]));
        }

        //遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();

        System.out.println("在平衡处理~~");
        System.out.println("树的高度=" + avlTree.getRoot().getCurrentNodeAsRootTreeHeight()); //3
        System.out.println("树的左子树高度=" + avlTree.getRoot().leftTreeHeight()); // 2
        System.out.println("树的右子树高度=" + avlTree.getRoot().rightTreeHeight()); // 2
        System.out.println("当前的根结点=" + avlTree.getRoot());//8

    }

    private static class AvlBinarySortTree {
        private TreeNode root;

        public TreeNode getRoot() {
            return root;
        }

        //添加结点的方法
        public void add(TreeNode node) {
            if (root == null) {
                root = node;//如果root为空则直接让root指向node
            } else {
                root.add(node);
            }
        }

        //中序遍历
        public void infixOrder() {
            if (root != null) {
                root.infixOrder();
            } else {
                System.out.println("二叉排序树为空，不能遍历");
            }
        }


        /**
         * 删除结点
         * 删除节点有三种情况:
         * 1) 删除叶子节点
         * 2) 删除只有一棵子树的节点
         * 3) 删除有两颗子树的节点
         * <p>
         * 第一种情况:删除叶子节点
         * ① 找到要删除的节点 targetNode
         * ② 找到要删除节点的父节点 parentNode
         * ③ 判断targetNode是 父节点的 左子节点还是右子节点, 然后删除
         * 左子节点  parentNode.left = null
         * 右子节点 parentNode.right = null
         * <p>
         * 第二种情况: 删除只有一棵子树的节点
         * ① 找到要删除的节点 targetNode
         * ② 找到要删除节点的父节点 parentNode
         * ③
         * a.如果targetNode的左子节点 不为空.
         * 并且targetNode是父节点的左子节点
         * 找到targetNode的左子树的下一个节点targetNode.left.来顶替targetNode
         * parentNode.left = targetNode.left
         * <p>
         * b.如果targetNode的左子节点 不为空.
         * 并且targetNode是父节点的右子节点
         * 找到targetNode的左子树的下一个节点targetNode.left.来顶替targetNode
         * parentNode.right = targetNode.left
         * <p>
         * <p>
         * c.如果targetNode的右子节点 不为空
         * 并且targetNode 是父节点的左子节点
         * 找到 targetNode的右子树的下一个节点targetNode.right,来顶替targetNode
         * parent.left = targetNode.right
         * <p>
         * d.如果targetNode的右子节点 不为空
         * 并且targetNode 是父节点的右子节点
         * 找到 targetNode的右子树的下一个节点targetNode.right,来顶替targetNode
         * parent.right = targetNode.right
         * <p>
         * 第三种情况: 删除有2棵子树的节点
         * ① 找到要删除的节点 targetNode
         * ② 找到要删除节点的父节点 parentNode
         * ③ 从targetNode的右子树中找到最小值 对应的节点minNode
         * 然后将value暂时存起来, temp = minNode.value
         * ④ 删除 minNode 即minNode= null
         * ⑤ targetNode的 value 用minNode的value替换
         * targetNode.value =temp
         *
         * @param value value
         */
        public void deleteNode(int value) {
            if (root == null) {
                System.out.println("二叉树为空,无法删除");
                return;
            }
            TreeNode targetNode = root.findTargetNode(value);
            if (targetNode == null) {
                System.out.println("没有找到对应的要删除的节点,删除失败");
                return;
            }

            if (targetNode == root) {
                System.out.println("开始删除根节点");
                root = null;
                return;
            }
            //如果我们发现当前这颗二叉排序树只有一个结点
            // if (root.getLeft() == null && root.getRight() == null) {
            //     root = null;
            //     return;
            // }

            //去找到targetNode的父结点
            TreeNode parentNode = findParentNode(value);

            // 要删除的结点是叶子结点
            if (targetNode.getLeft() == null && targetNode.getRight() == null) {
                System.out.println("要删除的结点是叶子结点");
                if (parentNode == null) {
                    root = null;
                    return;
                }
                //③ 判断targetNode是 父节点的 左子节点还是右子节点, 然后删除
                //左子节点  parentNode.left = null
                // 右子节点 parentNode.right = null
                if (parentNode.left != null && parentNode.getLeft().getValue() == value) {
                    parentNode.setLeft(null);
                } else if (parentNode.right != null && parentNode.right.getValue() == value) {
                    parentNode.setRight(null);
                }
            } else if (targetNode.getLeft() != null && targetNode.getRight() != null) {
                // 删除的是有2棵子树的节点
                System.out.println("删除的是有2棵子树的节点");
                // ③ 从targetNode的右子树中找到最小值 对应的节点minNode
                // 然后将value暂时存起来, temp = minNode.value
                // ④ 删除 minNode 即minNode= null
                // ⑤ targetNode的 value 用minNode的value替换
                // targetNode.value =temp
                int minValue = findMinValueOfTargetNodeRightTree(targetNode);
                targetNode.setValue(minValue);

            } else {
                // 删除的是有1棵子树的节点
                System.out.println("删除的是有1棵子树的节点");
                //* a.如果targetNode的左子节点 不为空.
                // 并且targetNode是父节点的左子节点
                // 找到targetNode的左子树的下一个节点targetNode.left.来顶替targetNode
                // parentNode.left = targetNode.left
                //  <p>
                //  b.如果targetNode的左子节点 不为空.
                //  并且targetNode是父节点的右子节点
                //  找到targetNode的左子树的下一个节点targetNode.left.来顶替targetNode
                //  parentNode.right = targetNode.left
                //  <p>
                //  <p>
                //  c.如果targetNode的右子节点 不为空
                //  并且targetNode 是父节点的左子节点
                //  找到 targetNode的右子树的下一个节点targetNode.right,来顶替targetNode
                //  parent.left = targetNode.right
                //  <p>
                //  d.如果targetNode的右子节点 不为空
                // 并且targetNode 是父节点的右子节点
                //  找到 targetNode的右子树的下一个节点targetNode.right,来顶替targetNode
                //   parent.right = targetNode.right
                if (targetNode.getLeft() != null) {
                    // 删除的当前节点是根节点.
                    if (parentNode == null) {
                        root = targetNode.getLeft();
                    } else {
                        if (targetNode == parentNode.getLeft()) {
                            parentNode.setLeft(targetNode.getLeft());
                        } else {
                            parentNode.setRight(targetNode.getLeft());
                        }
                    }

                } else {
                    if (parentNode == null) {
                        root = targetNode.getRight();
                    } else {
                        if (targetNode == parentNode.getLeft()) {
                            parentNode.setLeft(targetNode.getRight());
                        } else {
                            parentNode.setRight(targetNode.getRight());
                        }
                    }
                }
            }
        }

        //查找父结点
        public TreeNode findParentNode(int value) {
            if (root == null) {
                return null;
            } else {
                return root.findParentNode(value);
            }
        }

        private int findMinValueOfTargetNodeRightTree(TreeNode targetNode) {
            TreeNode rightNode = targetNode.getRight();
            // 右子树的左子节点为空,则右子树的根节点就是最小值
            if (rightNode.getLeft() == null) {
                // 右子树只有一个根节点. 删除根节点 即用null 顶替
                if (rightNode.getRight() == null) {
                    targetNode.setRight(null);
                } else {
                    //右子树还有右子节点 ,删除根节点,即用右子节点去顶替
                    targetNode.setRight(rightNode.getRight());
                }
                return rightNode.getValue();
            } else {
                // 否则,左递归循环遍历, 直到找到右子树 最左边的节点.
                TreeNode currentNode = rightNode.getLeft();
                TreeNode preNode = rightNode;
                while (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                    preNode = preNode.getLeft();
                }
                preNode.setLeft(null);
                return currentNode.getValue();
            }
        }

        public int delRightTreeMin(TreeNode treeNode) {
            TreeNode target = treeNode;
            //循环的查找左子节点，就会找到最小值
            while (target.left != null) {
                target = target.left;
            }
            //这时 target就指向了最小结点
            //删除最小结点
            deleteNode(target.value);
            return target.value;
        }
    }

    @Setter
    @Getter
    private static class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "TreeNode [value=" + value + "]";
        }


        //添加结点的方法
        //递归的形式添加结点，注意需要满足二叉排序树的要求
        public void add(TreeNode newNode) {
            //判断传入的结点的值，和当前子树的根结点的值关系
            if (newNode.value < this.value) {
                //如果当前结点左子结点为null
                if (this.left == null) {
                    this.left = newNode;
                } else {
                    //递归的向左子树添加
                    this.left.add(newNode);
                }
            } else { //添加的结点的值大于 当前结点的值
                if (this.right == null) {
                    this.right = newNode;
                } else {
                    //递归的向右子树添加
                    this.right.add(newNode);
                }
            }
            // 每次添加一个新的节点 之后都需要进行二叉排序树的平衡操作
            // 原因: 如果一个数列{1,2,3,4,5,6}, 则该数列对应的二叉排序树的左子树全部都为空.
            // 此刻 ,虽然添加节点效率没有问题, 但是查询节点效率明显降低
            // (因为要一个一个节点进行比较, 并且每个节点还要比较其左子节点,比单链表还慢)
            // 解决方案: 平衡二叉树: 即它的左子树和右子树的高度差 <= 1, 并且左子树和右子树也是一个平衡二叉树.

            // 右子树高度 比左子树高度 大于1 ,向左旋转
            if (rightTreeHeight() - leftTreeHeight() > 1) {
                // 如果当前根节点的右子树的左子树  > 当前根节点的右子树的右子树的高度
                if (right != null && right.leftTreeHeight() > right.rightTreeHeight()) {
                    //先对右子结点进行右旋转
                    right.rightRotate();
                    leftRotate();
                } else {
                    leftRotate();
                }
            }
            // 左子树高度 比右子树高度 大于1 ,向右旋转
            if (leftTreeHeight() - rightTreeHeight() > 1) {
                if (left != null && left.rightTreeHeight() > left.leftTreeHeight()) {
                    left.leftRotate();
                    rightRotate();
                } else {
                    rightRotate();
                }
            }
        }

        private void rightRotate() {
            TreeNode newTreeNode = new TreeNode(value);
            newTreeNode.setRight(right);
            newTreeNode.setLeft(left.right);

            value = left.getValue();
            right = newTreeNode;
            left = left.left;
        }

        private void leftRotate() {
            // 创建一个新节点newNode ,值为当前根节点的值
            TreeNode newTreeNode = new TreeNode(value);
            // 新节点的左子树 为 根节点的左子树 newNode.left = left
            newTreeNode.setLeft(left);
            // 新节点右子树 为 根节点右子树的左子树 newNode.right = right.left
            newTreeNode.setRight(right.left);
            // 根节点的值设置为 右子节点的值 value =  right.value
            value = right.getValue();
            // 根节点的左子树 为新节点 left = newTreeNode
            left = newTreeNode;
            // 根节点的右子树 为 右子树的右子节点 right = right.getRight()
            right = right.right;
        }

        // 当前节点为根节点的树的高度: 左子树或者右子树的高度 + 1
        public int getCurrentNodeAsRootTreeHeight() {
            return Math.max(left == null ? 0 : left.getCurrentNodeAsRootTreeHeight(),
                    right == null ? 0 : right.getCurrentNodeAsRootTreeHeight()) + 1;
        }

        public int leftTreeHeight() {
            if (left == null) {
                return 0;
            } else {
                return left.getCurrentNodeAsRootTreeHeight();
            }
        }

        public int rightTreeHeight() {
            if (right == null) {
                return 0;
            } else {
                return right.getCurrentNodeAsRootTreeHeight();
            }
        }

        //中序遍历
        public void infixOrder() {
            if (this.left != null) {
                this.left.infixOrder();
            }

            System.out.println(this);

            if (this.right != null) {
                this.right.infixOrder();
            }
        }

        public TreeNode findTargetNode(int findValue) {
            // 要找的值和目标值相同
            if (findValue == this.value) {
                return this;
            } else if (findValue < this.value) {
                // 要找的值 小于 当前节点的值, 去左边找. (如果左边没有,则返回null. 如果左边有,则左递归寻找)
                if (this.getLeft() != null) {
                    return this.getLeft().findTargetNode(findValue);
                } else {
                    return null;
                }
            } else {
                if (this.getRight() != null) {
                    return this.getRight().findTargetNode(findValue);
                } else {
                    return null;
                }
            }
        }

        public TreeNode findParentNode(int findValue) {
            // 当前节点就是父节点. 则返回当前节点
            if ((this.getLeft() != null && this.getLeft().getValue() == findValue) ||
                    (this.getRight() != null && this.getRight().getValue() == findValue)) {
                return this;
            } else {
                // 当前节点不是父节点 && 要找的值< 当前节点的值  && 当前节点有左子节点
                // 左递归寻找.
                if (findValue < this.getValue() && this.getLeft() != null) {
                    return this.getLeft().findParentNode(findValue);
                } else if (findValue >= this.getValue() && this.getRight() != null) {
                    return this.getRight().findParentNode(findValue);
                } else {
                    return null;
                }
            }
        }
    }
}
