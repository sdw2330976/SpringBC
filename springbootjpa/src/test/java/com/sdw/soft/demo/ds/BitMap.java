package com.sdw.soft.demo.ds;

/**
 * @author: shangyd
 * @create: 2019-03-06 17:08:03
 **/
public class BitMap {
    private int[] values;
    private int capacity;

    public BitMap(int capacity) {
        this.capacity = capacity;
        values = new int[(capacity >> 5) + 1];
    }


    public void add(int num) {
        int index = num >> 5;
        int position = num & 31;
        values[index] |= 1 << position;
    }

    public void remove(int num) {
        int index = num >> 5;
        int position = num & 31;
        values[index] &= ~(1 << position);
    }

    public boolean contains(int num) {
        int index = num >> 5;
        int position = num & 31;
        return (values[index] & (1 << position)) != 0;
    }

    public static void main(String[] args) {
        BitMap bitMap = new BitMap(16);
        bitMap.add(12);
        System.out.println(bitMap.contains(12));
        bitMap.remove(12);
        System.out.println(bitMap.contains(12));
    }
}
