package com.xiaomi.plugin.dao;

import com.xiaomi.plugin.service.PubService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class PubDaoTest {

    @Autowired
    private PubDao pubDao;
    @Autowired
    private PubService pubService;

    @Test
    public void testInsertOrUpdate() throws Exception {
        pubService.updatePubLimt("12345678", 3);
      System.out.println(pubService.getPubUsedNumber(3));
//      System.out.println(pubService.getLocalUsedNumber("12345678"));
    }
}