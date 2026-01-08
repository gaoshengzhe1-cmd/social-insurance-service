package jp.asatex.ggsz.socialinsurance.repository;

import jp.asatex.ggsz.socialinsurance.entity.IncomeTaxBracket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeTaxBracketRepository extends ReactiveCrudRepository<IncomeTaxBracket, Long> {
    /**
     * 根据工资查找对应的税率档位
     * 使用 ORDER BY min_salary DESC 确保即使数据有间隙也能找到最接近的档位
     */
    @Query("""
        SELECT * FROM income_tax_bracket 
        WHERE :salary >= min_salary 
        ORDER BY min_salary DESC 
        LIMIT 1
        """)
    Mono<IncomeTaxBracket> findBracketBySalary(Integer salary);
    
    @Query("SELECT COUNT(*) FROM income_tax_bracket")
    Mono<Long> countAll();
}
