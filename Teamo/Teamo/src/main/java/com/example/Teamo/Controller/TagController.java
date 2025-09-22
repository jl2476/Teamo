package com.example.Teamo.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.Teamo.DTO.TagDTO;
import com.example.Teamo.Service.TagService;

import java.util.List;


@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }



    @GetMapping
    public List<TagDTO> getAllTags() {
        return this.tagService.getAllTags();
    }
}