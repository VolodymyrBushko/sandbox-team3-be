package com.exadel.discountwebapp.tag.service;

import com.exadel.discountwebapp.tag.repository.TagRepository;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tag-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
public class TagServiceIntegrationTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;


    @Test
    void shouldGetAllTags() {
        var expectedItr = tagRepository.findAll();
        var expected = Lists.newArrayList(expectedItr);
        var actual = tagService.findAll();

        assertEquals(4, actual.size());

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());

        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());

        assertEquals(expected.get(2).getId(), actual.get(2).getId());
        assertEquals(expected.get(2).getName(), actual.get(2).getName());

        assertEquals(expected.get(3).getId(), actual.get(3).getId());
        assertEquals(expected.get(3).getName(), actual.get(3).getName());
    }

    @Test
    void shouldGetTagById() {
        var id = 1L;
        var expected = tagRepository.findById(id).get();
        var actual = tagService.findById(id);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldCreateTag() {
        var expected = TagRequestVO.builder().name("cats").categoryId(10L).build();
        var createTag = tagService.create(expected);

        var actual = tagRepository.findById(createTag.getId()).get();

        assertNotNull(actual);
        assertNotNull(actual.getId());

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCategoryId(), actual.getCategory().getId());
    }
}