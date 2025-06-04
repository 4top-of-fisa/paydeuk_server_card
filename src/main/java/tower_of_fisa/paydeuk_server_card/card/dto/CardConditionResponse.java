package tower_of_fisa.paydeuk_server_card.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardConditionResponse {

    private Long conditionId;
    private int value;


}
