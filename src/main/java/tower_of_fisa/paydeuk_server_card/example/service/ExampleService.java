package tower_of_fisa.paydeuk_server_card.example.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tower_of_fisa.paydeuk_server_card.common.ErrorDefineCode;
import tower_of_fisa.paydeuk_server_card.config.exception.custom.exception.AlreadyExistElementException409;
import tower_of_fisa.paydeuk_server_card.domain.entity.Example;
import tower_of_fisa.paydeuk_server_card.example.dto.ExampleRequest;
import tower_of_fisa.paydeuk_server_card.example.dto.ExampleResponse;
import tower_of_fisa.paydeuk_server_card.example.repository.ExampleRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ExampleService {

  private final ExampleRepository exampleRepository;

  /**
   * [예제 도메인 추가] 예제 도메인을 데이터베이스에 신규 추가한다.
   *
   * @param input ExampleRequestDto - 추가할 유저 정보를 담은 DTO 객체
   * @return Long - 추가된 유저의 ID
   */
  @Transactional
  public Long addExample(ExampleRequest input) {
    Example exist = exampleRepository.findFirstByName(input.getName());
    if (exist != null) {
      throw new AlreadyExistElementException409(ErrorDefineCode.DUPLICATE_EXAMPLE_NAME);
    }

    Example example = new Example();
    example.setName(input.getName());

    example = exampleRepository.save(example);
    return example.getExamId();
  }

  /**
   * [예제 도메인 탐색] Keyword를 통해 예제 도메인을 탐색하여 첫 번째 탐색 결과를 반환한다.
   *
   * @param keyword String - 예제 도메인의 검색 키워드
   * @return ExampleResponseDto - 탐색 결과 단일 도메인
   */
  public List<ExampleResponse> searchExampleByKeyword(String keyword) {
    List<Example> examples = exampleRepository.findByKeyword(keyword);
    return examples.stream().map(e -> new ExampleResponse(e.getExamId(), e.getName())).toList();
  }
}
