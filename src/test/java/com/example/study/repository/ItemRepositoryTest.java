package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        /*
        Item item = new Item();
        item.setName("노트북");
        item.setPrice(100000);
        item.setContent("samsung");
        */

        Item item = new Item();
        item.setStatus(ItemStatus.UNREGISTERED);
        item.setName("노트북");
        item.setTitle("samsung notebook");
        item.setContent("samsung notebook A100");
        //item.setPrice(900000);
        item.setBrandName("samsung");
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("Partner01");
        //item.setPartnerId(1L);


        Item newItem = itemRepository.save(item);
        Assert.assertNotNull(newItem);
    }

    @Test
    public void read() {
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);
        //Optional : 있을수도 있고 없을수도 있을 때 사용한다.

        Assert.assertTrue(item.isPresent());

        item.ifPresent(i -> {
            System.out.println(i);
        });
    }
}
