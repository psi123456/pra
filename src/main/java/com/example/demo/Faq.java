package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="FAQ")
public class Faq {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_no_seq")
	@SequenceGenerator(name = "faq_no_seq", sequenceName = "faq_no_seq", allocationSize = 1)
	private long no;
	   @Column(name="question")
	   private String question;
	   @Column(name ="answer")
	   private String answer;

	
	public Faq() { } // 배열 default 생성자
	public Faq(long no,String question,String answer) {
		this.question = question;
		this.answer=answer;
	} 
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString() {
	    return "Faq [no=" + no + ", question=" + question + ", answer=" + answer + "]";
	}
	

}