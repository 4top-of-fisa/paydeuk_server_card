package tower_of_fisa.paydeuk_server_card.token.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tower_of_fisa.paydeuk_server_card.global.common.response.CommonResponse;
import tower_of_fisa.paydeuk_server_card.global.common.response.swagger_response.SwaggerResponseExample;
import tower_of_fisa.paydeuk_server_card.token.dto.CardIssueRequest;
import tower_of_fisa.paydeuk_server_card.token.dto.CardTokenResponse;
import tower_of_fisa.paydeuk_server_card.token.service.IssuedCardTokenService;

@Tag(name = "카드 토큰 API", description = "카드 토큰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card/token")
@Slf4j
public class IssuedCardTokenController {

  private final IssuedCardTokenService issuedCardTokenService;

  @PostMapping("/issue")
  @Operation(summary = "CARD_TOKEN_01 : 카드 토큰 발급", description = "카드 토큰을 발급한다.")
  @ApiResponse(responseCode = "200", description = "카드 토큰 발급 성공")
  @ApiResponse(
      responseCode = "404",
      description = "카드 정보 없음",
      content = @Content(examples = {@ExampleObject(value = SwaggerResponseExample.CARD_404)}))
  @ApiResponse(
      responseCode = "403",
      description = "카드 소유자 아님",
      content = @Content(examples = {@ExampleObject(value = SwaggerResponseExample.CARD_403)}))
  @ApiResponse(
      responseCode = "400",
      description = "카드 정보가 유효하지 않음",
      content = @Content(examples = {@ExampleObject(value = SwaggerResponseExample.CARD_400)}))
  public CommonResponse<CardTokenResponse> issueCardToken(@RequestBody CardIssueRequest request) {
    CardTokenResponse cardTokenResponse = issuedCardTokenService.issueCardToken(request);
    return new CommonResponse<>(true, HttpStatus.OK, "카드 토큰 발급 성공", cardTokenResponse);
  }
}
