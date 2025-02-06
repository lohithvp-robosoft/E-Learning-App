package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findFirstByTestIdOrderByIdAsc(Long testId);

    Optional<Question> findByIdAndTestId(Long questionId, Long testId);

    Optional<Question> findFirstByTestIdAndIdGreaterThanOrderById(Long testId, Long questionId);

    Optional<Question> findFirstByTestIdAndIdLessThanOrderByIdDesc(Long testId, Long questionId);

    int countByTestId(Long testId);

    List<Question> findByTestIdOrderById(Long testId);

    int countByTestIdAndIdLessThan(Long testId, Long questionId);

    List<Question> findByTestId(Long testId);
}
