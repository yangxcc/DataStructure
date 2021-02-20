package DataStructures.linkedlist;

public class DoubleLinkedListDemo {

    public static void main(String[] args) {

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

        HeroNode2 node1 = new HeroNode2(1,"松江","及时雨");
        HeroNode2 node2 = new HeroNode2(2,"卢俊义","玉麒麟");
        HeroNode2 node3 = new HeroNode2(3,"无用","智多星");
        HeroNode2 node4 = new HeroNode2(4,"公孙胜","入云龙");

        doubleLinkedList.addNode(node1);
        doubleLinkedList.addNode(node2);
        doubleLinkedList.addNode(node3);
        doubleLinkedList.addNode(node4);

        doubleLinkedList.showList(doubleLinkedList.getHead());

        HeroNode2 newnode = new HeroNode2(4,"李崇","豹子头");
        doubleLinkedList.updateList(newnode);
        doubleLinkedList.showList(doubleLinkedList.getHead());

        doubleLinkedList.deleteNode(3);
        doubleLinkedList.showList(doubleLinkedList.getHead());

        doubleLinkedList.showListByParam(4);

        System.out.println("-----------------分隔符------------------");

        DoubleLinkedList doubleLinkedList1 = new DoubleLinkedList();

        doubleLinkedList1.addNodeByOrder(node2);
        doubleLinkedList1.addNodeByOrder(node1);
        doubleLinkedList1.addNodeByOrder(node4);
        doubleLinkedList1.addNodeByOrder(node3);

        doubleLinkedList1.showList(doubleLinkedList1.getHead());
    }
}

class DoubleLinkedList {
    // 初始化一个头节点
    HeroNode2 head = new HeroNode2(0,"","");

    public HeroNode2 getHead() {
        return head;
    }

    /**
     * 无序添加
     * 1、先找到表尾位置
     * 2、插入到表尾，与单链表不同的是又增加了一个前驱
     * */
    public void addNode(HeroNode2 node2) {
        HeroNode2 temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node2;
        node2.pre = temp;
    }

    public void addNodeByOrder(HeroNode2 node2) {
        HeroNode2 temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > node2.no) {
                break;
            }else if (temp.next.no == node2.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.println("已存在");
        }else {
            node2.next = temp.next;
            temp.next = node2;
            temp.next.pre = node2;
            node2.pre = temp;
        }

    }

    /**
     * 展示双向链表
     * */
    public void showList(HeroNode2 head) {
        if (head.next == null) {
            System.out.println("双向链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }

    }

    /**
     * 修改双向链表
     * */
    public void updateList(HeroNode2 heroNode2) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                System.out.println("链表中没有找到");
                break;
            }
            if (temp.no == heroNode2.no) {
                temp.name = heroNode2.name;
                temp.nickname = heroNode2.nickname;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 创建一个方法来删除节点
     * */
    public void deleteNode (int no) {
        if (head.next == null) {
            System.out.println("链表为空，没有可删除的");
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.pre.next = temp.next;
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        }else {
            System.out.printf("链表中不存在序号为%d的节点",no);
        }
    }

    public void showListByParam(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        HeroNode2 temp = head.next;   // temp代表的是第一个有数据的节点
        while(true) {
            if (temp == null) {
                System.out.println("查找不到");
                break;
            }
            if (temp.no == no) {
                System.out.println(temp);   // 因为重写了toString，所以直接输出就已经是格式化了
                break;
            }
            temp = temp.next;
        }
    }

}


// 定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode2 {
    public int no;   // 编号
    public String name; // 姓名
    public String nickname;  // 绰号
    public HeroNode2 next;  // 指针，指向下一个
    public HeroNode2 pre;   // 指针，指向上一个

    // 创建一个构造器
    public HeroNode2(int no,String name,String nickname) {
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
