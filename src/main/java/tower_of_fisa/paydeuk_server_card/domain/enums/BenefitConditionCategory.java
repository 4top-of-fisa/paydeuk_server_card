package tower_of_fisa.paydeuk_server_card.domain.enums;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public enum BenefitConditionCategory {
  PER_TRANSACTION_LIMIT(LimitType.LIMIT, DateCategory.DAILY),
  DAILY_LIMIT_COUNT(LimitType.COUNT, DateCategory.DAILY),
  MONTHLY_LIMIT_COUNT(LimitType.COUNT, DateCategory.MONTHLY),
  DAILY_DISCOUNT_LIMIT(LimitType.LIMIT, DateCategory.DAILY),
  MONTHLY_DISCOUNT_LIMIT(LimitType.LIMIT, DateCategory.MONTHLY);

  private final LimitType limitType; // 한도 관련 여부 (LIMIT, COUNT)
  private final DateCategory dateCategory; // 날짜 기준: "daily", "monthly"

  BenefitConditionCategory(LimitType limitType, DateCategory dateCategory) {
    this.limitType = limitType;
    this.dateCategory = dateCategory;
  }

  // 날짜 기준에 맞는 날짜 값 반환 (오늘 날짜 또는 이번 달 날짜)
  public String getDateKey() {
    return dateCategory.getDateKey();
  }

  public String getPrefix() {
    return this.limitType == LimitType.LIMIT ? "BenefitSum" : "BenefitCount";
  }

  public enum LimitType {
    LIMIT,
    COUNT;
  }

  public enum DateCategory {
    DAILY {
      @Override
      public String getDateKey() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(Calendar.getInstance().getTime());
      }
    },
    MONTHLY {
      @Override
      public String getDateKey() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        return sdf.format(Calendar.getInstance().getTime());
      }
    };

    public abstract String getDateKey();
  }
}
