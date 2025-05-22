package tower_of_fisa.paydeuk_server_card.benefitUsageCount.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tower_of_fisa.paydeuk_server_card.benefitUsageCount.service.BenefitUsageCountService;
import tower_of_fisa.paydeuk_server_card.benefitUsageCount.dto.CardConditionRequest;
import tower_of_fisa.paydeuk_server_card.benefitUsageCount.dto.CardConditionResponse;
import tower_of_fisa.paydeuk_server_card.global.common.response.CommonResponse;

import java.util.List;

@Tag(name = "카드 조건 조회 API", description = "카드 조건 조회 API")
@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class BenefitUsageCountController {

  private final BenefitUsageCountService benefitUsageCountService;

  @PostMapping("/check/condition")
  @Operation(summary = "CARD_01 : 카드 조건 조회", description = "카드 조건을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "카드 조건 조회 성공")
  public CommonResponse<List<CardConditionResponse>> checkCardCondition(
      @RequestBody CardConditionRequest request) {
    List<CardConditionResponse> cardConditionList =
        benefitUsageCountService.checkCardCondition(request);
    return new CommonResponse<>(true, HttpStatus.OK, "카드별 조건이 조회되었습니다.", cardConditionList);
  }
}
