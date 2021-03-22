package DataStructures.stack;

public class Calculator {
    public static void main(String[] args) {
        String expression = "32+2*6-2";
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        int index = 0, num1, num2, oper, result = 0;
        char ch = ' '; // 将每次扫描得到的char保存到Stack中
        String numCount = ""; // 用来处理多位数
        while (true) {
            // 一次得到expression中的每一个字符
            ch = expression.substring(index,index+1).charAt(0);
            if (operStack.isOper(ch)) {  // 先判断一下是不是运算符
                if (operStack.isEmpty()) {
                    operStack.push(ch);
                }else {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) { // 当前运算符与栈顶运算符比较优先级
                        // 弹出两个数与符号栈栈顶的符号进行运算
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        result = numStack.calculate(num1,num2,oper);
                        numStack.push(result);
                        operStack.push(ch);
                    }else {
                        operStack.push(ch);
                    }
                }
            }else {
                /**
                 * 一定要注意：字符'1'不等于数字1，对应于ASCII表，字符1对应的是数字49~~
                 * */
                // numStack.push(ch - 48); // 这样写只能够算单位数
                numCount += ch;
                if (index >= expression.length()-1) {  // 已经遍历到了最后一位
                    numStack.push(Integer.parseInt(numCount));
                }else {
                    if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))) { // 后一位是运算符了
                        numStack.push(Integer.parseInt(numCount));
                        numCount = ""; // 一定要清空！！！！！！！！
                    }
                }
            }
            index++;
            if (index >= expression.length()) {
                break;
            }
        }
        // 上述代码是将整个字符串都遍历完毕了
        // 接下来的操作就是按顺序将数栈和符号栈中的数据pop出来
        while(true) {
            if (operStack.isEmpty()) {
                break;
            }else {
                num1 = numStack.pop();
                num2 = numStack.pop();
                oper = operStack.pop();
                result = numStack.calculate(num1,num2,oper);
                numStack.push(result);
            }
        }
        System.out.printf("表达式%s=%d",expression,result);
    }
}

class ArrayStack2 {
    private int maxSize;  // 栈的容量
    private int[] stack;    // 用数组来模拟站
    private int top = -1; // 栈顶默认为 -1

    public ArrayStack2(int maxSize) {  // 创建一个栈的构造器
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    // 返回操作符的优先级，这个优先级需要自定义
    // 规定数字越大， 优先级越高
    public int priority(int oper) {   // 在java中，char和int可以混用
        if (oper == '*' || oper == '/') {
            return 1;
        }else if (oper == '+' || oper == '-'){
            return 0;
        }else{
            return -1;  // 暂定只有加减乘除，-1表示运算符不合法
        }

    }

    // 判断是不是一个运算符
    public boolean isOper(char oper) {
        return oper == '+' || oper == '-' || oper == '*' || oper == '/';
    }

    // 定义一个运算方法
    public int calculate(int num1, int num2, int oper) {  // int和char可以混用
        int result = 0;
        switch (oper){
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num2 - num1;  // 这里一定要是num2-num1，后出栈的减去先出栈的，和栈的性质有关
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num2 / num1;
                break;
        }
        return result;
    }

    // 定义一个方法，判断栈顶符号的优先级
    public int peek() {
       return stack[top];
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
