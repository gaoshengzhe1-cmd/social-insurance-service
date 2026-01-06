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
        log.info("Getting tax amount for dependents: {}, bracket: {}", dependentsCount, bracket);
        
        BigDecimal amount = switch (dependentsCount) {
            case 0 -> bracket.getDependents0();
            case 1 -> bracket.getDependents1();
            case 2 -> bracket.getDependents2();
            case 3 -> bracket.getDependents3();
            case 4 -> bracket.getDependents4();
            case 5 -> bracket.getDependents5();
            case 6 -> bracket.getDependents6();
            case 7 -> bracket.getDependents7();
            default -> throw new IllegalArgumentException("Unsupported number of dependents: " + dependentsCount);
        };
        
        log.info("Raw tax amount for {} dependents: {}", dependentsCount, amount);
        
        // 如果 dependents 字段为 null，尝试使用 taxAmountColB
        if (amount == null) {
            log.warn("Dependents field is null, using taxAmountColB: {}", bracket.getTaxAmountColB());
            amount = bracket.getTaxAmountColB();
        }
        
        BigDecimal result = amount != null ? amount : BigDecimal.ZERO;
        log.info("Final tax amount: {}", result);
        return result;
    }
}
