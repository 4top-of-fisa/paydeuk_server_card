package tower_of_fisa.paydeuk_server_card.common.response.swagger_response;

public final class SwaggerResponseExample {
  // 소나큐브 유틸클래스 이슈로 인해 생성자를 명시적으로 막아줌
  private SwaggerResponseExample() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static final String CARD_COMPANY_404 =
      """
          {
            "success": false,
            "status": "NOT_FOUND",
            "message": "해당 카드사가 존재하지 않습니다.",
            "response": {
              "errorCode": "CC_ERR_01",
              "time": "2024-01-01T00:00:00",
              "stackTrace": "tower_of_fisa.paydeuk_server_card.config.exception.custom.exception.NoSuchElementFoundException404: 해당 카드사가 존재하지 않습니다.,
              "errors": null
            }
          }
      """;

  public static final String CARD_404 =
      """
              {
                "success": false,
                "status": "NOT_FOUND",
                "message": "카드 정보가 존재하지 않습니다.",
                "response": {
                  "errorCode": "CARD_ERR_01",
                  "time": "2024-01-01T00:00:00",
                  "stackTrace": "tower_of_fisa.paydeuk_server_card.config.exception.custom.exception.NoSuchElementFoundException404: 카드 정보가 존재하지 않습니다.,
                  "errors": null
                }
              }
          """;
}
