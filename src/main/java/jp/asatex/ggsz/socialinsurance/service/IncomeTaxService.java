package jp.asatex.ggsz.socialinsurance.service;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface IncomeTaxService {
    Mono<BigDecimal> calculateIncomeTax(Integer monthlySalary, Integer dependentsCount);
}
