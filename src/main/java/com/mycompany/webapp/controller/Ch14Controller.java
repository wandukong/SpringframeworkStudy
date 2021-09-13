package com.mycompany.webapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.service.Ch14MemeberService;
import com.mycompany.webapp.service.Ch14MemeberService.JoinResult;
import com.mycompany.webapp.service.Ch14MemeberService.LoginResult;

@Controller
@RequestMapping("/ch14")
public class Ch14Controller {

	private static final Logger logger = LoggerFactory.getLogger(Ch14Controller.class);

	@Resource
	private DataSource dataSource; // dataSource 구현한 빈 객체 주입

	@RequestMapping("/content")
	public String content() {
		return "ch14/content";
	}

	@GetMapping("/testConnectToDB")
	public String testConnectToDB() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		logger.info("연결 성공");

		// 커넥션 풀로 연결 객체를 반납하기
		conn.close();
		return "redirect:/ch14/content";
	}

	@GetMapping("/testInsert")
	public String testInsert() throws SQLException {

		Connection conn = dataSource.getConnection();
		try {
			// 작업 처리
			String sql = "insert into board values(seq_bno.nextval, ?, ?, sysdate, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "오늘은 월요일");
			pstmt.setString(2, "스트레스가 이빠이 올라갔어요");
			pstmt.setString(3, "user");
			pstmt.executeUpdate();
			logger.info("insert 성공");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 커넥션 풀로 연결 객체를 반납하기 -> 예외가 발생하든 안하든 커낵션 풀을 무조건 반납해줘야한다.
		conn.close();
		return "redirect:/ch14/content";
	}

	@GetMapping("/testSelect")
	public String testSelect() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();

		try {
			// 작업 처리
			String sql = "select bno, btitle, bcontent, bdate, mid from board";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int bno = rs.getInt("bno");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Date bdate = rs.getDate("bdate");
				String mid = rs.getString("mid");
				logger.info(bno + "\t" + btitle + "\t" + bcontent + "\t" + bdate + "\t" + mid);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testUpdate")
	public String testUpdate() throws SQLException {

		Connection conn = dataSource.getConnection();
		try {
			String sql = "update board set btitle=?, bcontent=? where bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "배고파요");
			pstmt.setString(2, "점심 먹으러 언제 가요?");
			pstmt.setInt(3, 3);
			pstmt.executeUpdate();
			logger.info("Update 성공");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testDelete")
	public String testDelete() throws SQLException {

		Connection conn = dataSource.getConnection();
		try {
			String sql = "delete from board where bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.executeUpdate();
			logger.info("Delete 성공");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@Resource
	private Ch14MemeberService memberService;
	
	@GetMapping("/join")
	public String joinForm() {
		return "ch14/joinForm";
	}
	
	@PostMapping("/join")
	public String join(Ch14Member member, Model model) {
		member.setMenabled(1);
		member.setMrole("ROLE_USER");
		JoinResult jr = memberService.join(member);
		if(jr == JoinResult.SUCCESS) {
			return "redirect:/ch14/content";
		}else if(jr == JoinResult.DUPLICATED) {
			model.addAttribute("error", "중복된 아이디가 있습니다.");
			return "ch14/joinForm";
		}else { // 예외 발생시
			model.addAttribute("error", "회원 가입이 실패되었습니다. 다시 시도해 주세요.");
			return "ch14/joinForm";
		}
	}
	
	
	@GetMapping("/login")
	public String loginForm() {
		return "ch14/loginForm";
	}
	
	@PostMapping("/login")
	public String login(Ch14Member member, Model model) {
		LoginResult lr = memberService.login(member);
		if(lr == LoginResult.SUCCESS) {
			return "redirect:/ch14/content";
		}else if(lr == LoginResult.WRONG_ID) {
			model.addAttribute("error", "등록되지 않은 아이디입니다.");
			return "ch14/loginForm";
		}else if(lr == LoginResult.WRONG_PASSWORD) {
			model.addAttribute("error", "패스워드가 잘못 되었습니다.");
			return "ch14/loginForm";
		}else {
			model.addAttribute("error", "회원 가입이 실패되었습니다. 다시 시도해 주세요.");
			return "ch14/loginForm";
		}
	}
}
