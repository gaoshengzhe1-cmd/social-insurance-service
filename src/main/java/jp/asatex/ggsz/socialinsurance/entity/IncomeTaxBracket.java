package jp.asatex.ggsz.socialinsurance.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("income_tax_bracket")
public class IncomeTaxBracket {
    @Id
    private Long id;
    private Integer minSalary;
    private Integer maxSalary;
    private BigDecimal dependents0;
    private BigDecimal dependents1;
    private BigDecimal dependents2;
    private BigDecimal dependents3;
    private BigDecimal dependents4;
    private BigDecimal dependents5;
    private BigDecimal dependents6;
    private BigDecimal dependents7;
    private BigDecimal taxAmountColB;
}
