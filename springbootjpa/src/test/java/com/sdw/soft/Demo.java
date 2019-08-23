package com.sdw.soft;

import java.util.Stack;

/**
 * @author: shangyd
 * @create: 2019-07-17 15:55:49
 **/
public class Demo {

    public static void main(String[] args) {

        int[] arr = new int[]{23, 1, 43, 64, 24, 67, 35, 452, 145};

//        bubbleSort(arr);
//        quickSort(arr, 0, arr.length - 1);
//        heapSort(arr);

        /*for (int a : arr) {
            System.out.println(a);
        }*/

      /*  System.out.println(fibo(11));
        System.out.println(fibonacci(11));

        System.out.println(rec_search(arr, arr.length - 1));
        System.out.println(rec_search(arr));

        generate(3);*/


        System.out.println(checkSymbol("()()"));
    }


    private static boolean checkSymbol(String str) {
        if (str == null) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (!stack.isEmpty() && (c == '(' && stack.peek() == ')')) {
                stack.pop();
            } else {
                return false;
            }
        }
        return true;
    }
    private static void generate(int n) {
        gen("", 0, 0, n);
    }


    private static void gen(String result, int left, int right, int n) {
        if (right == n) {
            System.out.println(result);
            return;
        }
        if (left < n) {
            gen(result + "(", left + 1, right, n);
        }
        if (left > right) {
            gen(result + ")", left, right + 1, n);
        }
    }

    private static int rec_search(int[] arr) {
        int[] result = new int[arr.length];
        result[0] = arr[0];
        result[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < arr.length; i++) {
            int c1 = result[i - 2] + arr[i];
            int c2 = result[i - 1];
            result[i] = Math.max(c1, c2);
        }
        return result[arr.length - 1];
    }

    private static int rec_search(int[] arr, int i) {
        if (i == 0) {
            return arr[0];
        }
        if (i == 1) {
            return Math.max(arr[0], arr[1]);
        }

        int c1 = rec_search(arr, i - 2) + arr[i];
        int c2 = rec_search(arr, i - 1);
        return Math.max(c1, c2);
    }
    private static int fibo(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] arr = new int[n];
        arr[0]=1;
        arr[1] = 1;
        for (int i = 2; i < n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n - 1];
    }
    private static int fibonacci(int i) {
        if (i <= 0) {
            return -1;
        }

        if (i == 1 || i == 2) {
            return 1;
        }
        return fibonacci(i - 1) + fibonacci(i - 2);
    }

    private static void heapSort(int[] arr) {
        buildHeap(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, i, 0);
            heapify(arr, i, 0);
        }
    }

    private static void buildHeap(int[] arr) {
        int last = arr.length - 1;
        int parent = (last - 1) / 2;
        for (int i = parent; i >= 0; i--) {
            heapify(arr, arr.length, i);
        }
    }

    private static void heapify(int[] arr, int len, int item) {
        if (item > len) {
            return;
        }
        int c1 = 2 * item + 1;
        int c2 = 2 * item + 2;
        int max = item;
        if (c1 < len && arr[c1] > arr[max]) {
            max = c1;
        }
        if (c2 < len && arr[c2] > arr[max]) {
            max = c2;
        }
        if (max != item) {
            swap(arr, item, max);
        }
    }
    private static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partition = partition(arr, left, right);
            quickSort(arr, left, partition - 1);
            quickSort(arr, partition + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int index = left + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[left]) {
                swap(arr, index, i);
                index++;
            }
        }
        swap(arr, index - 1, left);
        return index - 1;
    }

    private static void bubbleSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            boolean flag = true;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) {
                return;
            }
        }
    }


    private static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
