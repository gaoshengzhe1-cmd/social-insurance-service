package jp.asatex.ggsz.socialinsurance.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("income_tax_bracket")
public class IncomeTaxBracket {
    @Id
    private Long id;
    private Integer minSalary;
    private Integer maxSalary;
    @Column("dependents_0")
    private BigDecimal dependents0;
    
    @Column("dependents_1")
    private BigDecimal dependents1;
    
    @Column("dependents_2")
    private BigDecimal dependents2;
    
    @Column("dependents_3")
    private BigDecimal dependents3;
    
    @Column("dependents_4")
    private BigDecimal dependents4;
    
    @Column("dependents_5")
    private BigDecimal dependents5;
    
    @Column("dependents_6")
    private BigDecimal dependents6;
    
    @Column("dependents_7")
    private BigDecimal dependents7;
    
    @Column("tax_amount_col_b")
    private BigDecimal taxAmountColB;
}
