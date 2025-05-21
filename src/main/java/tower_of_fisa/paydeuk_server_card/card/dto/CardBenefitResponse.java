package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.domain.enums.BenefitType;

@Getter
@Builder
@Schema(description = "카드 혜택")
public class CardBenefitResponse {

  @Schema(description = "혜택 이름", example = "기본혜택")
  private final String benefitTitle;

  @Schema(description = "혜택 내용", example = "국내외 가맹점 1.5% M포인트 적립")
  private final String benefitDescription;

  @Schema(description = "혜택 방식", example = "POINT")
  private final BenefitType benefitType;

  @Schema(description = "적용 가맹점 목록")
  private final List<MerchantResponse> merchants;

  @Schema(description = "혜택 조건 목록")
  private final List<BenefitConditionResponse> benefitConditions;

  @Schema(description = "할인율")
  private final List<DiscountRateResponse> discountRates;
}
