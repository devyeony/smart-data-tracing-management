package kr.com.inspect.dao;

import java.util.List;
import java.util.Map;

import kr.com.inspect.dto.*;

/**
 * 전사규칙과 관련된 DAO Interface
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 */
public interface RuleDao {
	/**
	 * 전사규칙 대분류 드롭다운 목록을 리턴함
	 * @return 전사규칙 대분류 목록
	 */
	public List<Rule> getAllRuleTopLevel();
	
	/**
	 * 전사규칙 중분류 드롭다운 목록을 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 전사규칙 중분류 목록
	 */
	public List<Rule> getAllRuleMiddleLevel(int top_level_id);
	
	/**
	 * 전사규칙 소분류 드롭다운 목록을 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 전사규칙 소분류 목록
	 */
	public List<Rule> getAllRuleBottomLevel(int top_level_id, int middle_level_id);
	
	/**
	 * 전사규칙 소분류 아이디로 해당되는 항목을 리턴함
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 전사규칙 소분류 항목
	 */
	public Rule getRuleBottomLevel(int bottom_level_id);
	
	/**
	 * 전사규칙 리스트의 총 개수를 가져옴
	 * @param rule_type 룰 타입(전체/SQL/메서드)
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @param search_word 검색어
	 * @return 전사규칙 리스트의 총 개수
	 */
	public int getAllCountOfRuleList(String rule_type,
										String top_level_id, 
										String middle_level_id, 
										String bottom_level_id, 
										String search_word);
	
	/**
	 * 해당되는 전사규칙 리스트를 가지고 옴
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 해당되는 전사규칙 리스트
	 */
	public List<Rule> getRuleList(String top_level_id, 
									String middle_level_id, 
									String bottom_level_id);
	
	/**
	 * 전사규칙 리스트를 페이징 처리하여 리턴함
	 * @param rule_type 룰 타입(전체/SQL/메서드)
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 페이징 처리된 전사규칙 리스트
	 */
	public List<Rule> getRuleListByPaging(String rule_type,
									String top_level_id, 
									String middle_level_id, 
									String bottom_level_id,
									int limit, 
									int offset,
									String search_word);
	
	/**
	 * 대분류를 등록함
	 * @param rule 대분류 등록을 위한 Rule 객체 
	 * @return 등록된 대분류 DB row의 수
	 */
	public int registerTopLevel(Rule rule);
	
	/**
	 * 중분류를 등록함
	 * @param rule 중분류 등록을 위한 Rule 객체 
	 * @return 등록된 중분류 DB row의 수
	 */
	public int registerMiddleLevel(Rule rule);
	
	/**
	 * 전사규칙을 등록함
	 * @param rule 전사규칙 등록을 위한 Rule 객체
	 * @return 등록된 소분류 DB row의 수
	 */
	public int registerBottomLevel(Rule rule);
	
	/**
	 * 이전 전사규칙을 등록함
	 * @param rule 이전 전사규칙 기록을 위한 Rule 객체
	 * @return 등록된 이전 전사규칙 DB row의 수
	 */
	public int registerPrevBottomLevel(Rule rule);
	
	/**
	 * 이전 전사규칙을 업데이트함
	 * @param rule 이전 전사규칙 업데이트를 위한 Rule 객체
	 * @return 업데이트된 이전 전사규칙 DB row의 수
	 */
	public int updatePrevBottomLevel(Rule rule);
	
	/**
	 * 대분류가 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * @param rule 대분류 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	public int isExistTopLevel(Rule rule);
	
	/**
	 * 중분류가 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * @param rule 중분류 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	public int isExistMiddleLevel(Rule rule);
	
	/**
	 * 전사규칙(소분류)이 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * @param rule 전사규칙(소분류) 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	public int isExistBottomLevel(Rule rule);
	
	/**
	 * 전사규칙(소분류)의 파일명을 업데이트함
	 * @param rule 전사규칙(소분류) 파일명 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 개수
	 */
	public int updateBottomLevelFileName(Rule rule);
	
	/**
	 * 사용자가 작성한 전사규칙(소분류) 코드 내용 및 정보를 DB에 저장함
	 * @param rule 전사규칙(소분류) 코드 내용 및 정보 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 수
	 */
	public int updateRuleContents(Rule rule);
	
	/**
	 * 사용자가 작성한 전사규칙(소분류)을 컴파일하고 결과값을 DB에 저장함
	 * @param rule 전사규칙(소분류) 컴파일 결과값 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 수
	 */
	public int updateRuleCompileResult(Rule rule);
	
	/**
	 * 해당되는 대분류와 종속된 중분류, 소분류를 삭제함
	 * @param id 대분류 아이디
	 * @return 삭제된 row의 수
	 */
	public int deleteTopLevel(int id);
	
	/**
	 * 해당되는 중분류와 종속된 소분류를 삭제함
	 * @param id 중분류 아이디
	 * @return 삭제된 row의 수
	 */
	public int deleteMiddleLevel(int id);
	
	/**
	 * 해당되는 전사규칙(소분류)를 삭제함
	 * @param id 전사규칙(소분류) 아이디
	 * @return 삭제된 row의 수
	 */
	public int deleteBottomLevel(int id);
	
	/**
	 * 해당되는 이전 전사규칙(소분류)를 삭제함
	 * @param id 전사규칙(소분류) 아이디
	 * @return 삭제된 row의 수
	 */
	public int deletePrevBottomLevel(int id);
	
