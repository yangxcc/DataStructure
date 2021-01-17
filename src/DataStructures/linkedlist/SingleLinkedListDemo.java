package DataStructures.linkedlist;

import java.util.Stack;

/**
 *
 * 使用带有 head 头的单链表实现，
 * 实现对象的增删改查
 *
 * */

public class SingleLinkedListDemo {


    public static void main(String[] args) {
        // 创建节点
        System.out.println("无序的插入");

        HeroNode node1 = new HeroNode(1,"宋江","及时雨");
        HeroNode node2 = new HeroNode(2,"卢俊义","玉麒麟");
        HeroNode node3 = new HeroNode(3,"吴用","智多星");
        HeroNode node4 = new HeroNode(4,"林冲","豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
//        singleLinkedList.addNode(node1);
//        singleLinkedList.addNode(node2);
//        singleLinkedList.addNode(node3);
//        singleLinkedList.addNode(node4);

        singleLinkedList.showList();

        System.out.println("有序的插入");

        singleLinkedList.addNodeByOrder(node1);
        singleLinkedList.addNodeByOrder(node4);
        singleLinkedList.addNodeByOrder(node3);
        singleLinkedList.addNodeByOrder(node2);

        HeroNode node = new HeroNode(1,"怂货","菜鸡");
        singleLinkedList.updateNode(node);
        singleLinkedList.deleteNode(2);

        singleLinkedList.showList();

        System.out.println(singleLinkedList.getListLength(singleLinkedList.getHead()));

        System.out.println(singleLinkedList.findIndexNode(singleLinkedList.getHead(),3));

//        System.out.println("测试单链表反转");
//        singleLinkedList.reverseList(singleLinkedList.getHead());
//        singleLinkedList.showList();

        System.out.println("测试单链表逆序打印");
        singleLinkedList.reversePrint(singleLinkedList.getHead());

        System.out.println("测试合并有序单链表");
        SingleLinkedList singleLinkedList1 = new SingleLinkedList();
        HeroNode node5 = new HeroNode(2,"al","ss");
        HeroNode node6 = new HeroNode(5,"al","ss");
        HeroNode node7 = new HeroNode(6,"al","ss");
        HeroNode node8 = new HeroNode(7,"al","ss");
        singleLinkedList1.addNodeByOrder(node5);
        singleLinkedList1.addNodeByOrder(node6);
        singleLinkedList1.addNodeByOrder(node7);
        singleLinkedList1.addNodeByOrder(node8);
        singleLinkedList1.showList();
        singleLinkedList.showList();

        HeroNode res = singleLinkedList1.mergeList(singleLinkedList1.getHead(),singleLinkedList.getHead());
        singleLinkedList1.showListByParam(res);

    }

}

// 定义单链表SingleLinkedList
class SingleLinkedList {

    // 先初始化一个头节点，头节点不能动
    HeroNode head = new HeroNode(0,"","");

    public HeroNode getHead() {
        return head;
    }

    /**
     * 创建一个方法添加结点到链表
     * 思路：先不考虑插入的顺序，即将新节点从表尾插入
     * 1、找到当前链表的最后节点
     * 2、将这个节点的 next 指向新的节点
     * */
    public void addNode (HeroNode node) {
        HeroNode temp = head;   // 为了保持head不懂，需要通过一个辅助变量来进行移动
        while (true) {
            // 找到了链表的最后
            if (temp.next == null) {
                break;
            }
            // 遍历，指针后移
            temp = temp.next;
        }
        temp.next = node;
        // node.next = null;  // 不写就是默认了null
    }

