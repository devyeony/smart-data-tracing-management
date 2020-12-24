package kr.com.inspect.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.dto.UsingLog;

/**
 * 사용 로그에 ip, 접속 시간, 아이디, 로그 내용을 세팅함
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component
public class UsingLogUtil {
	
	/**
	 * 클라이언트 정보 필드
	 */
	@Autowired
	private ClientInfo clientInfo;
	
	/**
	 * MemberDao Member DAO 필드 선언
	 */
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * RuleDao Rule DAO 필드 선언
	 */
	@Autowired
	private RuleDao ruleDao;
	
	/**
	 * 사용 로그에 ip, 접속 시간, 아이디, 로그 내용을 세팅하고 DB에 등록함
	 * @param usingLog 사용 로그 객체
	 */
	public void setUsingLog(UsingLog usingLog){
		System.out.println("파라미터 : "+usingLog);
		if(usingLog.getIp_addr() == null) {
			usingLog.setIp_addr(clientInfo.getIpAddr());
		}
		if(usingLog.getTime() == null) {
			usingLog.setTime(clientInfo.getTime());
		}
		if(usingLog.getMember_id() == null) {
			usingLog.setMember_id(clientInfo.getMemberId());
		}
		
		System.out.println("세팅 후 : "+usingLog);
		if(usingLog instanceof RuleLog) {
			System.out.println("캐스팅 전 : "+usingLog);
			RuleLog ruleLog = (RuleLog) usingLog;
			System.out.println("캐스팅 후 : "+ruleLog);
			if(ruleLog.getUsing_log_no() == 0) { //아직 사용 로그를 등록하지 않은 경우
				memberDao.insertIntoUsingLog(usingLog);
				ruleLog.setUsing_log_no(getNoOfUsingLog(usingLog));
				System.out.println("사용 로그 등록 후 외래키 세팅 : "+ruleLog);
			}
			ruleDao.insertIntoRuleLog(ruleLog);
			System.out.println("룰 로그 등록 : "+ruleLog);
		}else {
			memberDao.insertIntoUsingLog(usingLog);
			System.out.println("사용 로그 등록 : "+usingLog);
		}
	}
	
	/**
	 * 사용 로그의 Primary Key인 순번을 가져옴
	 * @param usingLog 사용 로그 객체
	 * @return 사용 로그의 Primary Key인 순번
	 */
	public int getNoOfUsingLog(UsingLog usingLog) {
		return memberDao.getUsingLog(usingLog).getNo();
	}
}
