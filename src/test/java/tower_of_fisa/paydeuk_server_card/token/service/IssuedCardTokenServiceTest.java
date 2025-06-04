package tower_of_fisa.paydeuk_server_card.token.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.entity.IssuedCard;
import tower_of_fisa.paydeuk_server_card.domain.entity.Users;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.BadRequestException400;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.ForbiddenException403;
import tower_of_fisa.paydeuk_server_card.global.config.exception.custom.exception.NoSuchElementFoundException404;
import tower_of_fisa.paydeuk_server_card.issued_card.repository.IssuedCardRepository;
import tower_of_fisa.paydeuk_server_card.token.dto.CardIssueRequest;
import tower_of_fisa.paydeuk_server_card.token.dto.CardTokenResponse;

@ExtendWith(MockitoExtension.class)
class IssuedCardTokenServiceTest {

  @Mock private IssuedCardRepository issuedCardRepository;

  @InjectMocks private IssuedCardTokenService issuedCardTokenService;

  private IssuedCard issuedCard;

  @BeforeEach
  void setUp() {
    Users user =
        Users.builder().name("임지섭").birthdate("1999-09-01").phoneNumber("010-3164-6358").build();

    Card card = Card.builder().id(1L).build();

    issuedCard =
        IssuedCard.builder()
            .id(1L)
            .cardNumber("1111-2222-3333-4445")
            .cvc("123")
            .expirationYear("2026")
            .expirationMonth("12")
            .cardPassword("0000")
            .user(user)
            .card(card)
            .build();
  }

  private CardIssueRequest validRequest() {
    return CardIssueRequest.builder()
        .userName("임지섭")
        .userBirthDate("1999-09-01")
        .userPhone("010-3164-6358")
        .cardNumber("1111-2222-3333-4445")
        .month("12")
        .year("26")
        .cvc("123")
        .pinPrefix("00")
        .build();
  }

  @Test
  @DisplayName("모든 정보가 올바르면 카드 토큰을 발급한다")
  void issueCardToken_success() throws NoSuchAlgorithmException {
    when(issuedCardRepository.findByCardNumber(anyString()))
        .thenReturn(java.util.Optional.of(issuedCard));

    CardTokenResponse response = issuedCardTokenService.issueCardToken(validRequest());

    assertThat(response.getCardId()).isEqualTo(1L);
    assertThat(response.getCardToken()).isEqualTo(expectedToken());
  }

  @Test
  @DisplayName("카드가 존재하지 않으면 예외를 던진다")
  void issueCardToken_cardNotFound() {
    when(issuedCardRepository.findByCardNumber(anyString())).thenReturn(java.util.Optional.empty());

    assertThatThrownBy(() -> issuedCardTokenService.issueCardToken(validRequest()))
        .isInstanceOf(NoSuchElementFoundException404.class);
  }

  @Test
  @DisplayName("카드 소유자가 아니면 예외를 던진다")
  void issueCardToken_ownerMismatch() {
    when(issuedCardRepository.findByCardNumber(anyString()))
        .thenReturn(java.util.Optional.of(issuedCard));

    CardIssueRequest request =
        CardIssueRequest.builder()
            .userName("다른사람")
            .userBirthDate("1999-09-01")
            .userPhone("010-3164-6358")
            .cardNumber("1111-2222-3333-4445")
            .month("12")
            .year("26")
            .cvc("123")
            .pinPrefix("00")
            .build();

    assertThatThrownBy(() -> issuedCardTokenService.issueCardToken(request))
        .isInstanceOf(ForbiddenException403.class);
  }

  @Test
  @DisplayName("카드 정보가 올바르지 않으면 예외를 던진다")
  void issueCardToken_invalidCardInfo() {
    when(issuedCardRepository.findByCardNumber(anyString()))
        .thenReturn(java.util.Optional.of(issuedCard));

    CardIssueRequest request =
        CardIssueRequest.builder()
            .userName("임지섭")
            .userBirthDate("1999-09-01")
            .userPhone("010-3164-6358")
            .cardNumber("1111-2222-3333-4445")
            .month("11") // 잘못된 월
            .year("26")
            .cvc("123")
            .pinPrefix("00")
            .build();

    assertThatThrownBy(() -> issuedCardTokenService.issueCardToken(request))
        .isInstanceOf(BadRequestException400.class);
  }

  private String expectedToken() throws NoSuchAlgorithmException {
    String raw =
        issuedCard.getCardNumber()
            + issuedCard.getExpirationMonth()
            + issuedCard.getExpirationYear()
            + issuedCard.getCvc()
            + issuedCard.getCardPassword()
            + issuedCard.getUser().getName();
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(raw.getBytes());
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
