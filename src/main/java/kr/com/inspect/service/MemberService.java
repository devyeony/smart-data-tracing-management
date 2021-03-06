package kr.com.inspect.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.ResponseData;

/**
 * 회원정보 Service Interface
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 *
 */

public interface MemberService extends UserDetailsService {
	
	/**
	 * 회원가입
	 * @param uploadImgFile 업로드한 이미지 파일
	 * @param member 회원 정보
	 * @return 회원정보값을 리턴
	 */
	public int registerMember(MultipartFile[] uploadImgFile, Member member); 
	
	/**
	 * 아이디로 회원정보를 가져옴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값을 리턴
	 */
	public Member readMemberById(String member_id); 
	
	/**
	 * 회원가입시 해당 요소가 DB에 존재하는지 중복 체크 
	 * @param object DB 객체
	 * @param value 여부 확인
	 * @return DB에 존재하는 해당 요소의 총 row 수를 리턴
	 */
	public int registerCheck(String object, String value);

	/**
	 * 회원 정보를 페이징 처리하여 가져옴
	 * @return 회원 목록
	 */
	public List<Member> getMemberList();
	
	/**
	 * 사용자 목록 테이블을 페이징 처리하여 가져옴
	 * @param role 사용자 권한
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 사용자 목록 테이블
	 */
	public ResponseData getMemberList(String role,
											String function_name, 
											int current_page_no,
											int count_per_page,
											int count_per_list,
											String search_word,
											String approval);
	
	/**
	 * 회원 정보 수정
	 * @param uploadImgFile 업로드한 이미지 파일
	 * @param member 회원 정보
	 * @param changeToDefaultImg 프로필 이미지를 기본 이미지로 바꾸는지 여부
	 * @return member 테이블에 수정된 row의 수
	 */
	public int updateMember(MultipartFile[] uploadImgFile, Member member, boolean changeToDefaultImg);
	
	/**
	 * 관리자 권한으로 회원 정보(권한) 수정
	 * @param member_id 회원 아이디
	 * @param authoritiesArr 권한 배열
	 * @return authority 테이블에 수정된 row의 수
	 */
	public int updateAuthorities(String member_id, String[] authoritiesArr);
	
	/**
	 * 비밀번호 변경
	 * @param member_id 회원 아이디
	 * @param pwd 회원 비밀 번호
	 * @return member 테이블에 수정된 row의 수
	 */
	public int updatePwd(String member_id, String pwd);
	
	/**
	 * 비밀번호를 잊어버린 회원에게 아이디와 이메일을 받고 임시 비밀번호를 메일로 발송 
	 * @param member_id 회원 아이디
	 * @param email 회원 이메일
	 * @return 성공 여부
	 */
	public String sendPwdToEmail(String member_id, String email);
	
	/**
	 * 회원 탈퇴 
	 * @param member_id 회원 아이디
	 */
	public void deleteMember(String member_id);
	
	/**
	 * Spring Security에서 아이디로 회원 정보를 읽어옴
	 * @param username 회원 이름
	 * @exception UsernameNotFoundException 유저정보 예외처리
	 * @return 회원 정보
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	/**
	 * 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함
	 * @param username 회원 이름
	 * @return 권한 목록
	 */
	public Collection<GrantedAuthority> getAuthorities(String username);
	
	/**
	 * 사용할 PasswordEncoder를 리턴해줌
	 * @return 비밀번호 암호화 객체
	 */
	public PasswordEncoder passwordEncoder();
	
	/**
	 * 사용 로그 테이블을 페이징 처리하여 가져옴
	 * @param member_id 사용자 아이디
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @param log_type 상세 검색 타입(사용자 아이디/사용 내역/IP 주소/접속 시간) 중 하나
	 * @param searchMap 상세 검색어(사용자 아이디/사용 내역/IP 주소/접속 시간) 값을 담고 있는 Map
	 * @return 사용 로그 테이블
	 */
	public ResponseData getUsingLog(String member_id,
									String function_name, 
									int current_page_no,
									int count_per_page,
									int count_per_list,
									String search_word,
									String log_type,
									Map<String, Object> searchMap);

	/**
	 * 관리자 권한으로 가입 승인
	 * @param member 회원
	 */
	public void updateMemberApprovalUsingId(Member member);
	
	/**
	 * 사용 로그에 로그아웃을 기록함
	 */
	public void recordLogout();

	/**
	 * 스케쥴러로 인한 멤버 삭제
	 * @param member_id 삭제할 계정 id
	 */
	public void deleteMemberByScheduler(String member_id);

	/**
	 * 로그인 할 때마다 마지막 로그인 시간을 업데이트
	 * @param member_id 업데이트 할 계정 id
	 */
	public void updateLoginTime(String member_id);

	/**
	 * 회원 정보를 모두 가져옴
	 * @return 회원 목록
	 */
	public List<Member> getAllMemberList();

	/**
	 * 3개월 이상 접속하지 않은 계정 만료
	 * @param member_id 만료할 계정 ID
	 */
	public void accountExpired(String member_id);

	/**
	 * 관리자 권한으로 계정 활성화
	 * @param member_id 활성화 할 member_id
	 */
	public void updateAccountActivation(String member_id);
}
