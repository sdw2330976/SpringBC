package com.sdw.soft.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * Created by shangyd on 17/3/1.
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        SpringApplication bootstrap = new SpringApplication(DemoApplication.class);
        bootstrap.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> aClass, PrintStream printStream) {
                //change  ASCII Arts char
            }
        });
//        bootstrap.setBanner(new ResourceBanner(new ClassPathResource("banner.txt")));
        bootstrap.setBannerMode(Banner.Mode.CONSOLE);
        bootstrap.run(args);
    }
}
