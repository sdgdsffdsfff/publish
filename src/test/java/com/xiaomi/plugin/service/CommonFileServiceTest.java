package com.xiaomi.plugin.service;

import com.xiaomi.plugin.model.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class CommonFileServiceTest {

    @Autowired
    private PubService commonPubService;
    @Autowired
    private FileTypeService commonFileService;

    @Autowired
    private TypeService typeService;


    @Test
    public void saveTest() {
        String hql = "from Type where name=? and style=?";
        List<Type> list = typeService.getListByHQL(hql, "PE", "1");
        System.out.println(list.size());
//        Pub commonPub = commonPubService.get(1);
//        System.out.println(commonPub.getId());
//        File commonFile = commonFileService.get(2);
//        System.out.println(commonFile.getId());

//        commonFile.setId(null);
//        commonFileService.saveTest(commonFile);
//        commonPub.setId(null);
//        commonPubService.saveTest(commonPub);

//        commonFile.setId(null);
//        commonFileService.save(commonFile);
//        commonFileService.deleteById(1);
//        Type commonType =commonTypeService.get(1);
//        System.out.println(commonType.getDesc());
    }


}