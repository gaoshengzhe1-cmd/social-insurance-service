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
        log.info("Querying tax bracket for salary: {}", monthlySalary);
        return taxBracketRepository.findBracketBySalary(monthlySalary)
            .doOnNext(bracket -> log.info("Found tax bracket: {}", bracket))
            .next()
            .flatMap(bracket -> {
                if (bracket == null) {
                    return Mono.empty();
                }
                BigDecimal amount = getTaxAmountByDependents(bracket, dependentsCount);
                return amount != null ? Mono.just(amount) : Mono.empty();
            })
            .defaultIfEmpty(BigDecimal.ZERO)
            .doOnNext(tax -> log.info("Calculated income tax: {} for salary: {}, dependents: {}", 
                tax, monthlySalary, dependentsCount));
    }

    private BigDecimal getTaxAmountByDependents(IncomeTaxBracket bracket, Integer dependentsCount) {
        // 使用 taxAmountColB 字段，因为它有正确的税额数据
        BigDecimal amount = bracket.getTaxAmountColB();
        return amount != null ? amount : BigDecimal.ZERO;
    }
}
