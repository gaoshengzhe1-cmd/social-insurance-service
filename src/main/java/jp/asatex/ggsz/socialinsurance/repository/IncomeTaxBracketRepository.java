package jp.asatex.ggsz.socialinsurance.repository;

import jp.asatex.ggsz.socialinsurance.entity.IncomeTaxBracket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeTaxBracketRepository extends ReactiveCrudRepository<IncomeTaxBracket, Long> {
    @Query("SELECT * FROM income_tax_bracket WHERE :salary >= min_salary AND :salary < max_salary LIMIT 1")
    Mono<IncomeTaxBracket> findBracketBySalary(Integer salary);
    
    @Query("SELECT COUNT(*) FROM income_tax_bracket")
    Mono<Long> countAll();
}
