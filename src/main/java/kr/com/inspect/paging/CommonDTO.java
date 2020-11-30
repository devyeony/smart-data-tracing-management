package kr.com.inspect.paging;

/**
 * 
 * @author Yeonhee Kim
 *
 */
public class CommonDTO {
	/**
	 * SELECT할 row의 수
	 */
	private int limit;
	
	/**
	 * 몇 번째 row부터 가져올지를 결정
	 */
	private int offset;
	
	/**
	 * 페이징 네비게이션 결과 값을 저장할 변수
	 */
	private String pagination;
	
	public CommonDTO() {}
	public CommonDTO(int limit, int offset, String pagination) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.pagination = pagination;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	
	@Override
	public String toString() {
		return "CommonDTO [limit=" + limit + ", offset=" + offset + ", pagination=" + pagination + "]";
	}
}