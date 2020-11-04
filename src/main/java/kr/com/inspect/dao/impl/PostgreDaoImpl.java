package kr.com.inspect.dao.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Sound;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.mapper.PostgreMapper;
import kr.com.inspect.parser.JsonParsing;
import kr.com.inspect.parser.XlsxParsing;

@Repository
public class PostgreDaoImpl implements PostgreDao {
	@Autowired
	private PostgreMapper postgreMapper;
	
	@Autowired
	private SqlSession sqlSession;
	private final String ns = "MetadataMapper.";
	
	@Autowired
	private ElasticDao elasticDao;
	
	private JsonParsing jsonParsing = new JsonParsing();
	private XlsxParsing xlsxParsing = new XlsxParsing();
	
	@Override
	public List<Metadata> getTable() {
		return postgreMapper.getTable();
	}

	@Override
	public Metadata getTableUsingId(Integer id){
		return postgreMapper.getTableUsingId(id);
	}

	@Override
	public List<Utterance> getUtteranceTableUsingMetadataId(Integer metadataId) {
		return postgreMapper.getUtteranceTableUsingMetadataId(metadataId);
	}

	@Override
	public void insertElasticIndex(String index) {
		// 인덱스를 통해 엘라스틱서치에서 데이터를 받아옴
		SearchHit[] searchHits = elasticDao.getIndex(index);
		
		for(SearchHit hit: searchHits) {
			Map<String, Object> map = hit.getSourceAsMap();
			
			Sound sound = new Sound();
			sound.setId(hit.getId());
			sound.setCategory((String)map.get("category"));
			sound.setTitle((String)map.get("title"));
			sound.setCompany((String)map.get("company"));
			sound.setContent((String)map.get("content"));
			
			postgreMapper.insertValue(sound);
		}
	}
	
	@Override
	public boolean insertJSONObject(String path) {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;
		
		for(File file : fileList){
			/* 확장자가 json인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("json")){
		    	String fullPath = path + file.getName();
		    	
		    	/* json 파일을 읽어서 객체로 파싱 */
				JSONObject obj = jsonParsing.getJSONObject(fullPath);
				
				/* metadata 테이블 입력 */
				Metadata metadata  = jsonParsing.setMetadata(obj);
				
				/* metadata_id를 가져옴(creator, title) */
				Map map = new HashMap();
				map.put("creator", metadata.getCreator());
				map.put("title", metadata.getTitle());
				String isExistId = postgreMapper.isExistMetadataId(map);
				
				if(isExistId == null) { //등록된 데이터가 아닐 경우
					check = true;
					
					/* metadata 테이블 입력 */
					postgreMapper.insertIntoMetadata(metadata); 
					
					/* auto increment로 등록된 id를 가져옴 */
					int metadata_id = postgreMapper.getMetadataId(map);
					
					/* speaker 테이블 입력 */
					List<Speaker> speakerList = jsonParsing.setSpeaker(obj, metadata_id);
					for(Speaker speaker : speakerList) {
						postgreMapper.insertIntoSpeaker(speaker);
					}
					
					/* utterance 테이블 입력 */
					List<Utterance> utteranceList = jsonParsing.setUtterance(obj, metadata_id);
					for(Utterance utterance : utteranceList) {
						postgreMapper.insertIntoUtterance(utterance); //utterance 입력
						List<EojeolList> eojeolListList = utterance.getEojoelList();
						for(EojeolList eojeolList : eojeolListList) {
							postgreMapper.insertIntoEojeolList(eojeolList); //eojeolList 입력
						}
					}
				}
		    }
		}
		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return true;
		}else { //모두 중복된 데이터일 경우
			return false; 
		}
	}

	@Override
	public boolean insertXlsxTable(String path) {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;
		
		for(File file : fileList){
			/* 확장자가 xlsx인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("xlsx")){
		    	String fullPath = path + file.getName();
		    	System.out.println(fullPath);
		    	List<Program> list = xlsxParsing.setProgramList(fullPath);
		    	System.out.println(list);
		    	for(int i=0; i<list.size(); i++) {
		    		if(postgreMapper.getProgramByFileNum(list.get(i).getFile_num()) == null) {
		    			check = true;
		    			postgreMapper.insertIntoProgram(list.get(i));
		    		}
		    	}
		    }
		}
		if(check == true) { //아직 등록되지 않은 데이터가 하나라도 있을 경우
			return true;
		}else { //모두 중복된 데이터일 경우
			return false; 
		}
	}
	
	public List<Metadata> getMetadataAndProgram(){
		List<Metadata> list = sqlSession.selectList(ns+"getMetadataAndProgram");
		System.out.println(list);
		return list;
	}
}