package com.kcc.buyer;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.NumberFormat;
import java.util.UUID;


@SpringBootTest
public class KccBuyerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void generatePdfTest(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}
