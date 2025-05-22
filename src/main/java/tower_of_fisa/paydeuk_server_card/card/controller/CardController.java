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
import org.springframework.web.bind.annotation.*;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.PaymentRequest;
import tower_of_fisa.paydeuk_server_card.card.dto.PaymentResponse;
import tower_of_fisa.paydeuk_server_card.card.service.CardService;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;
import tower_of_fisa.paydeuk_server_card.global.common.response.CommonResponse;
import tower_of_fisa.paydeuk_server_card.global.common.response.swagger_response.SwaggerResponseExample;

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
  @PostMapping("/payment")
  @Operation(summary = "CARD_03 : 카드 결제", description = "결제 요청을 처리합니다.")
  @ApiResponse(responseCode = "200", description = "결제 요청을 처리 성공")
//  @ApiResponse(
//          responseCode = "404",
//          description = "카드사 정보 없음",
//          content =
//          @Content(examples = {@ExampleObject(value = SwaggerResponseExample.CARD_COMPANY_404)}))
  // 결제 프로세스에 따른 에러처리
  public CommonResponse<PaymentResponse> receivePayment(
          @RequestBody PaymentRequest paymentRequest) {
    PaymentResponse paymentResponse = cardService.paymentProcessing(paymentRequest);
    return new CommonResponse<>(true, HttpStatus.OK, "카드사별 카드 정보 조회 성공", paymentResponse);
  }
}
