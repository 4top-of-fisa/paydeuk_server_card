package tower_of_fisa.paydeuk_server_card.card.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tower_of_fisa.paydeuk_server_card.card.dto.CardInfoResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.CardBenefitResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.MerchantResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.BenefitConditionResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.DiscountRateResponse;
import tower_of_fisa.paydeuk_server_card.card.dto.SpendingRangeResponse;
import tower_of_fisa.paydeuk_server_card.domain.entity.Card;
import tower_of_fisa.paydeuk_server_card.domain.entity.CardBenefit;
import tower_of_fisa.paydeuk_server_card.domain.entity.Benefit;
import tower_of_fisa.paydeuk_server_card.domain.entity.BenefitCondition;
import tower_of_fisa.paydeuk_server_card.domain.entity.DiscountRate;
import tower_of_fisa.paydeuk_server_card.domain.entity.SpendingRange;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardMapper {
    
    public CardInfoResponse toDto(Card card) {
        return CardInfoResponse.builder()
                .cardName(card.getName())
                .cardType(card.getType())
                .imageUrl(card.getImageUrl())
                .annualFee(card.getAnnualFee())
                .cardCompany(card.getCardCompany())
                .cardBenefits(mapCardBenefits(card.getCardBenefits()))
                .build();
    }

    private List<CardBenefitResponse> mapCardBenefits(List<CardBenefit> cardBenefits) {
        return cardBenefits.stream()
                .map(this::mapCardBenefit)
                .toList();
    }

    private CardBenefitResponse mapCardBenefit(CardBenefit cardBenefit) {
        Benefit benefit = cardBenefit.getBenefit();
        return CardBenefitResponse.builder()
                .benefitTitle(benefit.getBenefitTitle())
                .benefitDescription(benefit.getBenefitDescription())
                .benefitType(benefit.getBenefitType())
                .merchants(mapMerchants(benefit.getMerchant()))
                .benefitConditions(mapBenefitConditions(benefit.getBenefitConditions()))
                .discountRates(mapDiscountRates(benefit.getDiscountRates()))
                .build();
    }

    private List<MerchantResponse> mapMerchants(tower_of_fisa.paydeuk_server_card.domain.entity.Merchant merchant) {
        if (merchant == null) {
            return List.of();
        }
        return List.of(MerchantResponse.builder()
                .merchantName(merchant.getName())
                .build());
    }

    private List<BenefitConditionResponse> mapBenefitConditions(List<BenefitCondition> benefitConditions) {
        return benefitConditions.stream()
                .map(this::mapBenefitCondition)
                .toList();
    }

    private BenefitConditionResponse mapBenefitCondition(BenefitCondition condition) {
        return BenefitConditionResponse.builder()
                .conditionCategory(condition.getConditionCategory())
                .value(condition.getValue())
                .spendingRange(mapSpendingRange(condition.getSpendingRange()))
                .build();
    }

    private List<DiscountRateResponse> mapDiscountRates(List<DiscountRate> discountRates) {
        return discountRates.stream()
                .map(this::mapDiscountRate)
                .toList();
    }

    private DiscountRateResponse mapDiscountRate(DiscountRate rate) {
        return DiscountRateResponse.builder()
                .discountApplyType(rate.getDiscountApplyType().name())
                .discountAmount(rate.getDiscountAmount())
                .spendingRange(mapSpendingRange(rate.getSpendingRange()))
                .build();
    }

    private SpendingRangeResponse mapSpendingRange(SpendingRange spendingRange) {
        if (spendingRange == null) {
            return null;
        }
        return SpendingRangeResponse.builder()
                .minSpending(spendingRange.getMinSpending())
                .maxSpending(spendingRange.getMaxSpending())
                .build();
    }
}
