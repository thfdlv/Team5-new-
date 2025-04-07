package com.example.jsonExam.member;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.jsonExam.member.UserDTO;
import com.example.jsonExam.member.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




// Controller Layer
@Controller
@RequestMapping("/member")
public class UserController {
    @Autowired private UserService userService;
    @Autowired private HttpSession session;

    @PostMapping("/regist") // 등록 경로 변경
    public String regist( UserDTO user, RedirectAttributes ra) {
    	
    	 System.out.println("🔍 받은 유저 정보: " + user.getId());
    	    System.out.println("🔍 이름: " + user.getUserName());
    	    System.out.println("🔍 비밀번호 확인: " + user.getConfirm());
        String msg = userService.regist(user); // user.registProc → userService.regist 변경

        ra.addFlashAttribute("msg", msg);

        if (msg.equals("회원가입 성공!")) {
            return "redirect:http://www.team5.click/project1/login"; // 로그인 페이지로 이동
        }
        return "redirect:http://www.team5.click/project1/template"; // 실패하면 다시 회원가입 폼으로 이동
    }
    
    
    
    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes ra) {

        if (id == null || id.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            ra.addFlashAttribute("msg", "아이디와 비밀번호를 모두 입력하세요.");
            return "redirect:/login";
        }

        UserDTO user = userService.login(id, password);

        if (user == null) {
            ra.addFlashAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
            return "redirect:/login";
        }

        session.setAttribute("id", user.getId()); // ✅ 로그인 성공 시 세션에 id 저장
        return "redirect:/template";
    }





    
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // ✅ 로그아웃 후 메인 페이지로 이동
        return "redirect:/template";
    }
    
   
    
    @GetMapping("/session")
    @ResponseBody
    public Map<String, Object> getSession(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String userId = (String) session.getAttribute("id"); // ✅ 세션에서 사용자 ID 가져오기

        if (userId != null) {
            response.put("loggedIn", true);
            response.put("userId", userId);
        } else {
            response.put("loggedIn", false);
        }
        return response;
    }
    
    
    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("id");

        if (userId == null) {
            return "redirect:/login";
        }

        System.out.println("🔍 세션 유저 ID: " + userId); // 확인
        UserDTO user = userService.findById(userId);
        
        if (user == null) {
            System.out.println("❌ 사용자 정보 없음!");
        } else {
            System.out.println("✅ 사용자 정보 있음: " + user.getUserName());
        }

        model.addAttribute("user", user);
        return "mypage";
    }


    
    
    @RequestMapping("update")
	public String update() {
		String sessionId = (String)session.getAttribute("id");
		if(sessionId == null)
			return "redirect:login";
		
		return "/MypageUpdate";
	}
	
    @PostMapping("/updateProc")
    public String updateProc(UserDTO member, Model model, HttpSession session) {
        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null) return "redirect:/login";

        member.setId(sessionId);
        String msg = userService.updateProc(member); // ✅ 변수명도 맞춰야 함

        if ("회원 수정 완료".equals(msg)) {
            session.invalidate();
            return "redirect:/template";
        }

        model.addAttribute("msg", msg);
        return "/MypageUpdate";
    }
	
	//http://localhost:8086/dbQuiz/delete
    @GetMapping("/delete")
    public String delete() {
        return "/MypageDelete"; // 삭제 확인 페이지
    }

    @PostMapping("/deleteProc")
    public String deleteProc(UserDTO member, Model model, HttpSession session) {
        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null) {
            return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로
        }

        member.setId(sessionId); // 세션에서 아이디 가져와서 DTO에 설정
        String msg = userService.deleteProc(member); // 서비스 호출

        if ("회원 삭제 완료".equals(msg)) {
            session.invalidate(); // 세션 초기화
            return "redirect:/template"; // 메인 페이지로
        }

        model.addAttribute("msg", msg);
        return "/MypageDelete"; // 삭제 실패시 메시지와 함께 원래 페이지로
    }



}

    
    
    


