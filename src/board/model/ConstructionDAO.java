package board.model;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

public class ConstructionDAO {
	
	DataSource ds;
	
	public ConstructionDAO(){
		try{
			Context initContext = (Context)new InitialContext().lookup("java:comp/env/");
			ds = (DataSource)initContext.lookup("jdbc/mysql");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//공고관리 목록 갯수 구하기
	public int constructionListTotalCnt(String searchKeyword, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int result = 0;
		int cnt=0;
		
		String[] query={"CONSTRUCTION_NAME","CONSTRUCTION_WAY","CONSTRUCTION_AREA",
				"CONSTRUCTION_PRICE", "CONSTRUCTION_LOWER", "CONSTRUCTION_OPENING",
				"CONSTRUCTION_INSTITUTION", "CONSTRUCTION_PERCENT"};
		
		try {
		conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*)	cnt								\n");
		sql.append("FROM TB_CONSTRUCTION							\n");
		sql.append("WHERE DEL_YN <> 'Y' 							\n");
		
		//체크박스 체크여부 확인
		if(checked != null){
			sql.append("AND ( ");
			//쿼리가 2개 이상이라면 OR 를 붙여주기 위함
			int count=0;
			//체크박스의 길이만큼
			for(int i=0; i<checked.length; i++){
				//쿼리의 갯수만큼 체크박스의 값이 맞는지 비교하기 위함
				for(int j=0; j<query.length; j++){
					//체크박스의 값이 맞으면 쿼리 추가
					if(checked[i].equals(String.valueOf(j))){
						++count;
						//체크박스의 길이가 2개이상 이라면
						//쿼리추가전에 OR 추가
						if(2<=checked.length && 2<=count){
							sql.append(" OR ");
						}
						sql.append(""+ query[j] + " LIKE CONCAT('%',?,'%') \n");
					}
				}
			}
			//"AND (" 괄호 닫음 
			sql.append(")");
		//체크박스가 체크되어있지 않고 검색되었을때	
		}else if(searchKeyword.length() > 0){
			sql.append("AND ( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')	\n");
			sql.append("OR CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%'))		\n");
		}
			pstmt = conn.prepareStatement(sql.toString());
			//위에서 추가된 쿼리 ? 에 값을 대입
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					for(int j=0; j<query.length; j++){
						if(checked[i].equals(String.valueOf(j))){
							pstmt.setString(++cnt, searchKeyword);
						}
					}
				}
		//체크박스가 체크되어있지 않고 검색되었을때		
		}else if(searchKeyword.length() > 0){
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
			}
			//System.out.println("Con Cnt selectpstmt:   "+pstmt.toString());
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//공고관리 리스트 조회
	public ArrayList<ConstructionDTO> constructionList(String searchKeyword, int pageno, 
														int totalcnt, String[] checked){
		ArrayList<ConstructionDTO> list = new ArrayList<ConstructionDTO>();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int nCnt = 1;
		int startRow =0;
		
		if(searchKeyword.length() <= 0){
			startRow = (pageno - 1) * 10;
		}
		if(searchKeyword.length() > 0 && totalcnt>10){
			if(((pageno -1) * 10) >= totalcnt){
				startRow = 0;
			}else{
				startRow = (pageno - 1) * 10;
			}
		}
		int endRow = 10;
		
		String[] query={"CONSTRUCTION_NAME","CONSTRUCTION_WAY","CONSTRUCTION_AREA",
				"CONSTRUCTION_PRICE", "CONSTRUCTION_LOWER", "CONSTRUCTION_OPENING",
				"CONSTRUCTION_INSTITUTION", "CONSTRUCTION_PERCENT"};
		
		try {
		conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT 											\n");
		sql.append("CONSTRUCTION_NUM , CONSTRUCTION_NAME, 		 	\n");
		sql.append("CONSTRUCTION_WAY, CONSTRUCTION_AREA,			\n");
		sql.append("CONSTRUCTION_PRICE, CONSTRUCTION_LOWER,			\n");
		sql.append("CONSTRUCTION_OPENING, CONSTRUCTION_INSTITUTION,	\n");
		sql.append("CONSTRUCTION_PERCENT, 							\n");
		sql.append("date_format(CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
		sql.append("date_format(UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 											\n");
		sql.append("TB_CONSTRUCTION 								\n");
		sql.append("WHERE DEL_YN <> 'Y' 							\n");
		//체크박스 체크여부 확인
		if(checked != null){
			sql.append("AND ( ");
			//쿼리가 2개 이상이라면 OR 를 붙여주기 위함
			int count=0;
			//체크박스의 길이만큼
			for(int i=0; i<checked.length; i++){
				//쿼리의 갯수만큼 체크박스의 값이 맞는지 비교하기 위함
				for(int j=0; j<query.length; j++){
					//체크박스의 값이 맞으면 쿼리 추가
					if(checked[i].equals(String.valueOf(j))){
						++count;
						//체크박스의 길이가 2개이상 이라면
						//쿼리추가전에 OR 추가
						if(2<=checked.length && 2<=count){
							sql.append(" OR ");
						}
						sql.append(""+ query[j] + " LIKE CONCAT('%',?,'%') \n");
					}
				}
			}
			//"AND (" 괄호 닫음 
			sql.append(")");
		//체크박스가 체크되어있지 않고 검색되었을때	
		}else if(searchKeyword.length() > 0){
			sql.append("AND ( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')	\n");
			sql.append("OR CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%'))		\n");
		}
		sql.append("ORDER BY CONSTRUCTION_NUM DESC					\n");
		sql.append("LIMIT ?, ?										\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			//위에서 추가된 쿼리 ? 에 값을 대입
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					for(int j=0; j<query.length; j++){
						if(checked[i].equals(String.valueOf(j))){
							pstmt.setString(nCnt++, searchKeyword);
						}
					}
				}
		//체크박스가 체크되어있지 않고 검색되었을때
		}else if(searchKeyword.length() > 0){
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
			}
			pstmt.setInt(nCnt++, startRow);
			pstmt.setInt(nCnt++, endRow);
			//System.out.println("Con selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ConstructionDTO dto = new ConstructionDTO();
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setConstWay(rs.getString("CONSTRUCTION_WAY"));
				dto.setConstArea(rs.getString("CONSTRUCTION_AREA"));
				dto.setConstPrice(rs.getString("CONSTRUCTION_PRICE"));
				dto.setConstLower(rs.getString("CONSTRUCTION_LOWER"));
				dto.setConstOpening(rs.getString("CONSTRUCTION_OPENING"));
				dto.setConstInstitution(rs.getString("CONSTRUCTION_INSTITUTION"));
				dto.setConstPercent(rs.getString("CONSTRUCTION_PERCENT"));
				dto.setCrtDate(rs.getString("CRT_DATE"));
				dto.setUdtDate(rs.getString("UDT_DATE"));

				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//공고관리 수정 페이지 데이터 값 정의
	public ConstructionDTO constructionMod(int ConstNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		ConstructionDTO dto = new ConstructionDTO();
		
		try {
		conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT 											\n");
		sql.append("CONSTRUCTION_NUM , CONSTRUCTION_NAME,		 	\n");
		sql.append("CONSTRUCTION_WAY, CONSTRUCTION_AREA,			\n");
		sql.append("CONSTRUCTION_PRICE, CONSTRUCTION_LOWER,			\n");
		sql.append("CONSTRUCTION_OPENING, CONSTRUCTION_INSTITUTION,	\n");
		sql.append("CONSTRUCTION_PERCENT, 							\n");
		sql.append("date_format(CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
		sql.append("date_format(UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 											\n");
		sql.append("TB_CONSTRUCTION 								\n");
		sql.append("WHERE CONSTRUCTION_NUM = ?						\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ConstNum);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setConstWay(rs.getString("CONSTRUCTION_WAY"));
				dto.setConstArea(rs.getString("CONSTRUCTION_AREA"));
				dto.setConstPrice(rs.getString("CONSTRUCTION_PRICE"));
				dto.setConstLower(rs.getString("CONSTRUCTION_LOWER"));
				dto.setConstOpening(rs.getString("CONSTRUCTION_OPENING"));
				dto.setConstInstitution(rs.getString("CONSTRUCTION_INSTITUTION"));
				dto.setConstPercent(rs.getString("CONSTRUCTION_PERCENT"));
				dto.setCrtDate(rs.getString("CRT_DATE"));
				dto.setUdtDate(rs.getString("UDT_DATE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return dto;
	}
	
	//공고관리 수정
	public int constructionModOk(int ConstNum, String constWay, 
									String constArea, String constPrice, 
										String constLower, String constOpening, 
											String constInstitution, String constPercent){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int result = 0;
		try {
			conn = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE TB_CONSTRUCTION						  	\n");
			sql.append("SET CONSTRUCTION_WAY = ?, CONSTRUCTION_AREA = ?,\n");
			sql.append("CONSTRUCTION_PRICE = ?, CONSTRUCTION_LOWER = ?,	\n");
			sql.append("CONSTRUCTION_OPENING = ?, 						\n");
			sql.append("CONSTRUCTION_INSTITUTION = ?,					\n");
			sql.append("CONSTRUCTION_PERCENT = ?,	UDT_DATE = now()	\n");
			sql.append("WHERE CONSTRUCTION_NUM = ?						\n");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, constWay);
			pstmt.setString(2, constArea);
			pstmt.setString(3, constPrice);
			pstmt.setString(4, constLower);
			pstmt.setString(5, constOpening);
			pstmt.setString(6, constInstitution);
			pstmt.setString(7, constPercent);
			pstmt.setInt(8, ConstNum);
			System.out.println("Con Update:   "+pstmt.toString());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	//공고관리 삭제여부 'Y' 로 변경
		public int ConstructionDelOk(int constNum){
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			Connection conn = null;
			
			int result = 0;

			try {
				conn = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE TB_CONSTRUCTION						\n");
			sql.append("SET											\n");
			sql.append("DEL_YN = 'Y' 								\n");
			sql.append("WHERE TB_CONSTRUCTION.CONSTRUCTION_NUM = ?	\n");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, constNum);
				
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try{
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			return result;
		}
	
	//*
	public ArrayList<ConstructionDTO> selectConstructionList(){
		ArrayList<ConstructionDTO> list = new ArrayList<ConstructionDTO>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT											\n");
		sql.append("CONSTRUCTION_NAME, CONSTRUCTION_NUM				\n");
		sql.append("FROM											\n");
		sql.append("TB_CONSTRUCTION									\n");
		sql.append("WHERE DEL_YN <> 'Y'								\n");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				ConstructionDTO dto = new ConstructionDTO();
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	
	//공고등록
	public int constructionAddOk(String constName, String constWay, String constArea,
		 							String constPrice, String constLower, String constOpening,
			 							String constInstitution, String constPercent){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;
		
		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO TB_CONSTRUCTION(CONSTRUCTION_NAME,	\n");
		sql.append("CONSTRUCTION_WAY, CONSTRUCTION_AREA,			\n");
		sql.append("CONSTRUCTION_PRICE, CONSTRUCTION_LOWER,			\n");
		sql.append("CONSTRUCTION_OPENING, CONSTRUCTION_INSTITUTION,	\n");
		sql.append("CONSTRUCTION_PERCENT, CRT_DATE)					\n");
		sql.append("SELECT ?, ?, ?, ?, ?, ?, ?, ?, now()			 	\n");
		sql.append("FROM DUAL										\n");
		sql.append("WHERE 									 		\n");
		sql.append("NOT EXISTS(SELECT CONSTRUCTION_NAME				\n");
		sql.append("FROM											\n");
		sql.append("TB_CONSTRUCTION WHERE CONSTRUCTION_NAME=?)		\n");
		pstmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, constName);
			pstmt.setString(2, constWay);
			pstmt.setString(3, constArea);
			pstmt.setString(4, constPrice);
			pstmt.setString(5, constLower);
			pstmt.setString(6, constOpening);
			pstmt.setString(7, constInstitution);
			pstmt.setString(8, constPercent);
			pstmt.setString(9, constName);
			
			System.out.println("pstmt:"+pstmt);
			result = pstmt.executeUpdate();
			System.out.println("pstmt result:"+result);
			if (result > 0) {
				result = -1;
				try {
					rs = pstmt.getGeneratedKeys();
					if (rs.next()){
						System.out.println("rs:"+rs);
					System.out.println("rs result:"+result);
						result = rs.getInt(1);
						System.out.println("getint result:"+result);
					}
				} catch (SQLException e) {
					result = -1;
				}
			}
			System.out.println("최종 result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//업체등록 화면 셀렉트 박스에 표시될 데이터 조회
	public ArrayList<ConstructionDTO> businessAdd(){
		ArrayList<ConstructionDTO> list = new ArrayList<ConstructionDTO>();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT 											\n");
		sql.append("CONSTRUCTION_NUM , CONSTRUCTION_NAME		 	\n");
		sql.append("FROM 											\n");
		sql.append("TB_CONSTRUCTION 								\n");
		sql.append("WHERE DEL_YN <> 'Y'								\n");
		
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ConstructionDTO dto = new ConstructionDTO();
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return list;
	}
	
	//		** 아직 구현 안됨
	//공고관리 삭제 리스트 갯수 구하기
	public int constructionDelListTotalCnt(String searchKeyword, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;
		int cnt=0;

		try {
			conn = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT COUNT(*)	cnt							\n");
			sql.append("FROM TB_CONSTRUCTION 						\n");
			sql.append("WHERE DEL_YN = 'Y' 							\n");
			
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					if(checked[i].equals("1")){
						sql.append("AND CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("2")){
						sql.append("AND CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("3")){
						sql.append("AND CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("4")){
						sql.append("AND CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("5")){
						sql.append("AND CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("6")){
						sql.append("AND CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("7")){
						sql.append("AND CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("8")){
						sql.append("AND CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%')			\n");
					}
				}	
			}else if(searchKeyword.length() > 0){
				sql.append("AND	( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
				sql.append("OR CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
				sql.append("OR CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%'))		\n");
			}
			pstmt = conn.prepareStatement(sql.toString());
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					if(checked[i].equals("1")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("2")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("3")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("4")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("5")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("6")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("7")){
						pstmt.setString(++cnt, searchKeyword);
					}
					if(checked[i].equals("8")){
						pstmt.setString(++cnt, searchKeyword);
					}
				}	
			}else if(searchKeyword.length() > 0){
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
			}
			//System.out.println("Con Cnt selectpstmt:   "+pstmt.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
			//System.out.println("con result:  "+result);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}

	//*
	public ArrayList<ConstructionDTO> selectConstructionDelList(String searchKeyword, int pageno,
																int totalcnt, String[] checked){
		ArrayList<ConstructionDTO> list = new ArrayList<ConstructionDTO>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int nCnt = 1;
		int startRow =0;

		if(searchKeyword.length() <= 0){
			startRow = (pageno - 1) * 10;
		}
		if(searchKeyword.length() > 0 && totalcnt>10){
			if(((pageno -1) * 10) >= totalcnt){
				startRow = 0;
			}else{
				startRow = (pageno - 1) * 10;
			}
		}
		int endRow = 10;
		/*
		if(checked != null){
			for(int i=0; i<checked.length;i++){
				System.out.println("checked[]:    "+checked[i]);
			}
		}*/

		try {
			conn = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT					 						\n");
			sql.append("CONSTRUCTION_NUM , CONSTRUCTION_NAME,    	 	\n");
			sql.append("CONSTRUCTION_WAY, CONSTRUCTION_AREA,			\n");
			sql.append("CONSTRUCTION_PRICE, CONSTRUCTION_LOWER,			\n");
			sql.append("CONSTRUCTION_OPENING, CONSTRUCTION_INSTITUTION, \n");
			sql.append("CONSTRUCTION_PERCENT,							\n");
			sql.append("date_format(CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
			sql.append("date_format(UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
			sql.append("FROM 											\n");
			sql.append("TB_CONSTRUCTION 								\n");
			sql.append("WHERE DEL_YN = 'Y' 								\n");
			
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					if(checked[i].equals("1")){
						sql.append("AND CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("2")){
						sql.append("AND CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("3")){
						sql.append("AND CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("4")){
						sql.append("AND CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("5")){
						sql.append("AND CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("6")){
						sql.append("AND CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("7")){
						sql.append("AND CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')			\n");
					}
					if(checked[i].equals("8")){
						sql.append("AND CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%')			\n");
					}
				}	
			}else if(searchKeyword.length() > 0){
				sql.append("AND ( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
				sql.append("OR CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
				sql.append("OR CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%'))		\n");
			}
			sql.append("ORDER BY CONSTRUCTION_NUM DESC				\n");
			sql.append("LIMIT ?, ?									\n");

			pstmt = conn.prepareStatement(sql.toString());
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					if(checked[i].equals("1")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("2")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("3")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("4")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("5")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("6")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("7")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("8")){
						pstmt.setString(nCnt++, searchKeyword);
					}
				}	
			}else if(searchKeyword.length() > 0){

				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
			}
			pstmt.setInt(nCnt++, startRow);
			pstmt.setInt(nCnt++, endRow);
			//System.out.println("Con selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ConstructionDTO dto = new ConstructionDTO();
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setConstWay(rs.getString("CONSTRUCTION_WAY"));
				dto.setConstArea(rs.getString("CONSTRUCTION_AREA"));
				dto.setConstPrice(rs.getString("CONSTRUCTION_PRICE"));
				dto.setConstLower(rs.getString("CONSTRUCTION_LOWER"));
				dto.setConstOpening(rs.getString("CONSTRUCTION_OPENING"));
				dto.setConstInstitution(rs.getString("CONSTRUCTION_INSTITUTION"));
				dto.setConstPercent(rs.getString("CONSTRUCTION_PERCENT"));
				dto.setCrtDate(rs.getString("CRT_DATE"));
				dto.setUdtDate(rs.getString("UDT_DATE"));

				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//*
	public int totalInsert(String sql){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		//StringBuffer sql = new StringBuffer();
		int result = 0;

		System.out.println("sql:   "+sql);
		
		try {
			pstmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			
			result = pstmt.executeUpdate();
			
			if (result > 0) {
				result = -1;
				try {
					rs = pstmt.getGeneratedKeys();
					if (rs.next())
						result = rs.getInt(1);
				} catch (SQLException e) {
					result = -1;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}

	
	//*
	public int deleteConstructionView(int ConstNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int result = 0;

		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TB_CONSTRUCTION JOIN TB_BUSINESS							\n");
		sql.append("ON 																\n");
		sql.append("TB_CONSTRUCTION.CONSTRUCTION_NUM = TB_BUSINESS.CONSTRUCTION_NUM	\n");
		sql.append("SET TB_BUSINESS.DEL_YN = 'Y'									\n");
		sql.append("WHERE TB_BUSINESS.CONSTRUCTION_NUM = ?							\n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ConstNum);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//*
	public int deleteConstruction2(int ConstNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int result = 0;

		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		/*	�몢媛� �씠�긽 �궘�젣 �븷 �븣
		sql.append("DELETE TB_CONSTRUCTION, TB_BUSINESS FROM													\n");
		sql.append("TB_CONSTRUCTION	JOIN TB_BUSINESS								\n");
		sql.append("ON TB_CONSTRUCTION.DEL_YN = TB_BUSINESS.DEL_YN 												\n");
		sql.append("WHERE TB_CONSTRUCTION.DEL_YN='Y' AND (TB_CONSTRUCTION.CONSTRUCTION_NUM = ?) OR (TB_CONSTRUCTION.CONSTRUCTION_NUM = ? AND TB_BUSINESS.CONSTRUCTION_NUM = ?)					\n");
		*/
		
		sql.append("DELETE FROM										\n");
		sql.append("TB_CONSTRUCTION									\n");
		sql.append("WHERE DEL_YN='Y' AND CONSTRUCTION_NUM = ?		\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ConstNum);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//*			 휴지통 복구
	public int restoreConstruction(int ConstNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int result = 0;

		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TB_CONSTRUCTION							\n");
		sql.append("SET												\n");
		sql.append("DEL_YN = 'N' 									\n");
		sql.append("WHERE TB_CONSTRUCTION.CONSTRUCTION_NUM = ?		\n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ConstNum);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//공고관리 조회 엑셀 출력
	public ArrayList<ConstructionDTO> selectConstructionListExcel(String searchKeyword, int pageno, int totalcnt, String[] checked){
		ArrayList<ConstructionDTO> list = new ArrayList<ConstructionDTO>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int nCnt = 1;
		int startRow =0;

		if(searchKeyword.length() <= 0){
			startRow = (pageno - 1) * 10;
		}
		if(searchKeyword.length() > 0 && totalcnt>10){
			if(((pageno -1) * 10) >= totalcnt){
				startRow = 0;
			}else{
				startRow = (pageno - 1) * 10;
			}
		}
		int endRow = 10;
		/*
		if(checked != null){
			for(int i=0; i<checked.length;i++){
			System.out.println("checked[]:    "+checked[i]);
			}
		}
		*/

		try {
			conn = ds.getConnection();
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT 											\n");
		sql.append("CONSTRUCTION_NUM , CONSTRUCTION_NAME, 		 	\n");
		sql.append("CONSTRUCTION_WAY, CONSTRUCTION_AREA,			\n");
		sql.append("CONSTRUCTION_PRICE, CONSTRUCTION_LOWER,			\n");
		sql.append("CONSTRUCTION_OPENING, CONSTRUCTION_INSTITUTION,	\n");
		sql.append("CONSTRUCTION_PERCENT,							\n");
		sql.append("date_format(CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
		sql.append("date_format(UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 											\n");
		sql.append("TB_CONSTRUCTION									\n");
		sql.append("WHERE DEL_YN <> 'Y'								\n");
		
		if(checked != null){
			for(int i=0; i<checked.length; i++){
				if(checked[i].equals("1")){
					sql.append("AND CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("2")){
					sql.append("AND CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("3")){
					sql.append("AND CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("4")){
					sql.append("AND CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("5")){
					sql.append("AND CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("6")){
					sql.append("AND CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("7")){
					sql.append("AND CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("8")){
					sql.append("AND CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%')			\n");
				}
			}	
		}else if(searchKeyword.length() > 0){
			sql.append("AND ( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_WAY LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR CONSTRUCTION_AREA LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR CONSTRUCTION_PRICE LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_LOWER LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_OPENING LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_INSTITUTION LIKE CONCAT('%',?,'%')	\n");
			sql.append("OR CONSTRUCTION_PERCENT LIKE CONCAT('%',?,'%'))		\n");
		}
		sql.append("ORDER BY CONSTRUCTION_NUM DESC					\n");
		sql.append("LIMIT ?, ?										\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					if(checked[i].equals("1")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("2")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("3")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("4")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("5")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("6")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("7")){
						pstmt.setString(nCnt++, searchKeyword);
					}
					if(checked[i].equals("8")){
						pstmt.setString(nCnt++, searchKeyword);
					}
				}	
		}else if(searchKeyword.length() > 0){
				
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
				pstmt.setString(nCnt++, searchKeyword);
			}
			pstmt.setInt(nCnt++, startRow);
			pstmt.setInt(nCnt++, endRow);
			//System.out.println("Con selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ConstructionDTO dto = new ConstructionDTO();
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setConstWay(rs.getString("CONSTRUCTION_WAY"));
				dto.setConstArea(rs.getString("CONSTRUCTION_AREA"));
				dto.setConstPrice(rs.getString("CONSTRUCTION_PRICE"));
				dto.setConstLower(rs.getString("CONSTRUCTION_LOWER"));
				dto.setConstOpening(rs.getString("CONSTRUCTION_OPENING"));
				dto.setConstInstitution(rs.getString("CONSTRUCTION_INSTITUTION"));
				dto.setConstPercent(rs.getString("CONSTRUCTION_PERCENT"));
				dto.setCrtDate(rs.getString("CRT_DATE"));
				dto.setUdtDate(rs.getString("UDT_DATE"));

				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}

	
}