package DataStructures.queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        // 创建一个队列
        CircleArrayQueue arrayQueue = new CircleArrayQueue(3);   // 初始化一个最大长度为3的队列,但是其能存放的长度是2，maxSize-1
        char key  = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列中取出数据");
            System.out.println("h(head):查看队列头数据");
            key = scanner.next().charAt(0);  // 接受一个字符
            switch (key) {
                case 's':
                    try {
                        arrayQueue.show();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    // scanner.close();  // 不关闭会有一个警告
                    loop = false;
                    break;
                case 'a':
                    try {
                        System.out.println("输入要添加到队列中的数据");
                        int value = scanner.nextInt();
                        arrayQueue.addQueue(value);
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        int value = arrayQueue.getQueue();
                        System.out.printf("从队列中取出的数据是%d\n",value);
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int value = arrayQueue.getFront();
                        System.out.printf("队头数据为%d\n",value);
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
        System.out.println("程序退出~~~");
    }
}

class CircleArrayQueue {
    private int maxSize;  // 队列的最大容量
    private int front;    // 队列头
    private int rear;     // 队列尾
    private int[] arr;    // 数组，用来存储队列中的元素

    // 创建队列的一个构造器
    public CircleArrayQueue(int MaxSize) {
        maxSize = MaxSize;
        arr = new int[maxSize];
//        front = 0;   // 指向的是队列头部
//        rear = 0;    // 指向的是队列尾部的后一个位置
        // 其实front、rear声明之后就默认是0了，所以可以不用写了
    }

    // 判断队列是否满
    public boolean isFull() {
        return front == (rear + 1) % maxSize;
    }

    // 判断队列是否空
    public boolean isEmpty() {
        return front == rear;
    }

    // 添加数据到队列
    public void addQueue(int num) {
        if (isFull()) {
            // System.out.println("队列满，不能加入");
            throw new RuntimeException("队列满，不能加入");
            // return;  // 抛出异常代码行后的代码都不能运行了
        }
        // rear++;  // rear先后移
        arr[rear] = num;
        rear = (rear + 1) % maxSize;
    }

    // 数据出队列
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，不能输出");  // 通过抛异常来处理
        }
        // front++;
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    // 显示队列中的所有数据
    public void show() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，不能输出");
        }
//        for (int i = 0; i < arr.length; i++) {
//            System.out.printf("arr[%d] = %d\n",i,arr[i]);
//        }
        // 从front开始遍历
        for (int i = front; i < front + numCount(); i++) {
            System.out.printf("arr[%d] = %d\n",i % maxSize,arr[i % maxSize]);
        }
    }

    // 显示队列的头部数据，不是取出数据
    public int getFront() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，没有头部数据");
        }
        // return arr[front + 1];  // 注意要加1
        return arr[front];
    }

    // 获取队列中有效数字的个数
    public int numCount() {
        return (rear - front + maxSize) % maxSize;
    }
}
