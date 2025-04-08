package com.example.jsonExam.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    @Autowired private UserMapper userMapper;
    

    public String regist(UserDTO user) {
        if (user == null) {
            return "잘못된 요청입니다. 회원 정보를 입력하세요.";
        }

        // 아이디 중복 체크
        if (userMapper.getUserById(user.getId()) != null) {
            return "이미 존재하는 아이디입니다.";
        }

        // ✅ 여기에서 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(user.getPw());
        user.setPw(encodedPw);  // 암호화된 비밀번호 저장

        userMapper.insertUser(user); // DB 저장
        return "회원가입 성공!";
    }

    
    public void registerUser(UserDTO user) {
        userMapper.registerUser(user);
    }

    private HttpSession session;  // 🔹 세션 객체 주입

    public UserDTO login(String id, String password) {
        UserDTO user = userMapper.getUserById(id);
        if (user == null) return null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password, user.getPw())) {
            return user;
        }

        return null;
    }


    public boolean updateUser(UserDTO user) {
        int result = userMapper.updateUser(user); // 수정된 행의 수 반환
        return result > 0;
    }

    public boolean deleteUser(String id) {
        int result = userMapper.deleteUser(id); // ✅ 인스턴스 방식 호출
        return result > 0;
    }

    
    public UserDTO getLoggedInUser(HttpSession session) {
        return (UserDTO) session.getAttribute("loginUser");
    }

	public void logout() {
		// TODO Auto-generated method stub
		
	}

	public UserDTO findById(String userId) {
	    return userMapper.getUserById(userId); // ✅ 수정 완료
	}

	public String updateProc(UserDTO member) {
	    if(member.getPw() == null || member.getPw().trim().isEmpty()) {
	        return "비밀번호를 입력하세요.";
	    }
	    if(!member.getPw().equals(member.getConfirm())) {
	        return "두 비밀번호를 일치하여 입력하세요.";
	    }
	    if(member.getUserName() == null || member.getUserName().trim().isEmpty()) {
	        return "이름을 입력하세요.";
	    }

	    // ✅ 비밀번호 암호화
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String secretPass = encoder.encode(member.getPw());
	    member.setPw(secretPass);

	    // ✅ DB에 업데이트 요청
	    int result = userMapper.updateUser(member); // <-- 진짜 DB 호출
	    if(result == 1)
	        return "회원 수정 완료";

	    return "회원 수정을 다시 시도하세요.";
	}


	public String deleteProc(UserDTO member) {
	    System.out.println("📌 입력 PW: " + member.getPw());
	    System.out.println("📌 입력 CONFIRM: " + member.getConfirm());

	    if (member.getPw() == null || member.getPw().trim().isEmpty()) {
	        return "비밀번호를 입력하세요.";
	    }

	    if (!member.getPw().equals(member.getConfirm())) {
	        return "두 비밀번호를 일치하여 입력하세요.";
	    }

	    UserDTO check = userMapper.getUserById(member.getId());
	    System.out.println("📌 세션 ID: " + member.getId());
	    if (check == null) {
	        System.out.println("❌ 해당 유저 없음");
	        return "회원 정보가 존재하지 않습니다.";
	    }

	    System.out.println("📌 DB에 저장된 PW: " + check.getPw());
	    System.out.println("📌 비교 결과: " + member.getPw().equals(check.getPw()));

	    if (member.getPw().equals(check.getPw())) {
	        int result = userMapper.deleteUser(member.getId());
	        return result == 1 ? "회원 삭제 완료" : "회원 삭제를 다시 시도하세요.";
	    }

	    return "아이디 또는 비밀번호를 확인 후 입력하세요.";
	}




    
}
