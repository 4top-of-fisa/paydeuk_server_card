package tower_of_fisa.paydeuk_server_card.config.redis;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;

  // 키-값 저장 (TTL을 지정할 수 있는 메서드 추가)
  public void saveValue(String key, String value, long ttl, TimeUnit unit) {
    redisTemplate.opsForValue().set(key, value, ttl, unit);
  }

  public String getValue(String key) {
    String value = redisTemplate.opsForValue().get(key);
    return value != null ? value : ""; // null 대신 빈 문자열을 반환
  }

  // Redis에서 해당 키의 값을 1 증가시키는 메서드
  public void incrementValue(String key) {
    redisTemplate.opsForValue().increment(key, 1);
  }

  // Redis에서 해당 키의 값을 혜택 금액만큼 추가하는 메서드
  public void addValue(String key, double amount) {
    redisTemplate.opsForValue().increment(key, amount);
  }

  public boolean hasKey(String redisKey) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
  }

  // TTL을 계산하여 저장하는 메서드
  public long getRemainingSecondsUntilMidnight() {
    Calendar now = Calendar.getInstance();
    Calendar midnight = (Calendar) now.clone();
    midnight.set(Calendar.HOUR_OF_DAY, 0);
    midnight.set(Calendar.MINUTE, 0);
    midnight.set(Calendar.SECOND, 0);
    midnight.set(Calendar.MILLISECOND, 0);

    long diffInMillis = midnight.getTimeInMillis() - now.getTimeInMillis();
    return TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
  }

  public long getRemainingSecondsUntilNextMonthFirstDay() {
    Calendar now = Calendar.getInstance();
    Calendar firstOfNextMonth = (Calendar) now.clone();
    firstOfNextMonth.add(Calendar.MONTH, 1); // 다음 달로 이동
    firstOfNextMonth.set(Calendar.DAY_OF_MONTH, 1); // 1일로 설정
    firstOfNextMonth.set(Calendar.HOUR_OF_DAY, 0);
    firstOfNextMonth.set(Calendar.MINUTE, 0);
    firstOfNextMonth.set(Calendar.SECOND, 0);
    firstOfNextMonth.set(Calendar.MILLISECOND, 0);

    long diffInMillis = firstOfNextMonth.getTimeInMillis() - now.getTimeInMillis();
    return TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
  }
}
