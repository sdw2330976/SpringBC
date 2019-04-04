package com.sdw.soft.demo.jdk8;

import java.lang.annotation.*;
import java.util.*;
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

        Arrays.asList("1","2","3").forEach(e-> System.out.println(e));

        Optional<String> name = Optional.ofNullable(null);
        boolean present = name.isPresent();
        System.out.println(present);
        name = Optional.of(null);
        System.out.println(name.isPresent());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @Repeatable(Hellos.class)
    @interface Hello {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface Hellos{
        Hello[] value();
    }

    @FunctionalInterface
    interface Animal {
        void say();

        default void test1(){
            System.out.println("test");
        }

        static void t() {
            System.out.println("hello");
        }
    }

    interface MatchOperator{
        @Hello("1")
        @Hello("2")
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
