package jp.asatex.ggsz.socialinsurance.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SocialInsuranceDto {
    private CostDetail employeeCost;
    private CostDetail employerCost;

    @Data
    @Builder
    public static class CostDetail {
        private BigDecimal healthCostWithNoCare;
        private BigDecimal careCost;
        private BigDecimal pension;
        private BigDecimal employmentInsurance;
        private BigDecimal incomeTax;
    }
}