    /**
     * 考虑编号，按照顺序进行插入
     * 即不管按照什么顺序插入，都需要按照编号排好序
     * 如果已经存在了，提示信息
     * 重要的是找到插入节点的位置
     * */
    public void addNodeByOrder (HeroNode Node) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > Node.no) {  // temp指向插入点的前面
                break;
            }else if (temp.next.no == Node.no) {
                flag = true;  // 说明该节点已经存在
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("想要插入的英雄序号%d，已经存在了",Node.no);
        }else {
            Node.next = temp.next;
            temp.next = Node;
        }

    }

    /**
     * 创建一个方法来删除节点
     * */
    public void deleteNode (int no) {
        if (head.next == null) {
            System.out.println("链表为空，没有可删除的");
        }
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        }else {
            System.out.printf("链表中不存在序号为%d的节点",no);
        }
    }

    /**
     * 创建一个方法来修改节点的信息
     * 根据no，修改name和nickname
     * */
    public void updateNode(HeroNode node) {

        if (head.next == null) {
            System.out.println("链表为空，没有可修改的");
            return;
        }

        HeroNode temp = head.next;
        boolean flag = false;
        while(true) {
            if (temp == null) {
                // System.out.println("链表中没有这个节点");
                break;
            }
            if (temp.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = node.name;
            temp.nickname = node.nickname;
        }else {
            System.out.printf("链表中没有标号为%这个节点",node.no);
        }
    }

    /**
     * 显示链表,遍历
     * */
    public void showList() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        HeroNode temp = head.next;   // temp代表的是第一个有数据的节点
        while(true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);   // 因为重写了toString，所以直接输出就已经是格式化了
            temp = temp.next;
        }
    }

    public void showListByParam(HeroNode head) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        HeroNode temp = head.next;   // temp代表的是第一个有数据的节点
        while(true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);   // 因为重写了toString，所以直接输出就已经是格式化了
            temp = temp.next;
        }
    }

    /**
     * 获取单链表中有效节点的个数
     * */
    public int getListLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

    /**
     * 查找单链表中倒数第k个节点
     * index 表示倒数第几个
     * */
    public HeroNode findIndexNode(HeroNode head,int index) {
        if (head.next == null) {
            return null;
        }
        int size = getListLength(head);
        if (index <= 0 || index > size) {
            return null;
        }
        HeroNode temp = head.next;
        for (int i = 0; i < size - index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     * 单链表反转
     * 思路：生成一个头结点，把原单链表上的节点分别摘下来，加到新的头结点后
     * 每次加入都是要加入到新的头结点的后面
     * */
    public void reverseList(HeroNode head) {
        if (head.next == null || head.next.next == null) {  // 如果单链表中没有节点或者只有一个
            return;
        }
        HeroNode reverseHead = new HeroNode(0,"","");  // 生成反转单链表的头结点

        HeroNode cur = head.next;
        HeroNode next = null;   // 很重要！！指向当前节点的下一个节点

        // 遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表的最前端
        while (cur != null) {
            next = cur.next;   // 先保存当前节点的下一个节点
            cur.next = reverseHead.next;   // 先断开链
            reverseHead.next = cur;   // 将链连接
            cur = next;   // 移动cur
        }
        head.next = reverseHead.next;   // 指向反转后的链表
    }

    /**
     * 反向打印链表
     * */
    public void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;
        }
        HeroNode cur = head.next;
        Stack<HeroNode> stack = new Stack<>();
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 合并两个有序的链表
     * */
    public HeroNode mergeList(HeroNode head1,HeroNode head2) {
        if (head1.next == null && head2.next == null) {
            return null;
        }else if (head1.next == null) {
            return head2;
        }else if (head2.next == null) {
            return head1;
        }
        HeroNode res = new HeroNode(0,"","");
        HeroNode cur = res;
        System.out.println("即将进入循环");
        while (head1.next != null && head2.next != null) {
            if (head1.next.no <= head2.next.no) {
                HeroNode temp = head1.next;
                cur.next = temp;
                head1 = head1.next;
            }else {
                HeroNode temp = head2.next;
                cur.next = temp;
                head2 = head2.next;
            }
            cur = cur.next;
        }
        System.out.println("我能出循环");
        if (head1.next == null) {
            cur.next = head2;
        }
        if (head2.next == null) {
            cur.next = head1;
        }
        return res;
    }
}


// 定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode {
    public int no;   // 编号
    public String name; // 姓名
    public String nickname;  // 绰号
    public HeroNode next;  // 指针，指向下一个

    // 创建一个构造器
    public HeroNode(int no,String name,String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    // 为了显示方便，重写一下tostring方法

    @Override
    public String toString() {
//        return "HeroNode{" +
//                "no=" + no +
//                ", name='" + name + '\'' +
//                ", nickname='" + nickname + '\'' +
//                ", next=" + next +
//                '}';
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}


