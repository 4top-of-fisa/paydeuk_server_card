package tower_of_fisa.paydeuk_server_card.card.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.PaymentRequest;
import tower_of_fisa.paydeuk_server_card.card.dto.PaymentResponse;
import tower_of_fisa.paydeuk_server_card.card.mapper.CardMapper;
import tower_of_fisa.paydeuk_server_card.card.repository.CardRepository;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

  private final CardRepository cardRepository;
  private final CardMapper cardMapper;

  public List<CardInfoResponse> getAllCardInfo() {
    return cardRepository.findAll().stream().map(cardMapper::toDto).toList();
  }

  public List<CardInfoResponse> getCardInfoByCardCompany(CardCompany cardCompany) {
    return cardRepository.findByCardCompany(cardCompany).stream().map(cardMapper::toDto).toList();
  }

  public PaymentResponse paymentProcessing(PaymentRequest paymentRequest) {
    //결제 프로스세를 진행합니다.
    paymentRequest.getAmount(); // 소나큐브 에러 방지를 위한 대체
    return new PaymentResponse("2025-05-22-T10:15:30");
  }
}
