package com.example.Teamo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Teamo.DTO.TagDTO;

import jakarta.transaction.Transactional;

import com.example.Teamo.DAO.TagDAO;

@Service
@Transactional
public class TagService {

    private final TagDAO tagDAO;

    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public List<TagDTO> getAllTags() {
        return tagDAO.findAll()
                .stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }
}
