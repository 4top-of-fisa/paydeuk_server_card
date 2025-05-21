package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "가맹점 정보")
public class MerchantResponse {

  @Schema(description = "가맹점 이름", example = "스타벅스")
  private final String merchantName;

  @Schema(description = "카맹점 카테고리", example = "food_beverage")
  private final String merchantCategory;
}
