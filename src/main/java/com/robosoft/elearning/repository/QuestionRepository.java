package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
