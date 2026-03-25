package com.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 应用程序测试类
 */
@SpringBootTest  // Spring Boot 测试注解
public class AppTest {

    @Test
    public void testApp() {
        // 简单的测试
        assertTrue(true);
        System.out.println("测试运行成功");
    }
}