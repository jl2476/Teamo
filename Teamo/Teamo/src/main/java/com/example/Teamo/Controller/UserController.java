package com.example.Teamo.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import com.example.Teamo.Model.Tag;
import com.example.Teamo.Model.User;
import com.example.Teamo.DAO.UserDAO;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/search-by-tags")
    public List<User> searchByTags(@RequestBody Set<Long> tagIds) {
        Set<Tag> tags = tagIds.stream().map(id -> {
            Tag t = new Tag();
            t.setId(id); // only ID is needed for JPQL
            return t;
        }).collect(Collectors.toSet());
        return userDAO.findByTagsIn(tags);
    }
}

