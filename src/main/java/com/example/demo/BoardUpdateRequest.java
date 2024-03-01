package com.example.demo;

public class BoardUpdateRequest {
    private String title;
    private String content;
    private String password; // 게시글 수정을 위한 비밀번호 필드 추가
    private String username;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	 
    
    
    // Getter와 Setter 생략
    
}

