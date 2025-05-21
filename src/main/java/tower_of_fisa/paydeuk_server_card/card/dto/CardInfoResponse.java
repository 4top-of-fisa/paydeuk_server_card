package tower_of_fisa.paydeuk_server_card.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardType;

import java.util.List;

@Builder
@Getter
@Schema(description = "카드 정보")
public class CardInfoResponse {

  @Schema(description = "카드 이름", example = "현대카드 M")
  private final String cardName;

  @Schema(description = "카드 타입", example = "credit")
  private final CardType cardType;

  @Schema(description = "카드 이미지 URL", example = "https://example.com/card.png")
  private final String imageUrl;

  @Schema(description = "연회비", example = "100000")
  private final Long annualFee;

  @Schema(description = "카드사", example = "HYUNDAI")
  private final CardCompany cardCompany;

  @Schema(description = "카드 혜택 목록")
  private final List<CardBenefitResponse> cardBenefits;
}
