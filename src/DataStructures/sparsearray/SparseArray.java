package DataStructures.sparsearray;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SparseArray {

    public static void main(String[] args) throws IOException {
        // 创建一个原始的二维数组 11*11
        // 0表示没有棋子，1表示黑子，2表示白子
        int[][] chessArr = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        System.out.println("原始的二维数组如下~~~");
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
        // 将二维数组转化成稀疏数组
        int numcount = 0;  // 记录原始二维数组中不是0的个数
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[0].length; j++) {
                if (chessArr[i][j] != 0) {
                    numcount++;
                }
            }
        }
        // System.out.println(numcount);
        int[][] sparseArr = new int[numcount+1][3];   // 定义这个稀疏数组
        sparseArr[0][0] = chessArr.length;
        sparseArr[0][1] = chessArr[0].length;
        sparseArr[0][2] = numcount;    // 稀疏数组的第一行分别存放 原始数组的 行、列、以及非0个数
        int index = 0;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[0].length; j++) {
                if (chessArr[i][j] != 0) {
                    index++;
                    sparseArr[index][0] = i;
                    sparseArr[index][1] = j;
                    sparseArr[index][2] = chessArr[i][j];
                }
            }
        }
        System.out.println("得到的稀疏数组为~~~");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n",sparseArr[i][0],sparseArr[i][1],sparseArr[i][2]);
        }
        System.out.println("由稀疏数组恢复成原数组~~~");
        int row = sparseArr[0][0];
        int column = sparseArr[0][1];
        int sumNum = sparseArr[0][2];
        int[][] chessArr1 = new int[row][column];
        for (int i = 1; i <= sumNum; i++) {
            chessArr1[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        for (int[] rows : chessArr1) {
            for (int data : rows) {
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

        // 将稀疏数组村到磁盘
        System.out.printf("将稀疏数组存储到磁盘");

        File f = new File("sparseArray.data");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int[] Row : sparseArr) {
            for (int Data : Row) {
                bw.write(Data + "\t");
            }
            bw.write("\n");
            bw.flush();  // 强制把数据输出，缓存区就清空了，最后再关闭读写流调用close()就完成了。
        }
        bw.close();

        // 从磁盘中读取出稀疏数组
        File file = new File("sparseArray.data");
//        BufferedReader br = null;
        List<Integer> list = new ArrayList<>();
        FileReader fd = new FileReader(file);
        BufferedReader br = new BufferedReader(fd);
        String line;
        while ((line = br.readLine()) != null) {
            String[] str = line.split("\t");
            for (int i = 0; i < str.length; i++) {
                list.add(Integer.parseInt(str[i]));
            }
        }
        fd.close();
        // 利用磁盘中读取出来的数据恢复稀疏数组
        int[][] sparseArr1 = new int[list.get(2)+1][3];
        int count = 0;
        // System.out.println(list.size());
        for (int i = 0; i < list.size(); i += 3) {
            sparseArr1[count][0] = list.get(i);
            sparseArr1[count][1] = list.get(i+1);
            sparseArr1[count][1] = list.get(i+2);
            count++;
        }
        System.out.println("从磁盘中读出的稀疏数组恢复~~~");
        for (int[] Rows : sparseArr1) {
            for (int data1 : Rows) {
                System.out.printf("%d\t",data1);
            }
            System.out.println("\n");
        }
    }
}
