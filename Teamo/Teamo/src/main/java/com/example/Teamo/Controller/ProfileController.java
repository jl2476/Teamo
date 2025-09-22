package com.example.Teamo.Controller;

import com.example.Teamo.DAO.UserDAO;
import com.example.Teamo.DTO.ProfileDTO;
import com.example.Teamo.Mapper.ProfileMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProfileController {

  private final UserDAO userDAO;

  public ProfileController(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @GetMapping("/profiles")
  public List<ProfileDTO> getProfiles() {
    return userDAO.findByIsActiveTrue().stream()
        .map(ProfileMapper::toDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/profile/{id}")
  public ProfileDTO getProfile(@PathVariable Long id) {
    var u = userDAO.findById(id).orElseThrow();
    return ProfileMapper.toDto(u);
  }

  @GetMapping("/profiles/search")
  public List<ProfileDTO> searchByTag(@RequestParam String tag) {
    return userDAO.findByTagNameContaining(tag).stream()
        .map(ProfileMapper::toDto)
        .collect(Collectors.toList());
  }
}
