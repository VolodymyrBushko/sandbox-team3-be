package com.exadel.discountwebapp.tag.controller;

import com.exadel.discountwebapp.tag.service.TagService;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagResponseVO> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public TagResponseVO findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<TagResponseVO> findAllTagsByCategoryId(@PathVariable Long categoryId) {
        return tagService.findAllTagsByCategoryId(categoryId);
    }

    @PostMapping
    public TagResponseVO create(@Valid @RequestBody TagRequestVO request) {
        return tagService.create(request);
    }

    @PutMapping("/{id}")
    public TagResponseVO update(@PathVariable Long id, @Valid @RequestBody TagRequestVO request) {
        return tagService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}