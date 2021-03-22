package DataStructures.com.atguigu.stack;

public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(4);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.show();
    }
}

class ArrayStack {
    private int maxSize;  // 栈的容量
    private int[] stack;    // 用数组来模拟站
    private int top = -1; // 栈顶默认为 -1

    public ArrayStack(int maxSize) {  // 创建一个栈的构造器
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈
    public void push(int value) {
        if (isFull()) {
            return;
        }
        top++;
        stack[top] = value;
    }

    // 出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空~~~");  // 有返回值就可以这样，返回一个异常
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 遍历栈,遍历时需要从栈顶开始显示数据
    public void show() {
        if (isEmpty()) {
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }
}
