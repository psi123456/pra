package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByQuestionContaining(String question);
}
