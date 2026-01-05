package jp.asatex.ggsz.socialinsurance.repository;

import jp.asatex.ggsz.socialinsurance.entity.PremiumBracket;
import reactor.core.publisher.Mono;

public interface PremiumBracketRepository {
    Mono<PremiumBracket> findBracketByAmount(Integer amount);
}