	/**
	 * 클래스 아이디로 해당되는 클래스 정보를 가져옴
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClass(int class_id);
	
	/**
	 * 클래스 아이디로 해당되는 클래스 필드 정보를 가져옴
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 필드 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClassField(int class_id);
	
	/**
	 * 클래스 아이디로 해당되는 클래스 생성자 정보를 가져옴
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 생성자 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClassConstructor(int class_id);
	
	/**
	 * 클래스 아이디로 해당되는 클래스 메소드 정보를 가져옴
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 메소드 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClassMethod(int class_id);
	
	/**
	 * 사용자가 import하고자 하는 커스텀 라이브러리를 등록함
	 * @param customLibrary 등록할 라이브러리 정보를 담고 있는 객체
	 * @return DB에 추가된 row의 수
	 */
	public int registerCustomLibrary(CustomLibrary customLibrary);
	
	/**
	 * 아이디로 커스텀 라이브러리를 가져옴
	 * @param id 커스텀 라이브러리 아이디
	 * @return 커스텀 라이브러리 객체
	 */
	public CustomLibrary getCustomLibraryById(int id);
	
	/**
	 * 사용자 아이디로 사용자가 추가한 커스텀 라이브러리 목록을 가져옴
	 * @param creator 사용자 아이디
	 * @return 사용자가 추가한 커스텀 라이브러리 목록
	 */
	public List<CustomLibrary> getAllCustomLibraryByCreator(String creator);
	
	/**
	 * 해당되는 커스텀 라이브러리를 삭제함
	 * @param id 아이디
	 * @return DB에서 삭제된 row의 수
	 */
	public int deleteCustomLibrary(int id);

	/**
	 * class 파일의 package를 업데이트
	 * @param customLibrary 사용자가 업로드한 customLibrary
	 * @return 업데이트된 row의 개수
	 */
	public int updateCustomLibraryPackage(CustomLibrary customLibrary);
	
	/**
	 * 룰 로그에 기록함
	 * @param ruleLog 룰 로그에 기록할 RuleLog 객체
	 * @return DB에 추가된 row의 수
	 */
	public int insertIntoRuleLog(RuleLog ruleLog);
	
	/**
	 * 디테일 룰 로그(2개 이상 실행/다운로드/삭제)에 기록함
	 * @param ruleLogDetail 디테일 룰 로그에 기록할 RuleLog 객체
	 * @return DB에 추가된 row의 수
	 */
	public int insertIntoRuleLogDetail(RuleLog ruleLogDetail);
	
	/**
	 * 사용 기록 번호로 디테일한 룰 로그 목록을 가져옴
	 * @param using_log_no 사용 기록 번호
	 * @return 디테일한 룰 로그 목록
	 */
	public List<RuleLog> getAllRuleLogDetailByUsingLogNo(int using_log_no);
	
	/**
	 * 룰 로그를 모두 가져옴
	 * @param using_log_no RuleLog 테이블의 외래키인 using_log_no
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @param log_type 상세 검색 타입(사용자 아이디/사용 내역/IP 주소/접속 시간) 중 하나
	 * @param searchMap 상세 검색어(사용자 아이디/사용 내역/IP 주소/접속 시간) 값을 담고 있는 Map
	 * @return 룰 로그 목록
	 */
	public List<RuleLog> getAllRuleLog(int using_log_no,
									int limit, 
									int offset,
									String search_word,
									String log_type, 
									Map<String, Object> searchMap);
	
	/**
	 * 룰 로그의 총 개수를 가져옴
	 * @param using_log_no RuleLog 테이블의 외래키인 using_log_no
	 * @param search_word 검색어
	 * @param log_type 상세 검색 타입(사용자 아이디/사용 내역/IP 주소/접속 시간) 중 하나
	 * @param searchMap 상세 검색어(사용자 아이디/사용 내역/IP 주소/접속 시간) 값을 담고 있는 Map
	 * @return 룰 로그 총 개수
	 */
	public int getAllCountOfRuleLog(int using_log_no, String search_word,
			String log_type, Map<String, Object> searchMap);
	
	/**
	 * 해당되는 룰의 버전 관리 목록을 가져옴
	 * @param bottom_level_id 룰 소분류 아이디
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 룰의 버전 관리 목록
	 */
	public List<Rule> getPrevRuleVersionList(int bottom_level_id, 
			int limit, int offset, String search_word);
	
	/**
	 * 해당되는 룰의 버전 관리 개수를 가져옴
	 * @param bottom_level_id 소분류 id
	 * @param search_word 검색어
	 * @return 룰의 버전 관리 개수
	 */
	public int getCountOfPrevRuleVersion(int bottom_level_id, String search_word);
	
	/**
	 * 룰의 버전별 변화(수정 전/수정 후)를 가져옴
	 * @param bottom_level_id 룰 소분류 아이디
	 * @param prev_bottom_level_id 룰 버전 관리 목록 아이디
	 * @return 룰의 버전별 변화(수정 전/수정 후)
	 */
	public List<Rule> getRuleChange(int bottom_level_id, int prev_bottom_level_id);

	/**
	 * 문장의 변경사항을 로그로 저장
	 * @param utteranceLog 저장할 로그
	 * @return 저장한 개수
	 */
	public int insertIntoUtteranceLog(UtteranceLog utteranceLog);
}
