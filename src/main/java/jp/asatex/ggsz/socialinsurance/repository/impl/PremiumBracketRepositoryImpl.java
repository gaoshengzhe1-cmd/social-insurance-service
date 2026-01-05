package jp.asatex.ggsz.socialinsurance.repository.impl;

import jp.asatex.ggsz.socialinsurance.entity.PremiumBracket;
import jp.asatex.ggsz.socialinsurance.repository.PremiumBracketRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PremiumBracketRepositoryImpl implements PremiumBracketRepository {

    private final DatabaseClient databaseClient;

    public PremiumBracketRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<PremiumBracket> findBracketByAmount(Integer amount) {
        String sql = "SELECT * FROM premium_bracket WHERE min_amount <= :amount AND max_amount > :amount LIMIT 1";
        
        return databaseClient.sql(sql)
                .bind("amount", amount)
                .map(row -> {
                    PremiumBracket bracket = new PremiumBracket();
                    bracket.setId(row.get("id", Long.class));
                    bracket.setGrade(row.get("grade", String.class));
                    bracket.setStdRem(row.get("std_rem", Integer.class));
                    bracket.setMinAmount(row.get("min_amount", Integer.class));
                    bracket.setMaxAmount(row.get("max_amount", Integer.class));
                    bracket.setHealthNoCare(row.get("health_no_care", java.math.BigDecimal.class));
                    bracket.setHealthCare(row.get("health_care", java.math.BigDecimal.class));
                    bracket.setPension(row.get("pension", java.math.BigDecimal.class));
                    bracket.setCreatedAt(row.get("created_at", java.time.LocalDateTime.class));
                    bracket.setUpdatedAt(row.get("updated_at", java.time.LocalDateTime.class));
                    return bracket;
                })
                .one();
    }
}
