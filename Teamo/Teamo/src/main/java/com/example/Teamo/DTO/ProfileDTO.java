package com.example.Teamo.DTO;

import java.util.List;

public class ProfileDTO {
  public Long id;
  public String name;               // firstName + " " + lastName
  public String title;
  public String bio;
  public String avatar;             // profilePictureUrl
  public String banner;             // bannerUrl
  public List<String> tags;         // tag names
  public List<String> portfolio_imgs; // image URLs
  //public int projects;              // count
  //public int followers;             // stub for now
}
