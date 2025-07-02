package hr.java.web.helloworld.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class SearchArticle extends Article {

    private BigDecimal lowerPrice;
    private BigDecimal upperPrice;

    public SearchArticle(Integer id,
                         String name,
                         String description,
                         BigDecimal lowerPrice,
                         BigDecimal upperPrice,
                         Category category)
    {
        this(name, description, lowerPrice, upperPrice, category);
        super.setId(id);
    }

    public SearchArticle(String name,
                         String description,
                         BigDecimal lowerPrice,
                         BigDecimal upperPrice,
                         Category categoryName)
    {
        super.setName(name);
        super.setDescription(description);
        this.lowerPrice = lowerPrice;
        this.upperPrice = upperPrice;
        super.setCategory(categoryName);
    }

}
