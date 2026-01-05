package jp.asatex.ggsz.socialinsurance.repository;

import jp.asatex.ggsz.socialinsurance.entity.PremiumBracket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PremiumBracketRepository extends R2dbcRepository<PremiumBracket, Long> {
    Mono<PremiumBracket> findBracketByAmount(Integer amount);
}
