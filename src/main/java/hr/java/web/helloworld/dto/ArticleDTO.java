package hr.java.web.helloworld.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDTO {
    private String articleName;
    private String articleDescription;
    private BigDecimal articlePrice;
    private String categoryName;
}
