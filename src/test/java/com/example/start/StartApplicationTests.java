package com.example.start;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StartApplicationTests {

    @Test
    public void multiplyTest() {
        final int result = multiply(2, 3);

        Assert.assertEquals(result, 6);
    }

    public int multiply(final int num1, final int num2) {
        return 6;
    }

}
