package com.alkemy.ong.dto;

import com.alkemy.ong.model.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsPaginationDto {
    private List<NewsDto> news;
    private Map<String, String> links;
}
