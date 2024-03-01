package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="Board")
public class Board {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_no_seq")
    @SequenceGenerator(name = "board_no_seq", sequenceName = "board_no_seq", allocationSize = 1)
    private long no;

    @Column(name="title")
    private String title;

    @Lob
    private String content;

    @Column(name="username")
    private String username;

    @Column(name="viewCount")
    private int viewCount;
    
    @Column(name="password")
    private String password; // 비밀번호 타입을 String으로 변경

    public Board() { }

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
 
}

