package hr.java.web.helloworld.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class SearchArticleDTO extends ArticleDTO {

    private BigDecimal lowerPrice;
    private BigDecimal upperPrice;

    public SearchArticleDTO(String articleName,
                            String articleDescription,
                            BigDecimal lowerPrice,
                            BigDecimal upperPrice,
                            String categoryName) {
        super.setArticleName(articleName);
        super.setArticleDescription(articleDescription);
        this.lowerPrice = lowerPrice;
        this.upperPrice = upperPrice;
        super.setCategoryName(categoryName);
    }

}
