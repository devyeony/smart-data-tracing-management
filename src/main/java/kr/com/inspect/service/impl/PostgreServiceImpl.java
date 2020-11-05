package kr.com.inspect.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Sound;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.mapper.PostgreInsertMapper;
import kr.com.inspect.mapper.PostgreSelectMapper;
import kr.com.inspect.parser.JsonParsing;
import kr.com.inspect.parser.XlsxParsing;
import kr.com.inspect.service.PostgreService;

@Service
public class PostgreServiceImpl implements PostgreService {
	@Autowired
	private ElasticDao elasticDao;
	
	@Autowired
	private PostgreDao postgreDao;
	
	@Autowired
	private PostgreInsertMapper postgreInsertMapper;
	
	@Autowired
	private PostgreSelectMapper postgreSelectMapper;
	
	private JsonParsing jsonParsing = new JsonParsing();
	private XlsxParsing xlsxParsing = new XlsxParsing();
	
	/* 엘라스틱 서치에서 받아온 인덱스를 PostgreSQL에 넣음(테스트) */
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
			
			postgreInsertMapper.insertTestValue(sound);
		}
	}
	
	/* Metadata 테이블을 모두 가지고 옴 */
	public List<Metadata> getMetadata(){
		return postgreDao.getMetadata();
	}
	
	/* id로 해당되는 Metadata 테이블을 가져옴 */
	public Metadata getMetadataById(Integer id) {
		return postgreDao.getMetadataById(id);
	}
	
	/* metadataId로 해당되는 Utterance 테이블을 가져옴 */
	public List<Utterance> getUtteranceByMetadataId(Integer metadataId){
		return postgreDao.getUtteranceByMetadataId(metadataId);
	}
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 PostgreSQL에 넣음 */
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
				String isExistId = postgreSelectMapper.isExistMetadataId(map);
				
				if(isExistId == null) { //등록된 데이터가 아닐 경우
					check = true;
					
					/* metadata 테이블 입력 */
					postgreInsertMapper.insertIntoMetadata(metadata); 
					
					/* auto increment로 등록된 id를 가져옴 */
					int metadata_id = postgreSelectMapper.getMetadataId(map);
					
					/* speaker 테이블 입력 */
					List<Speaker> speakerList = jsonParsing.setSpeaker(obj, metadata_id);
					for(Speaker speaker : speakerList) {
						postgreInsertMapper.insertIntoSpeaker(speaker);
					}
					
					/* utterance 테이블 입력 */
					List<Utterance> utteranceList = jsonParsing.setUtterance(obj, metadata_id);
					for(Utterance utterance : utteranceList) {
						postgreInsertMapper.insertIntoUtterance(utterance); //utterance 입력
						List<EojeolList> eojeolListList = utterance.getEojoelList();
						for(EojeolList eojeolList : eojeolListList) {
							postgreInsertMapper.insertIntoEojeolList(eojeolList); //eojeolList 입력
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

	/* 특정 경로에 있는 xlsx 파일들을 읽어서 PostgreSQL에 넣음 */
	@Override
	public boolean insertXlsxTable(String path) {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;
		
		for(File file : fileList){
			/* 확장자가 xlsx인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("xlsx")){
		    	String fullPath = path + file.getName();
		    	List<Program> list = xlsxParsing.setProgramList(fullPath);
		    	for(int i=0; i<list.size(); i++) {
		    		if(postgreSelectMapper.getProgramByFileNum(list.get(i).getFile_num()) == null) {
		    			check = true;
		    			postgreInsertMapper.insertIntoProgram(list.get(i));
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
	
	/* Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public List<Metadata> getMetadataAndProgram(){
		return postgreDao.getMetadataAndProgram();
	}

	/* metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId){
		return postgreDao.getMetadataAndProgramUsingId(metaId);
	}
}