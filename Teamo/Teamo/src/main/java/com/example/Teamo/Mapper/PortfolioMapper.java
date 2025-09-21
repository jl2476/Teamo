package com.example.Teamo.Mapper;
import org.springframework.stereotype.Component;
import com.example.Teamo.DTO.PortfolioItemDTO;
import com.example.Teamo.Model.PortfolioItem;

@Component
public class PortfolioMapper {

    public PortfolioItemDTO toDTO(PortfolioItem p) {
        if (p == null) return null;

        PortfolioItemDTO dto = new PortfolioItemDTO();
        dto.id = p.getId();
        dto.title = p.getTitle();
        dto.description = p.getDescription();
        dto.image_url = p.getImageUrl();
        return dto;
    }
}
