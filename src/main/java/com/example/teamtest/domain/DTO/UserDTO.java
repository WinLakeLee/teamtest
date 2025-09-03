package com.example.teamtest.domain.DTO;


import java.util.List;

import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.entity.UserEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	@NotNull
	private Long id;

	@NotNull(message = "이름을 입력해 주세요")
	@NotBlank(message = "띄어쓰기는 안됩니다")
	@Size(min = 2, max = 10, message = "2 ~ 10자 이내로 해주세요")
	private String username;
	
	@NotNull(message = "닉네임을 설정해 주세요")
	@NotBlank(message = "띄어쓰기는 안됩니다")
	@Size(min = 2, max = 10, message = "2 ~ 10자 이내로 해주세요")
	private String nickname;
	
	@NotNull(message = "비밀번호를 설정해 주세요")
	@NotBlank(message = "띄어쓰기는 안됩니다")
	@Size(min = 2, max = 20, message = "2~ 20 자 이내로 해주세요")
	private String password;
	
	@NotNull(message = "이메일을 입력해 주세요")
	@NotBlank(message = "띄어쓰기는 안됩니다")
	@Email(message = "이메일을 입력해 주세요")
	private String email;
	
	@NotNull
	private int point;
	
	@NotNull
	private Grade grade;
	
	private String nicknameBg;

}