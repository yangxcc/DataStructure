### 栈Stack

栈是限制插入和删除操作只能在同一端进行的特殊线性表。**允许插入和删除的一端称为栈顶**，是变化的，而固定的一端称为栈底

#### **数组模拟栈**

**思路**

- 定义一个变量 top 作为栈顶，初始化为-1

- 入栈：当有数据加入到栈时，

  ```java
  top++；
  stack[top] = data；
  ```

- 出栈：先把栈顶的值拿到，

  ```java
  int value = stack[top]；
  top--； 
  return value;
  ```

#### 用栈实现综合计算器思路

- 首先创建两个栈：数栈和符号栈
- 通过一个index（索引），来扫描这个表达式字符串
- 再扫描过程中，如果发现是一个数字，就直接加入到数栈
- 如果扫描到了符号，
  - 如果发现当前的符号栈为空，就直接如符号栈
  - 如果当前的符号栈不为空，就需要对符号的优先级进行比较：
    - 如果当前操作符的优先级小于等于符号栈栈顶符号的优先级，那么就需要在数栈中pop出两个数，将符号栈栈顶的符号pop出来，计算出的结果push进数栈，同时将当前的操作符push进符号栈
    - 如果当前操作符的优先级大于符号栈栈顶符号的优先级，那么直接push进符号栈
- 当表达式扫描完毕，按顺序从数栈和符号栈中分别pop出数字和运算符，并进行运算
- 最后在数栈中只有一个数字，就是表达式的结果

```java
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
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
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
                    if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))) {
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
```





### 前缀、中缀、后缀表达式

- 前缀表达式又叫做波兰表达式，**特点是运算符位于操作符之前**，如`-✖+3456`

  - 前缀表达式在计算机中的求值：**从右至左扫描表达式**，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对他们做相应的计算（栈顶元素和次顶元素），并将结果入栈；重复上述过程直到表达式最左端，最后运算出的值即为表达式的结果
  - 例如：`(3+4)✖5-6`对应的前缀表达式就是`-✖+3456`，针对前缀表达式的步骤如下：
    - 从右至左扫描，将6、5、4、3依次压入栈中
    - 遇到`+`运算符，因此弹出3和4（3为栈顶元素，4为次顶元素），计算出`3+4`的值，得`7`，在将`7`入栈
    - 接下来是`✖`运算符，因此弹出`7`和`5`，计算`7×5=35`，将35入栈
    - 最后是`-`运算符，计算出`35-6=29`，由此得出最终结果
    - 可以看出，**在前缀表达式运算中，不用考虑优先级了**

- 中缀表达式，就是我们最熟悉的形式，虽然中缀表达式在人看来很容易理解，但是对于计算机而言，他是很难理解的，比如它需要判断运算符的优先级等，在实际计算时，往往要先把中缀表达式转化成前缀/后缀表达式，一般是后缀表达式

- 后缀表达式又叫做逆波兰表达式，与前缀表达式类似，只是运算符在后面

  - 后缀表达式的计算机求值：从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对他们做相应计算（次顶元素和栈顶元素），并将结果入栈，重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
  - 例如：`(3+4)✖5-6`对应的后缀表达式就是`34+5×6-`，针对后缀表达式的步骤如下：
    - 从左至右扫描，将3和4压入堆栈
    - 遇到 + 运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈
    - 将5入栈
    - 接下来是 × 运算符，因此弹出 5 和 7，计算出 7×5 = 35，将35入栈
    - 将 6 入栈
    - 最后是 - 运算符，计算出 35 - 6 的值，即为29，由此得出最终结果

  ```java
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
  }
  ```

  很显然，通过后缀表达式来求值要简单得多，因为相比于中缀表达式，后缀表达式更适合计算机理解

**中缀表达式转后缀表达式**

步骤：

```
1、初始化两个栈，运算符栈s1和存储中间结果的栈s2
2、从左到右扫描中缀表达式
3、遇到操作数时，将其压入栈s2
4、遇到运算符时，比较其与s1栈顶运算符的优先级：
  （1）如果s1为空，或是栈顶运算符是“（”，则直接将此运算符入栈s1
  （2）若优先级比栈顶运算符的高，直接将此运算符压入栈s1
  （3）若优先级比栈顶运算符低，将s1栈顶元素[运算符]弹出，并压入s2，再次转到4.1与s1新的栈顶元素比较优先级
5、遇到括号时：
  （1）如果是左括号“（”，直接压入栈s1
  （2）如果是右括号“）”，则弹出s1栈顶的运算符，并压入s2中，直到遇到左括号“（”为止，这样也是丢弃了一对括号
6、重复步骤 2-5，直到到达表达式的最右端
7、将s1中剩余的运算符全部压入s2中
8、依次弹出s2中的元素并输出，输出结果的逆序即为后缀表达式
```

优化：从上面的步骤可以看出，栈`s2`只有在最后输出结果的时候才有弹栈操作，因为`s2`完全可以用一个list来代替，而且list来是有序的，输出的顺序即为插入的顺序

