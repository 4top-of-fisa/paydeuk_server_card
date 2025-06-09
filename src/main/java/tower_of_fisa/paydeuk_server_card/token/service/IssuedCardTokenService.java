package tower_of_fisa.paydeuk_server_card.token.service;

import static tower_of_fisa.paydeuk_server_card.global.common.ErrorDefineCode.*;

import java.security.MessageDigest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class IssuedCardTokenService {

  private final IssuedCardRepository issuedCardRepository;

  public CardTokenResponse issueCardToken(CardIssueRequest request) {
    log.info("[카드 토큰 발급 요청] 사용자 이름: {}, 카드 번호: {}", request.getUserName(), maskCardNumber(request.getCardNumber()));

    IssuedCard issuedCard = issuedCardRepository
            .findByCardNumber(request.getCardNumber())
            .orElseThrow(() -> {
              log.warn("[카드 없음] cardNumber: {}", maskCardNumber(request.getCardNumber()));
              return new NoSuchElementFoundException404(CARD_NOT_FOUND);
            });

    if (!isSameUser(issuedCard, request)) {
      log.warn("[사용자 정보 불일치] 요청자: {}, 카드 소유자: {}", request.getUserName(), issuedCard.getUser().getName());
      throw new ForbiddenException403(CARD_OWNER_MISMATCH);
    }

    if (!isValidCard(issuedCard, request)) {
      log.warn("[카드 정보 불일치] 카드 번호: {}", maskCardNumber(request.getCardNumber()));
      throw new BadRequestException400(INVALID_CARD);
    }

    String cardToken = generateCardToken(issuedCard);
    log.info("[페이득 카드 등록 성공] 사용자: {}, 카드 ID: {}", request.getUserName(), issuedCard.getCard().getName());

    return CardTokenResponse.builder()
            .cardId(issuedCard.getCard().getId())
            .cardToken(cardToken)
            .build();
  }

  private boolean isSameUser(IssuedCard issuedCard, CardIssueRequest request) {
    return issuedCard.getUser().getName().equals(request.getUserName())
            && issuedCard.getUser().getBirthdate().equals(request.getUserBirthDate())
            && issuedCard.getUser().getPhoneNumber().equals(request.getUserPhone());
  }

  private boolean isValidCard(IssuedCard issuedCard, CardIssueRequest request) {
    return issuedCard.getExpirationMonth().equals(request.getMonth())
            && issuedCard.getExpirationYear().endsWith(request.getYear())
            && issuedCard.getCvc().equals(request.getCvc())
            && issuedCard.getCardPassword().startsWith(request.getPinPrefix());
  }

  private String generateCardToken(IssuedCard issuedCard) {
    String rawToken =
            issuedCard.getCardNumber()
                    + issuedCard.getExpirationMonth()
                    + issuedCard.getExpirationYear()
                    + issuedCard.getCvc()
                    + issuedCard.getCardPassword()
                    + issuedCard.getUser().getName();

    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hash = messageDigest.digest(rawToken.getBytes());
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (Exception e) {
      log.error("[카드 토큰 생성 실패]", e);
      throw new BadRequestException400(UNCAUGHT);
    }
  }

  private String maskCardNumber(String cardNumber) {
    if (cardNumber == null || cardNumber.length() < 4) return "****";
    return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
  }
}