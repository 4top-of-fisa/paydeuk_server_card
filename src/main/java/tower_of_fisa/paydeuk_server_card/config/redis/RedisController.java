package tower_of_fisa.paydeuk_server_card.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
  private final RedisService redisService;

  // 조회
  @GetMapping("/get")
  public String getValue(@RequestParam String key) {
    String value = redisService.getValue(key);
    return value != null ? value : "Key not found";
  }
}
