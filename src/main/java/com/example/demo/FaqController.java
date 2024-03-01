package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;


@RestController // Rest 프로그래밍이 중요한 이유 : traffic이 줄어들고 속도가 빨라짐 
@RequestMapping("/api")
public class FaqController {
    @Autowired // 자동주입
    FaqRepository FaqRepository; // 
    @CrossOrigin(origins = "*") // 클라이언트 앱의 주소로 변경
    @GetMapping("/faq")
    public ResponseEntity<List<Faq>> getAllFaq() 
    {
        try {
            List<Faq> faq = new ArrayList<Faq>();
////            if (name ==null)
//            	System.out.println("null"); // cache에 데이터가 없는 캐시가 정의
//            // 함숳여 프로그래밍
//            // 람다함수 호출
             FaqRepository.findAll().forEach(faq::add);
            if (faq.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(faq, HttpStatus.OK); // 자동으로 json
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/faq/{no}")
    public ResponseEntity<Faq> getFaqById(@PathVariable("no") long no) {
        Optional<Faq> faqData = FaqRepository.findById(no);
        if (faqData.isPresent()) {
            return new ResponseEntity<>(faqData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/faq") // get이 있어야 post 아니면 거절됨
    public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
        try {
            // 여기서는 클라이언트로부터 받은 FAQ 객체를 직접 저장합니다.
            // ID는 데이터베이스에서 자동으로 생성되도록 설정해야 합니다.
            Faq _faq = FaqRepository.save(faq);
            return new ResponseEntity<>(_faq, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/faq/{no}")
    @Transactional
    public ResponseEntity<Faq> updateFaq(@PathVariable("no") long no, @RequestBody Faq faq) {
        Optional<Faq> faqData = FaqRepository.findById(no);
        if (faqData.isPresent()) {
            Faq _faq = faqData.get();
            _faq.setQuestion(faq.getQuestion());
            _faq.setAnswer(faq.getAnswer());
            return new ResponseEntity<>(FaqRepository.save(_faq), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/faq/{no}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteFaq(@PathVariable("no") long no) {
        try {
            FaqRepository.deleteById(no);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/faq")
    public ResponseEntity<HttpStatus> deleteAllFaq() {
        try {
            FaqRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}