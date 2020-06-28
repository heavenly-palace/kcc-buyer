package com.kcc.buyer;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.NumberFormat;


@SpringBootTest
public class KccBuyerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void generatePdfTest(){
        Double rate =12d;
        System.out.println(String.format("%.2f", rate));
    }
}
