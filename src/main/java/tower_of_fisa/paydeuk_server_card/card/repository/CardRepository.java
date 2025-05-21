package tower_of_fisa.paydeuk_server_card.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.enums.CardCompany;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

  List<Card> findByCardCompany(CardCompany cardCompany);
}
