package jp.asatex.ggsz.socialinsurance.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("premium_bracket")
public class PremiumBracket {
    @Id
    private Long id;
    
    @Column("grade")
    private String grade;
    
    @Column("std_rem")
    private Integer stdRem;
    
    @Column("min_amount")
    private Integer minAmount;
    
    @Column("max_amount")
    private Integer maxAmount;
    
    @Column("health_no_care")
    private BigDecimal healthNoCare;
    
    @Column("health_care")
    private BigDecimal healthCare;
    
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
