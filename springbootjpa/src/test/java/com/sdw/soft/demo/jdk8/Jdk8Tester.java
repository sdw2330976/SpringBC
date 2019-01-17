package com.sdw.soft.demo.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Jdk8Tester {


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        Jdk8Tester tester = new Jdk8Tester();
        Collections.sort(list, (a1, a2) -> a1.compareTo(a2));
        int sum = 10;
        MatchOperator matchOperator = (a, b) -> a + b + sum;
        int operate = matchOperator.operate(1, 2);
        System.out.println(operate);

        Car car = Car.create(Car::new);
        List<Car> cars = Arrays.asList(car);
        cars.forEach(Car::collide);
        cars.forEach(Car::repair);
        Car instance = Car.create(Car::new);
        cars.forEach(instance::follow);

    }

    @FunctionalInterface
    interface Animal {
        void say();

        default void test1(){
            System.out.println("test");
        }
    }

    interface MatchOperator{
        int operate(int a, int b);
    }

    static class Car{
        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public static void collide(final Car car) {
            System.out.println("Collide "+car.toString());
        }

        public void follow(final Car anothor) {
            System.out.println("Following the " + anothor.toString());
        }

        public void repair() {
            System.out.println("Repaired " + this.toString());
        }
    }
}
