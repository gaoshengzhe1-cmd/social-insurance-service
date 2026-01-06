package jp.asatex.ggsz.socialinsurance.repository;

import jp.asatex.ggsz.socialinsurance.entity.IncomeTaxBracket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeTaxBracketRepository extends ReactiveCrudRepository<IncomeTaxBracket, Long> {
    @Query("SELECT * FROM income_tax_bracket WHERE ?1 >= min_salary AND ?1 < max_salary ORDER BY min_salary DESC LIMIT 1")
    Flux<IncomeTaxBracket> findBracketBySalary(Integer salary);
}
