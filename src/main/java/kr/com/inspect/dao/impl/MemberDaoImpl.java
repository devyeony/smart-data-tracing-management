package kr.com.inspect.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;

/**
 * 회원정보 DAO
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Repository
public class MemberDaoImpl implements MemberDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String memberNs = "MemberMapper.";
	private final String authorityNs = "AuthorityMapper.";

	/**
	 * member 회원가입
	 * @param member 회원 정보
	 * @return 회원 정보값을 세션값 함께 리턴
	 */
	@Override
	public int registerMember(Member member) {
		return sqlSession.insert(memberNs+"registerMember", member);
	}
	
	/**
	 * 회원 정보 수정
	 * @param member 회원정보
	 * @return 회원정보값을 세션값과 함께 리턴
	 */
	public int updateMember(Member member) {
		return sqlSession.update(memberNs+"updateMember", member);
	}
	
	/**
	 * 비밀번호 변경
	 * @param member_id 회원 아이디
	 * @param pwd 회원 비밀번호
	 * @return 회원 비밀번호 값을 세션값과 함께 리턴
	 */
	public int updatePwd(String member_id, String pwd) {
		Map<String, String> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("pwd", pwd);
		return sqlSession.update(memberNs+"updatePwd", map);
	}
	
	/**
	 * member 탈퇴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값과 세션값을 리턴
	 */
	public int deleteMember(String member_id) {
		return sqlSession.delete(memberNs+"deleteMember", member_id);
	}

	/**
	 * 권한 등록 
	 * @param member_id 회원 아이디
	 * @param authority 권한
	 * @return 회원 아이디와 권한 값을 map으로 담아 세션값과 함께 리턴 
	 */
	public int registerAuthority(String member_id, String authority) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", member_id);
		map.put("authority_name", authority);
		return sqlSession.insert(authorityNs+"registerAuthority", map);
	}
	
	/**
	 * 권한을 모두 삭제
	 * @param member_id 회원 아이디
	 * @return 회원아이디에 해당하는 권한을 0으로 설정후 세션값과 함께 리턴
	 */
	public int deleteAuthorities(String member_id) {
		int result = 0;
		return sqlSession.delete(authorityNs+"deleteAuthorities", member_id);
	}
	
	/**
	 * 아이디로 회원정보를 읽음
	 * @param member_id 회원 아이디
	 * @return 회원 아이디로 읽어온 회원 정보와 세션값을 변수 member에 담아 값을 리턴
	 */
	@Override
	public Member readMemberById(String member_id) {
		Member member = null;
		member = sqlSession.selectOne(memberNs+"readMemberById", member_id);
		return member;
	}
	
	/**
	 * 아이디 중복확인
	 * @param member_id 회원아이디
	 * @return 회원 아이디로 중복 확인 후 변수 result에 담아 값을 리턴
	 */
	@Override
	public int idCheck(String member_id) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"idCheck", member_id);
		return result;
	}
	
	/**
	 * 이메일 중복확인
	 * @param email 이메일 정보
	 * @return 이메일 정보 중복 확인 후 변수 result에 담아 값을 리턴
	 */
	public int emailCheck(String email) {
		System.out.println("요청이 들어옴");
		int result = 0;
		System.out.println(email);
		result = sqlSession.selectOne(memberNs+"emailCheck", email);
		return result;
	}
	
	/**
	 * 연락처 중복확인
	 * @param phone 연락처 정보
	 * @return 연락처 정보 중복확인 후 변수 result 값에 담아 리턴
	 */
	public int phoneCheck(String phone) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"phoneCheck", phone);
		return result;
	}

	/**
	 * id로 가지고 있는 권한들을 가져옴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디로 권한정보를 불러온후 세션값과 함께 리스트로 담아 리턴
	 */
	@Override
	public List<String> readAuthorities(String member_id) {
		return sqlSession.selectList(authorityNs+"readAuthorities", member_id);
	}

	/**
	 * 회원 정보 모두 가지고옴 
	 * @return 회원 목록
	 */
	public List<Member> getMemberList(){ 
		return sqlSession.selectList(memberNs+"getMemberList"); 	
	}
	
	/**
	 * 권한명으로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @return 해당 권한을 가진 회원 목록
	 */
	@Override
	public List<Member> getMemberListUsingRole(String role){
		return sqlSession.selectList(memberNs+"getMemberListUsingRole", role); 
	}

	/**
	 * 회원 수를 가져옴
	 * @return 회원 수
	 */
	public int getMemberCount(){
		return sqlSession.selectOne(memberNs+"getMemberCount");
	}
}
