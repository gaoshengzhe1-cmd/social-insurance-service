package jp.asatex.ggsz.socialinsurance.repository;

import jp.asatex.ggsz.socialinsurance.entity.IncomeTaxBracket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IncomeTaxBracketRepository extends ReactiveCrudRepository<IncomeTaxBracket, Long> {
    @Query("SELECT * FROM income_tax_bracket WHERE :salary >= min_salary AND :salary < max_salary")
    Mono<IncomeTaxBracket> findBracketBySalary(Integer salary);
}
