package com.example.Teamo.Mapper;

import com.example.Teamo.DTO.ProfileDTO;
import com.example.Teamo.Model.User;

import java.util.stream.Collectors;

public class ProfileMapper {
  public static ProfileDTO toDto(User u) {
    var dto = new ProfileDTO();
    dto.id = u.getId();
    dto.name = u.getFirstName() + " " + u.getLastName();
    dto.title = u.getTitle();
    dto.bio = u.getBio();
    dto.avatar = u.getProfilePictureUrl();
    dto.banner = u.getBannerUrl();
    dto.tags = u.getTags().stream().map(t -> t.getName()).collect(Collectors.toList());
    dto.portfolio_imgs = u.getPortfolioItems().stream()
        .map(pi -> pi.getImageUrl())
        .collect(Collectors.toList());
    //dto.projects = (u.getPortfolioItems() == null) ? 0 : u.getPortfolioItems().size();
    //dto.followers = 0; 
    return dto;
  }
}
