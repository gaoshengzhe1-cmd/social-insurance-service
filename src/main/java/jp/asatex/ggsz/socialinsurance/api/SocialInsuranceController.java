package jp.asatex.ggsz.socialinsurance.api;

import jp.asatex.ggsz.socialinsurance.dto.SocialInsuranceDto;
import jp.asatex.ggsz.socialinsurance.service.SocialInsuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/health-insurance")
@RequiredArgsConstructor
public class SocialInsuranceController {

    private final SocialInsuranceService socialInsuranceService;

    @GetMapping("/calculate")
    public Mono<ResponseEntity<SocialInsuranceDto>> calculateHealthInsurance(
            @RequestParam Integer monthlySalary,
            @RequestParam Integer age) {
        
        log.info("收到健康保险计算请求: monthlySalary={}, age={}", monthlySalary, age);
        
        return socialInsuranceService.calculateHealthInsurance(monthlySalary, age)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    log.error("处理健康保险计算请求时发生错误: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().build());
                })
                .doOnSuccess(response -> log.debug("成功处理健康保险计算请求"));
    }
}
