package DataStructures.linkedlist;

public class JosephuDemo {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.add(5);
        circleSingleLinkedList.showBoy();
        System.out.println("测试一下小孩出拳的顺序");
        circleSingleLinkedList.countBoy(1,2,5);
    }
}

class CircleSingleLinkedList {
    //先定义第一个节点
    private Boy first = null;

    /**
     *
     * @param nums：表示添加节点的个数
     */
    public void add(int nums) {
        // 先做一个数据校验
        if (nums < 1) {
            System.out.printf("nums=%d",nums + "不合法");
            return;
        }
        // 定义一个curBoy，始终指向当前的节点
        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            // 第一个节点比较特殊,因为要让first指向它，而且之后first也不能再动了
            if (i == 1) {
                first = boy;
                first.setNext(first);  // 指向自己，构成环
                curBoy = first;
            }else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;   // curBoy 后移
            }
        }
    }

    /**
     * 当curBoy.next = first时，表示遍历完成
     */
    public void showBoy() {
        // 首先需要判断链表是否为空
        if (first.getNext() == null) {
            System.out.println("链表为空~~~");
            return;
        }
        // 因为first指针不能动，所以我们需要使用一个辅助指针
        Boy curBoy = first;
        while(true) {
            System.out.printf("第%d个小孩\n",curBoy.getId());
            if (curBoy.getNext() == first) {
                break;
            }
//            System.out.printf("第%d个小孩\n",curBoy.getId());  // 这句话应该放在前面，不然最后一个节点遍历不出来
            curBoy = curBoy.getNext();   // curBoy节点后移
        }
    }

    // 根据用户输入，数小孩 ----- 约瑟夫环问题

    /**
     *
     * @param startId 从startId开始数数
     * @param countNum 数到countNum的小孩出圈
     * @param nums 共有nums个小孩
     */
    public void countBoy(int startId, int countNum, int nums) {
        // 先对数据进行校验
        if (startId < 0 || startId > nums || countNum > nums) {
            System.out.println("输入的数据不合法~~~");
            return;
        }
        // 创建辅助指针，帮助完成小孩出圈
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {  // 让helper指向单项环形链表的最后一个节点
                break;
            }
            helper = helper.getNext();
        }
        // 小孩报数之前，先让first和helper同时移动k-1次
        for (int i = 0; i < startId-1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        while (true) {
            if (helper == first) {  // 此时只有一个节点了
                break;
            }
            // 当小孩报数时，first和helper需要同时移动m-1次
            for (int j = 0; j < countNum-1; j++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 令first指向的节点出圈
            System.out.printf("此时%d号小孩出圈\n",first.getId());
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中的小号编号是%d\n",first.getId());
    }
}


class Boy {
    private int id;
    private Boy next;  // 如果属性变量是私有的，那么就需要设置set、get方法

    public Boy(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}