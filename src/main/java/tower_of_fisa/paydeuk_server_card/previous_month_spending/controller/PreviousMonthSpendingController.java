package tower_of_fisa.paydeuk_server_card.previous_month_spending.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tower_of_fisa.paydeuk_server_card.global.common.response.CommonResponse;
import tower_of_fisa.paydeuk_server_card.global.common.response.swagger_response.SwaggerResponseExample;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingRequest;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.dto.PreviousMonthSpendingResponse;
import tower_of_fisa.paydeuk_server_card.previous_month_spending.service.PreviousMonthSpendingService;

@RequestMapping("/api/card")
@RequiredArgsConstructor
@RestController
public class PreviousMonthSpendingController {

    private final PreviousMonthSpendingService previousMonthSpendingService;


    @PostMapping("/record")
    @Operation(summary = "CARD_01 : 전월 실적 조회", description = "사용할려는 카드의 전월 실적을 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "전월 실적 조회 성공"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "카드를 찾을 수 없음",
                            content =
                            @Content(examples = {@ExampleObject(value = SwaggerResponseExample.CARD_404)}))
            })
    public CommonResponse<PreviousMonthSpendingResponse> getRecord(
            @RequestBody @Valid PreviousMonthSpendingRequest previousMonthSpendingRequest) {

        PreviousMonthSpendingResponse previousMonthSpendingResponse = previousMonthSpendingService.getRecord(previousMonthSpendingRequest);
        return new CommonResponse<>(true, HttpStatus.OK, "전월 실적을 조회에 성공했습니다.", previousMonthSpendingResponse);
    }
}
