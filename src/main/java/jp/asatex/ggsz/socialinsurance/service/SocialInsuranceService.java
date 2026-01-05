package jp.asatex.ggsz.socialinsurance.service;

import jp.asatex.ggsz.socialinsurance.dto.SocialInsuranceDto;
import reactor.core.publisher.Mono;

public interface SocialInsuranceService {
    Mono<SocialInsuranceDto> calculateHealthInsurance(Integer monthlySalary, Integer age);
}
