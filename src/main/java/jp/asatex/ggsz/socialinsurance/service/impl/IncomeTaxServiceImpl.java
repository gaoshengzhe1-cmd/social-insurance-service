package jp.asatex.ggsz.socialinsurance.service.impl;

import jp.asatex.ggsz.socialinsurance.entity.IncomeTaxBracket;
import jp.asatex.ggsz.socialinsurance.repository.IncomeTaxBracketRepository;
import jp.asatex.ggsz.socialinsurance.service.IncomeTaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeTaxServiceImpl implements IncomeTaxService {
    private final IncomeTaxBracketRepository taxBracketRepository;

    @Override
    public Mono<BigDecimal> calculateIncomeTax(Integer monthlySalary, Integer dependentsCount) {
        log.info("查询工资: {} 的源泉税", monthlySalary);
        return taxBracketRepository.findBracketBySalary(monthlySalary)
            .map(bracket -> {
                if (bracket == null) {
                    log.warn("未找到工资 {} 对应的税率区间", monthlySalary);
                    return BigDecimal.ZERO;
                }
                // 直接返回0人抚养的税额
                BigDecimal taxAmount = bracket.getDependents0();
                log.info("工资 {} 的源泉税 (0人抚养): {}", monthlySalary, taxAmount);
                return taxAmount != null ? taxAmount : BigDecimal.ZERO;
            })
            .defaultIfEmpty(BigDecimal.ZERO);
    }
}
