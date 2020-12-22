package kr.com.inspect.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.UsingLog;

/**
 * 사용 로그에 ip, 접속 시간, 아이디, 로그 내용을 세팅함
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component
public class UsingLogUtil {
	/**
	 * MemberDao dao 필드 선언
	 */
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * 사용 로그에 ip, 접속 시간, 아이디, 로그 내용을 세팅함
	 * @param content 사용 로그 내용
	 * @return 사용 로그 정보가 담긴 UsingLog 객체
	 */
	public void setUsingLog(UsingLog usingLog){
		if(usingLog.getIp_addr() == null) {
			usingLog.setIp_addr(getIpAddr());
		}
		if(usingLog.getTime() == null) {
			usingLog.setTime(getTime());
		}
		if(usingLog.getMember_id() == null) {
			usingLog.setMember_id(getMemberId());
		}
		memberDao.insertIntoUsingLog(usingLog);
	}
	
	/**
	 * 클라이언트의 ip 주소를 가져옴
	 * @return ip 주소
	 */
	public String getIpAddr() {
		String ip_addr = null;
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		
		ip_addr = request.getHeader("X-Forwarded-For");
		if (ip_addr == null) {
			ip_addr = request.getHeader("Proxy-Client-IP");
		}
		if (ip_addr == null) {
			ip_addr = request.getHeader("WL-Proxy-Client-IP"); 
	    }
		if (ip_addr == null) {
			ip_addr = request.getHeader("HTTP_CLIENT_IP");
	    }
		if (ip_addr == null) {
			ip_addr = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
		if (ip_addr == null) {
			ip_addr = request.getRemoteAddr();
	    }
		return ip_addr;
	}
	
	/**
	 * 클라이언트의 현재 시간을 가져옴
	 * @return 현재 시간
	 */
	public String getTime() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(zone);
		String currentTime = format.format(System.currentTimeMillis());
		return currentTime;
	}
	
	/**
	 * 스프링 시큐리티에서 로그인한 사용자의 아이디를 가져옴
	 * @return 로그인한 사용자의 아이디
	 */
	public String getMemberId(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		UserDetails userDetails = (UserDetails)principal; 
		String username = userDetails.getUsername();
		return username;
	}
}
