package DataStructures.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {

    public static void main(String[] args) {
        // 为了方便，将字符之间使用空格隔开了
        String suffixExpression = "3 4 + 5 × 6 -";
        /**
         * 思路
         * 1、先将3 4 + 5 × 6 -放入到ArrayList中
         * 2、将ArrayList传递给一个方法，遍历ArrayList配合栈完成计算
         * */
        List<String> list = getListString(suffixExpression);
        int res = calculate(list);
        System.out.println(res);

        // s = 1+((2+3)×4)-5
        List<String> ls = toInfixExpressionList("1+((2+3)×4)-5");
        System.out.println(ls);

        System.out.println("测试一下中缀转后缀");
        String expression = "1+((2+3)*4)-5";
        List<String> infixExp = toInfixExpressionList(expression);
        List<String> suffixExp = parseSuffixExpressionList(infixExp);
        for (String item : suffixExp) {
            System.out.printf(item+"\t");
        }
    }
    // 将逆波兰表达式放入到arraylist中
    public static List<String> getListString(String suffixExpression) {
        List<String> list = new ArrayList<>();
        String[] splits = suffixExpression.split(" ");
        for (String item : splits) {
            list.add(item);
        }
        return list;
    }
    // 完成对逆波兰表达式的运算
    /**
     * - 从左至右扫描，将3和4压入堆栈
     * - 遇到 + 运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈
     * - 将5入栈
     * - 接下来是 × 运算符，因此弹出 5 和 7，计算出 7×5 = 35，将35入栈
     * - 将 6 入栈
     * - 最后是 - 运算符，计算出 35 - 6 的值，即为29，由此得出最终结果
     * */
    public static int calculate(List<String> list) {
        Stack<String> stack = new Stack<>();  // 规定栈中存放的是字符串
        // 遍历list
        for (String item : list) {
            if (item.matches("\\d+")) {   // 正则表达式，匹配多位数
                stack.push(item);
            }else {
                // pop出两个数，进行运算，将运算结果压入栈
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int result = 0;
                if (item.equals("+")) {
                    result = num1 + num2;
                }else if (item.equals("-")) {
                    result = num2 - num1;
                }else if (item.equals("×")) {
                    result = num1 * num2;
                }else if (item.equals("/")) {
                    result = num2 / num1;
                }else {
                    throw new RuntimeException("运算符有误~~~");
                }
                stack.push("" + result);  // 将int类型的转化成string类型的简单写法
            }
        }
        return Integer.parseInt(stack.pop());  // 最终栈中只剩下一个元素，就是最终结果
    }


    // 将中缀表达式放入到ArrayList中
    public static List<String> toInfixExpressionList(String s) {
        List<String> list = new ArrayList<>(); // 存放中缀表达式的内容
        int i = 0;  // 遍历数组的指针
        char c;
        String str;  // 用来进行字符的拼接
        do {
            c = s.charAt(i);
            if (c < 48 || c > 57) {   // c 是符号
                list.add("" + c);  // 直接将字符转成字符串
                i++;
            }else {
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {  // 这里必须这么写c = s.charAt(i)，不然他会卡在1个数这里
                    str += c;
                    i++;
                }
                list.add(str);
            }
        }while(i < s.length());
        return list;
    }

    // 将中缀表达式转化成后缀表达式
    public static List<String> parseSuffixExpressionList(List<String> list) {
        Stack<String> s1 = new Stack<>();  // 符号栈
        List<String> s2 = new ArrayList<>();  // 存放中间结果
        for (String item : list) {
            if (item.matches("\\d+")) {   // 是一个数字
                s2.add(item);
            }else if (item.equals("(")) {
                s1.push(item);
            }else if (item.equals(")")) {
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();  // 把（ 也弹出去，别忘了！！！
            }else {            // 操作符,需要比较优先级
               while (s1.size() != 0 && priority(s1.peek()) >= priority(item)) {    // s1栈顶运算符的优先级大于等于当前运算符
                   s2.add(s1.pop());
               }
               s1.push(item);
            }
        }
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;    // 不需要再逆序输出了，因为是存放在list中了，桉顺序输出即可
    }

    // 定义一个方法比较优先级
    public static int priority(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = 1;
                break;
            case "-":
                result = 1;
                break;
            case "*":
                result = 2;
                break;
            case "/":
                result = 2;
                break;
        }
        return result;
    }
}
