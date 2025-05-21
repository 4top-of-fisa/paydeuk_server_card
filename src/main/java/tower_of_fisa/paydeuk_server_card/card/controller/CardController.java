package tower_of_fisa.paydeuk_server_card.card.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.service.CardService;
import tower_of_fisa.paydeuk_server_card.global.common.response.CommonResponse;
import tower_of_fisa.paydeuk_server_card.global.common.response.swagger_response.SwaggerResponseExample;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;

@Tag(name = "카드사 API", description = "카드사 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
@Slf4j
public class CardController {

  private final CardService cardService;

  @GetMapping("/info")
  @Operation(summary = "CARD_01 : 전체 카드 조회", description = "모든 카드 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "카드 정보 조회 성공")
  public CommonResponse<List<CardInfoResponse>> getAllCardInfo() {
    List<CardInfoResponse> cardInfoList = cardService.getAllCardInfo();
    return new CommonResponse<>(true, HttpStatus.OK, "카드 정보 조회 성공", cardInfoList);
  }

  @GetMapping("/info/{cardCompany}")
  @Operation(summary = "CARD_02 : 카드사별 카드 조회", description = "카드사별 카드 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "카드사별 카드 정보 조회 성공")
  @ApiResponse(
      responseCode = "404",
      description = "카드사 정보 없음",
      content =
          @Content(examples = {@ExampleObject(value = SwaggerResponseExample.CARD_COMPANY_404)}))
  public CommonResponse<List<CardInfoResponse>> getCardInfoByCardCompany(
      @PathVariable CardCompany cardCompany) {
    List<CardInfoResponse> cardInfoList = cardService.getCardInfoByCardCompany(cardCompany);
    return new CommonResponse<>(true, HttpStatus.OK, "카드사별 카드 정보 조회 성공", cardInfoList);
  }
}
