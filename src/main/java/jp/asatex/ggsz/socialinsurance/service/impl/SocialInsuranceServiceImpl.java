package jp.asatex.ggsz.socialinsurance.service.impl;

import jp.asatex.ggsz.socialinsurance.dto.SocialInsuranceDto;
import jp.asatex.ggsz.socialinsurance.entity.PremiumBracket;
import jp.asatex.ggsz.socialinsurance.repository.PremiumBracketRepository;
import jp.asatex.ggsz.socialinsurance.service.SocialInsuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialInsuranceServiceImpl implements SocialInsuranceService {

    @Value("${social.insurance.care-insurance-age-threshold:40}")
    private int careInsuranceAgeThreshold;

    private final PremiumBracketRepository premiumBracketRepository;

    @Override
    public SocialInsuranceDto calculateHealthInsurance(Integer monthlySalary, Integer age) {
        return premiumBracketRepository.findBracketByAmount(monthlySalary)
                .map(bracket -> {
                    // 计算健康保险（个人负担50%）
                    BigDecimal employeeHealth = bracket.getHealthNoCare()
                            .divide(new BigDecimal("2"), 0, RoundingMode.HALF_UP);
                    
                    // 计算介护保险（如果需要，个人负担50%）
                    final BigDecimal employeeCare;
                    boolean needsCareInsurance = age >= careInsuranceAgeThreshold;
                    if (needsCareInsurance) {
                        employeeCare = bracket.getHealthCare()
                                .subtract(bracket.getHealthNoCare())
                                .divide(new BigDecimal("2"), 0, RoundingMode.HALF_UP);
                    } else {
                        employeeCare = BigDecimal.ZERO;
                    }

                    // 创建DTO并设置各项费用
                    return SocialInsuranceDto.builder()
                            .employeeCost(SocialInsuranceDto.CostDetail.builder()
                                    .healthCostWithNoCare(employeeHealth)
                                    .careCost(employeeCare)
                                    .build())
                            .employerCost(SocialInsuranceDto.CostDetail.builder()
                                    .healthCostWithNoCare(employeeHealth)
                                    .careCost(employeeCare)
                                    .build())
                            .build();
                })
                .block();
    }
}
