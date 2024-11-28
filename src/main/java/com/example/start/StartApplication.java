package com.example.start;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Test
    public void multiplyTest() {
        final int result = multiply(2, 3);

        Assert.assertEquals(result, 5);
    }

    public int multiply(final int num1, final int num2) {
        return 6;
    }
}
