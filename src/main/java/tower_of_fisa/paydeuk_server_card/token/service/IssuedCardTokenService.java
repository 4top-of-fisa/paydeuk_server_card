package tower_of_fisa.paydeuk_server_card.token.service;

import static tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode.*;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCard;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.BadRequestException400;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.ForbiddenException403;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.issued_card.repository.IssuedCardRepository;
import tower_of_fisa.paydeuk_server_card.token.dto.CardIssueRequest;
import tower_of_fisa.paydeuk_server_card.token.dto.CardTokenResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssuedCardTokenService {

  private final IssuedCardRepository issuedCardRepository;

  public CardTokenResponse issueCardToken(CardIssueRequest request) {

    // 1. 카드 번호로 카드 존재 유무 확인
    IssuedCard issuedCard =
        issuedCardRepository
            .findByCardNumber(request.getCardNumber())
            .orElseThrow(() -> new NoSuchElementFoundException404(CARD_NOT_FOUND));

    // 2. 등록 요청한 사용자가 카드의 소유자인지 확인
    if (!isSameUser(issuedCard, request)) {
      throw new ForbiddenException403(CARD_OWNER_MISMATCH);
    }

    // 3. 카드 정보 유효성 검증
    if (!isValidCard(issuedCard, request)) {
      throw new BadRequestException400(INVALID_CARD);
    }

    // 4. 카드 토큰 발급
    String cardToken = generateCardToken();

    return CardTokenResponse.builder()
        .cardId(issuedCard.getCard().getId())
        .cardToken(cardToken)
        .build();
  }

  private boolean isSameUser(IssuedCard issuedCard, CardIssueRequest request) {
    return issuedCard.getUser().getName().equals(request.getUserName())
        && issuedCard.getUser().getBirthdate().equals(request.getUserDob())
        && issuedCard.getUser().getPhoneNumber().equals(request.getUserPhone());
  }

  private boolean isValidCard(IssuedCard issuedCard, CardIssueRequest request) {
    return issuedCard.getExpirationMonth().equals(request.getMonth())
        && issuedCard.getExpirationYear().endsWith(request.getYear())
        && issuedCard.getCvc().equals(request.getCvc())
        && issuedCard.getCardPassword().startsWith(request.getPinPrefix());
  }

  private String generateCardToken() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
