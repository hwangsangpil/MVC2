package board.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

public class BusinessDAO {
	
	DataSource ds;
	
	public BusinessDAO(){
		try{
			Context initContext = (Context)new InitialContext().lookup("java:comp/env/");
			ds = (DataSource)initContext.lookup("jdbc/mysql");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//업체관리 갯수 구하기
	public int businessListTotalCnt(String searchKeyword, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;
		int cnt = 0;
		
		String[] query = {"BUSINESS_NAME", "CONSTRUCTION_NAME", 
							"BUSINESS_OPENING", "BUSINESS_PERCENT",
								"BUSINESS_PRICE", "BUSINESS_WAY",
									"BUSINESS_AREA"	};

		try {
			conn = ds.getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*)	cnt							\n");
			sql.append("FROM TB_CONSTRUCTION CONSTRUCTION			\n");
			sql.append("JOIN										\n");
			sql.append("TB_BUSINESS	BUSINESS						\n");
			sql.append("WHERE BUSINESS.DEL_YN <> 'Y'				\n");
			sql.append("AND CONSTRUCTION.CONSTRUCTION_NUM = 		\n");
			sql.append("BUSINESS.CONSTRUCTION_NUM					\n");
		
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
			sql.append(" ) ");
		//체크박스가 체크되어있지 않고 검색되었을때	
		}else if(searchKeyword.length() > 0){
			sql.append("AND ( BUSINESS_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%') )		\n");
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
			}
			//System.out.println("Busi Cnt selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
			//System.out.println("Busi result:  "+result);
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
	
	//업체관리 리스트 구하기
	public ArrayList<BusinessDTO> businessList(String searchKeyword, int pageno, int totalcnt, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		ArrayList<BusinessDTO> list = new ArrayList<BusinessDTO>();
		
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

		String[] query = {"BUSINESS_NAME", "CONSTRUCTION_NAME", 
							"BUSINESS_OPENING", "BUSINESS_PERCENT", 
								"BUSINESS_PRICE", "BUSINESS_WAY",
									"BUSINESS_AREA"	};
		try {
			conn = ds.getConnection();
		
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT 													\n");
			sql.append("BUSINESS_NUM, BUSINESS_NAME, CONSTRUCTION_NAME,			\n");
			sql.append("BUSINESS_OPENING, BUSINESS_PERCENT, BUSINESS_PRICE,		\n");
			sql.append("BUSINESS_WAY, BUSINESS_AREA,							\n");
			sql.append("date_format(BUSINESS.CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
			sql.append("date_format(BUSINESS.UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
			sql.append("FROM TB_CONSTRUCTION CONSTRUCTION						\n");
			sql.append("JOIN													\n");
			sql.append("TB_BUSINESS	BUSINESS									\n");
			sql.append("WHERE BUSINESS.DEL_YN <> 'Y'							\n");
			sql.append("AND CONSTRUCTION.CONSTRUCTION_NUM = 					\n");
			sql.append("BUSINESS.CONSTRUCTION_NUM								\n");
			
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
							sql.append("" + query[j] + " LIKE CONCAT('%',?,'%') \n");
						}
					}
				}
				//"AND (" 괄호 닫음 
				sql.append(" ) ");
			//체크박스가 체크되어있지 않고 검색되었을때
			}else if(searchKeyword.length() > 0){
				sql.append("AND ( BUSINESS_NAME LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%') )	\n");
			}
			sql.append("ORDER BY BUSINESS_NUM DESC						\n");
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
			}
			pstmt.setInt(nCnt++, startRow);
			pstmt.setInt(nCnt++, endRow);
			//System.out.println("Busi selectpstmt:\n"+pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BusinessDTO dto = new BusinessDTO();
				dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
				dto.setBusiName(rs.getString("BUSINESS_NAME"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
				dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
				dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
				dto.setBusiWay(rs.getString("BUSINESS_WAY"));
				dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
	//업체관리 수정 페이지 이동
		public BusinessDTO businessMod(int busiNum){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			Connection conn = null;

			BusinessDTO dto = new BusinessDTO();

			try {
			conn = ds.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT							 						\n");
			sql.append("BUSINESS_NUM, BUSINESS_NAME,					 		\n");
			sql.append("CONSTRUCTION_NAME, BUSINESS_OPENING,    				\n");
			sql.append("BUSINESS_PERCENT, BUSINESS_PRICE,  						\n");
			sql.append("BUSINESS_WAY, BUSINESS_AREA,							\n");
			sql.append("date_format(BUSINESS.CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
			sql.append("date_format(BUSINESS.UDT_DATE, '%Y.%m.%d') as UDT_DATE			\n");
			sql.append("FROM 													\n");
			sql.append("TB_CONSTRUCTION CONSTRUCTION JOIN TB_BUSINESS BUSINESS	\n");
			sql.append("WHERE BUSINESS.DEL_YN <> 'Y'  							\n");
			sql.append("AND CONSTRUCTION.CONSTRUCTION_NUM = 					\n");
			sql.append("BUSINESS.CONSTRUCTION_NUM 								\n");
			sql.append("AND BUSINESS_NUM=?										\n");
			
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, busiNum);
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
					dto.setBusiName(rs.getString("BUSINESS_NAME"));
					dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
					dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
					dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
					dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
					dto.setBusiWay(rs.getString("BUSINESS_WAY"));
					dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
		//업체관리 수정완료
		public int businessModOk(int busiNum, String busiOpening, String busiPercent, String busiPrice, String busiWay, String busiArea){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			Connection conn = null;

			int result = 0;

			try {
			conn = ds.getConnection();
			System.out.println("dao busiNum:"+busiNum);
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE TB_BUSINESS								\n");
			sql.append("SET BUSINESS_OPENING = ?, BUSINESS_PERCENT = ?,	\n");
			sql.append("BUSINESS_PRICE = ?, BUSINESS_WAY = ?, 		  	\n");
			sql.append("BUSINESS_AREA = ?, UDT_DATE = now()		  		\n");
			sql.append("WHERE BUSINESS_NUM = ?							\n");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, busiOpening);
				pstmt.setString(2, busiPercent);
				pstmt.setString(3, busiPrice);
				pstmt.setString(4, busiWay);
				pstmt.setString(5, busiArea);
				pstmt.setInt(6, busiNum);
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
		
		//업체관리 삭제여부 변경
		public int businessDelOk(int busiNum){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			Connection conn = null;

			int result = 0;

			try {
			conn = ds.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE TB_BUSINESS							\n");
			sql.append("SET 										\n");
			sql.append("DEL_YN = 'Y' 								\n");
			sql.append("WHERE BUSINESS_NUM = ?						\n");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, busiNum);

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
	
		
	//공고 상세보기 갯수 구하기
	public int businessViewTotalCnt(String searchKeyword, int constNum, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;
		int cnt = 0;
		
		String[] query = {"CONSTRUCTION_NAME", "BUSINESS_NAME",
							"BUSINESS_OPENING", "BUSINESS_PERCENT",
								"BUSINESS_PRICE", "BUSINESS_WAY",
									"BUSINESS_AREA"	};
		
		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*)	cnt								\n");
		sql.append("FROM											\n");
		sql.append("TB_CONSTRUCTION CONSTRUCTION JOIN 				\n");
		sql.append("TB_BUSINESS BUSINESS							\n");
		sql.append("WHERE											\n");
		sql.append("CONSTRUCTION.CONSTRUCTION_NUM = 				\n");
		sql.append("BUSINESS.CONSTRUCTION_NUM						\n");
		sql.append("AND BUSINESS.DEL_YN <> 'Y'						\n");
		sql.append("AND CONSTRUCTION.DEL_YN <> 'Y'					\n");
		sql.append("AND BUSINESS.CONSTRUCTION_NUM=?					\n");
		
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
						sql.append("" + query[j] + " LIKE CONCAT('%',?,'%') \n");
					}
				}
			}
			//"AND (" 괄호 닫음 
			sql.append(" ) ");
		//체크박스가 체크되어있지 않고 검색되었을때	
		}else if(searchKeyword.length() > 0){
			sql.append("AND ( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')	\n");
			sql.append("OR BUSINESS_NAME LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
			sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%') )		\n");
		}
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(++cnt, constNum);
			
			if(checked != null){
				for(int i=0; i<checked.length; i++){
					for(int j=0; j<query.length; j++){
						if(checked[i].equals(String.valueOf(j))){
							pstmt.setString(++cnt, searchKeyword);
						}
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
			}
			System.out.println("View Cnt selectpstmt:   "+pstmt.toString());
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
			//System.out.println("view result:  "+result);
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
	
	//공고상세보기
	public ArrayList<BusinessDTO> businessView(int constNum, int pageno, String searchKeyword, int totalcnt, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		ArrayList<BusinessDTO> list = new ArrayList<BusinessDTO>();
		
		int nCnt = 1;
		int startRow =0;
		
		String[] query = {"CONSTRUCTION_NAME", "BUSINESS_NAME",
							"BUSINESS_OPENING", "BUSINESS_PERCENT",
								"BUSINESS_PRICE", "BUSINESS_WAY",
									"BUSINESS_AREA"	};
		
		/*
		if(Math.ceil(totalcnt/10)<=pageno && (pageno-1)!=0)
		{
			--pageno;
		}
		*/
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
			sql.append("SELECT					 								\n");
			sql.append("BUSINESS_NUM, CONSTRUCTION_NAME, 						\n");
			sql.append("BUSINESS_NAME, BUSINESS_OPENING,    					\n");
			sql.append("BUSINESS_PRICE, BUSINESS_PERCENT, 						\n");
			sql.append("BUSINESS_WAY, BUSINESS_AREA,							\n");
			sql.append("date_format(BUSINESS.CRT_DATE, '%Y.%m.%d') as CRT_DATE,	\n");
			sql.append("date_format(BUSINESS.UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
			sql.append("FROM													\n");
			sql.append("TB_CONSTRUCTION CONSTRUCTION JOIN TB_BUSINESS BUSINESS	\n");
			sql.append("WHERE    												\n");
			sql.append("CONSTRUCTION.CONSTRUCTION_NUM =							\n");
			sql.append("BUSINESS.CONSTRUCTION_NUM								\n");
			sql.append("AND BUSINESS.DEL_YN <> 'Y'								\n");
			sql.append("AND CONSTRUCTION.DEL_YN <> 'Y'							\n");
			sql.append("AND BUSINESS.CONSTRUCTION_NUM=?						\n");
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
							sql.append("" + query[j] + " LIKE CONCAT('%',?,'%') \n");
						}
					}
				}
				//"AND (" 괄호 닫음 
				sql.append(" ) ");
			//체크박스가 체크되어있지 않고 검색되었을때	
			}else if(searchKeyword.length() > 0){
				sql.append("AND ( CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')	\n");
				sql.append("OR BUSINESS_NAME LIKE CONCAT('%',?,'%')			\n");
				sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')		\n");
				sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
				sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%') )		\n");
			}
			sql.append("ORDER BY BUSINESS.BUSINESS_NUM DESC				\n");
			sql.append("LIMIT ?, ?											\n");
			
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(nCnt++, constNum);
				
				if(checked != null){
					for(int i=0; i<checked.length; i++){
						for(int j=0; j<query.length; j++){
							if(checked[i].equals(String.valueOf(j))){
								pstmt.setString(nCnt++, searchKeyword);
							}
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
				}
				pstmt.setInt(nCnt++, startRow);
				pstmt.setInt(nCnt++, endRow);
				
				System.out.println("View selectpstmt:   "+pstmt.toString());
				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					BusinessDTO dto = new BusinessDTO();
					dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
					dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
					dto.setBusiName(rs.getString("BUSINESS_NAME"));
					dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
					dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
					dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
					dto.setBusiWay(rs.getString("BUSINESS_WAY"));
					dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
	/*
	 * 
	 */
	
	public int cntTotalDelBusiness(String searchKeyword, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;
		int cnt = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*)	cnt														\n");
		sql.append("FROM																	\n");
		sql.append("TB_BUSINESS									\n");
		sql.append("WHERE																	\n");
		sql.append("DEL_YN = 'Y' 										\n");
		
		if(checked != null){
			for(int i=0; i<checked.length; i++){
				if(checked[i].equals("1")){
					sql.append("AND CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("2")){
					sql.append("AND BUSINESS_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("3")){
					sql.append("AND BUSINESS_OPENING LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("4")){
					sql.append("AND BUSINESS_PRICE LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("5")){
					sql.append("AND BUSINESS_PERCENT LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("6")){
					sql.append("AND BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("7")){
					sql.append("AND BUSINESS_AREA LIKE CONCAT('%',?,'%')			\n");
				}
			}	
		}else if(searchKeyword.length() > 0){
			sql.append("AND (CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_NAME LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%'))				\n");
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
				}	
		}else if(searchKeyword.length() > 0){
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
				pstmt.setString(++cnt, searchKeyword);
			}
			//System.out.println("Busi Cnt selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
			//System.out.println("Busi result:  "+result);
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
	
	
	public int constDelCntTotal(int ConstNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;
		int cnt = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*)	cnt														\n");
		sql.append("FROM																	\n");
		sql.append("TB_CONSTRUCTION JOIN TB_BUSINESS										\n");
		sql.append("WHERE																	\n");
		sql.append("TB_CONSTRUCTION.CONSTRUCTION_NUM = TB_BUSINESS.CONSTRUCTION_NUM			\n");
		sql.append("AND TB_BUSINESS.DEL_YN <> 'Y' 											\n");
		sql.append("AND TB_BUSINESS.CONSTRUCTION_NUM=?										\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(++cnt, ConstNum);
			
			//System.out.println("View Cnt selectpstmt:   "+pstmt.toString());
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
			//System.out.println("view result:  "+result);
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
	
	
	
	
	public ArrayList<BusinessDTO> selectBusinessDelList(String searchKeyword, int pageno, int totalcnt, String[] checked){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		ArrayList<BusinessDTO> list = new ArrayList<BusinessDTO>();
		
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
		
		/*if(checked != null){
			for(int i=0; i<checked.length;i++){
			System.out.println("checked[]:    "+checked[i]);
			}
		}*/

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT 					\n");
		sql.append("BUSINESS_NUM, CONSTRUCTION_NUM , CONSTRUCTION_NAME, BUSINESS_NAME,			\n");
		sql.append("BUSINESS_OPENING, BUSINESS_PRICE, BUSINESS_PERCENT, BUSINESS_WAY, BUSINESS_AREA, date_format(CRT_DATE, '%Y.%m.%d') as CRT_DATE, date_format(UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 														\n");
		sql.append("TB_BUSINESS										\n");
		sql.append("WHERE DEL_YN='Y'						\n");
		
		if(checked != null){
			for(int i=0; i<checked.length; i++){
				if(checked[i].equals("1")){
					sql.append("AND CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("2")){
					sql.append("AND BUSINESS_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("3")){
					sql.append("AND BUSINESS_OPENING LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("4")){
					sql.append("AND BUSINESS_PRICE LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("5")){
					sql.append("AND BUSINESS_PERCENT LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("6")){
					sql.append("AND BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("7")){
					sql.append("AND BUSINESS_AREA LIKE CONCAT('%',?,'%')			\n");
				}
			}	
		}else if(searchKeyword.length() > 0){
			sql.append("AND (CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_NAME LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%'))				\n");
		}
		sql.append("ORDER BY BUSINESS_NUM DESC												\n");
		sql.append("LIMIT ?, ?															\n");
		
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
				}	
		}else if(searchKeyword.length() > 0){
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
			//System.out.println("Busi selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BusinessDTO dto = new BusinessDTO();
				dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setBusiName(rs.getString("BUSINESS_NAME"));
				dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
				dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
				dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
				dto.setBusiWay(rs.getString("BUSINESS_WAY"));
				dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
	
	public int deleteBusinessView(int no){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		int result = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TB_BUSINESS														\n");
		sql.append("SET 														\n");
		sql.append("	DEL_YN = 'Y' 												\n");
		sql.append("WHERE BUSINESS_NUM = ?													\n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

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
	
	public int businessAddOk(int constNum, String busiName,
									String busiOpening,String busiPrice,  
										String busiPercent, String busiWay, 
											String busiArea){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO TB_BUSINESS(CONSTRUCTION_NUM, 			\n");
		sql.append("BUSINESS_NAME, BUSINESS_OPENING, BUSINESS_PRICE,	\n");
		sql.append("BUSINESS_PERCENT, BUSINESS_WAY, BUSINESS_AREA,		\n");
		sql.append("CRT_DATE)											\n");
		sql.append("SELECT ?, ?, ?, ?, ?, ?, ?, now() FROM DUAL			\n");
		sql.append("WHERE NOT EXISTS(SELECT CONSTRUCTION_NUM,			\n");
		sql.append("BUSINESS_NAME										\n");
		sql.append("FROM TB_BUSINESS									\n");
		sql.append("WHERE CONSTRUCTION_NUM=? AND BUSINESS_NAME=?)		\n");
		
			pstmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, constNum);
			pstmt.setString(2, busiName);
			pstmt.setString(3, busiOpening);
			pstmt.setString(4, busiPrice);
			pstmt.setString(5, busiPercent);
			pstmt.setString(6, busiWay);
			pstmt.setString(7, busiArea);
			pstmt.setInt(8, constNum);
			pstmt.setString(9, busiName);
			
			result = pstmt.executeUpdate();
			if (result > 0) {
				result = -1;
				try {
					rs = pstmt.getGeneratedKeys();
					if (rs.next()){
						result = rs.getInt(1);
					}
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
	

	
	
	public int deleteBusiness2(int BusiNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM														\n");
		sql.append("TB_BUSINESS 														\n");
		sql.append("WHERE 												\n");
		sql.append("BUSINESS_NUM = ?													\n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, BusiNum);

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
	
	public int restoreBusiness(int BusiNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TB_BUSINESS														\n");
		sql.append("SET 														\n");
		sql.append("	DEL_YN = 'N' 												\n");
		sql.append("WHERE BUSINESS_NUM = ?													\n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, BusiNum);

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
	
	public ArrayList<BusinessDTO> selectBusinessListExcel(String searchKeyword, int pageno, int totalcnt, String[] checked){
		ArrayList<BusinessDTO> list = new ArrayList<BusinessDTO>();
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
		
		/*if(checked != null){
			for(int i=0; i<checked.length;i++){
			System.out.println("checked[]:    "+checked[i]);
			}
		}*/

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT 					\n");
		sql.append("BUSINESS_NUM,CONSTRUCTION_NUM , CONSTRUCTION_NAME, BUSINESS_NAME,			\n");
		sql.append("BUSINESS_OPENING, BUSINESS_PRICE, BUSINESS_PERCENT, BUSINESS_WAY, BUSINESS_AREA, date_format(CRT_DATE, '%Y.%m.%d') as CRT_DATE, date_format(UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 														\n");
		sql.append("TB_BUSINESS														\n");
		sql.append("WHERE DEL_YN <> 'Y' 														\n");
		
		if(checked != null){
			for(int i=0; i<checked.length; i++){
				if(checked[i].equals("1")){
					sql.append("AND CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("2")){
					sql.append("AND BUSINESS_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("3")){
					sql.append("AND BUSINESS_OPENING LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("4")){
					sql.append("AND BUSINESS_PRICE LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("5")){
					sql.append("AND BUSINESS_PERCENT LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("6")){
					sql.append("AND BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("7")){
					sql.append("AND BUSINESS_AREA LIKE CONCAT('%',?,'%')			\n");
				}
			}	
		}else if(searchKeyword.length() > 0){
			sql.append("AND (CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR BUSINESS_NAME LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR BUSINESS_OPENING LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_PRICE LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_PERCENT LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR BUSINESS_WAY LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR BUSINESS_AREA LIKE CONCAT('%',?,'%'))				\n");
		}
		sql.append("ORDER BY BUSINESS_NUM DESC												\n");
		sql.append("LIMIT ?, ?															\n");
		
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
				}	
		}else if(searchKeyword.length() > 0){
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
			//System.out.println("Busi selectpstmt:   "+pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BusinessDTO dto = new BusinessDTO();
				dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("CONSTRUCTION_NAME"));
				dto.setBusiName(rs.getString("BUSINESS_NAME"));
				dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
				dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
				dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
				dto.setBusiWay(rs.getString("BUSINESS_WAY"));
				dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
	
	public ArrayList<BusinessDTO> selectBusinessListViewExcel(int ConstNum, int pageno, String searchKeyword, int totalcnt, String[] checked){
		ArrayList<BusinessDTO> list = new ArrayList<BusinessDTO>();
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
		sql.append("SELECT 					\n");
		sql.append("TB_BUSINESS.BUSINESS_NUM,TB_BUSINESS.CONSTRUCTION_NUM , TB_CONSTRUCTION.CONSTRUCTION_NAME, TB_BUSINESS.BUSINESS_NAME,			\n");
		sql.append("TB_BUSINESS.BUSINESS_OPENING, TB_BUSINESS.BUSINESS_PRICE, TB_BUSINESS.BUSINESS_PERCENT, TB_BUSINESS.BUSINESS_WAY, TB_BUSINESS.BUSINESS_AREA, date_format(TB_BUSINESS.CRT_DATE, '%Y.%m.%d') as CRT_DATE, date_format(TB_BUSINESS.UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 														\n");
		sql.append("TB_CONSTRUCTION JOIN TB_BUSINESS														\n");
		sql.append("WHERE TB_CONSTRUCTION.CONSTRUCTION_NUM=TB_BUSINESS.CONSTRUCTION_NUM AND TB_BUSINESS.DEL_YN <> 'Y' AND TB_BUSINESS.CONSTRUCTION_NUM=?										\n");
		
		if(checked != null){
			for(int i=0; i<checked.length; i++){
				if(checked[i].equals("1")){
					sql.append("AND TB_CONSTRUCTION.CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("2")){
					sql.append("AND TB_BUSINESS.BUSINESS_NAME LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("3")){
					sql.append("AND TB_BUSINESS.BUSINESS_OPENING LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("4")){
					sql.append("AND TB_BUSINESS.BUSINESS_PRICE LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("5")){
					sql.append("AND TB_BUSINESS.BUSINESS_PERCENT LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("6")){
					sql.append("AND TB_BUSINESS.BUSINESS_WAY LIKE CONCAT('%',?,'%')			\n");
				}
				if(checked[i].equals("7")){
					sql.append("AND TB_BUSINESS.BUSINESS_AREA LIKE CONCAT('%',?,'%')			\n");
				}
			}	
		}else if(searchKeyword.length() > 0){
			sql.append("AND (TB_CONSTRUCTION.CONSTRUCTION_NAME LIKE CONCAT('%',?,'%')		\n");
			sql.append("OR TB_BUSINESS.BUSINESS_NAME LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR TB_BUSINESS.BUSINESS_OPENING LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR TB_BUSINESS.BUSINESS_PRICE LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR TB_BUSINESS.BUSINESS_PERCENT LIKE CONCAT('%',?,'%')				\n");
			sql.append("OR TB_BUSINESS.BUSINESS_WAY LIKE CONCAT('%',?,'%')					\n");
			sql.append("OR TB_BUSINESS.BUSINESS_AREA LIKE CONCAT('%',?,'%'))				\n");
		}
		sql.append("ORDER BY TB_BUSINESS.BUSINESS_NUM DESC												\n");
		sql.append("LIMIT ?, ?															\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(nCnt++, ConstNum);
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
				}	
		}else if(searchKeyword.length() > 0){
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
			
			//System.out.println("View selectpstmt:   "+pstmt.toString());
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BusinessDTO dto = new BusinessDTO();
				dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("TB_CONSTRUCTION.CONSTRUCTION_NAME"));
				dto.setBusiName(rs.getString("BUSINESS_NAME"));
				dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
				dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
				dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
				dto.setBusiWay(rs.getString("BUSINESS_WAY"));
				dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
	

	
	public BusinessDTO selectBusinessViewInfo(int BusiNum, int ConstNum){
		BusinessDTO dto = new BusinessDTO();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT 					\n");
		sql.append("TB_BUSINESS.BUSINESS_NUM,TB_BUSINESS.CONSTRUCTION_NUM , TB_CONSTRUCTION.CONSTRUCTION_NAME, TB_BUSINESS.BUSINESS_NAME,			\n");
		sql.append("TB_BUSINESS.BUSINESS_OPENING, TB_BUSINESS.BUSINESS_PRICE, TB_BUSINESS.BUSINESS_PERCENT, TB_BUSINESS.BUSINESS_WAY, TB_BUSINESS.BUSINESS_AREA, date_format(TB_BUSINESS.CRT_DATE, '%Y.%m.%d') as CRT_DATE, date_format(TB_BUSINESS.UDT_DATE, '%Y.%m.%d') as UDT_DATE	\n");
		sql.append("FROM 														\n");
		sql.append("TB_CONSTRUCTION JOIN TB_BUSINESS														\n");
		sql.append("WHERE TB_CONSTRUCTION.CONSTRUCTION_NUM=TB_BUSINESS.CONSTRUCTION_NUM AND TB_BUSINESS.BUSINESS_NUM=? AND TB_BUSINESS.CONSTRUCTION_NUM=?										\n");
		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, BusiNum);
			pstmt.setInt(2, ConstNum);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setBusiNum(rs.getInt("BUSINESS_NUM"));
				dto.setConstNum(rs.getInt("CONSTRUCTION_NUM"));
				dto.setConstName(rs.getString("TB_CONSTRUCTION.CONSTRUCTION_NAME"));
				dto.setBusiName(rs.getString("BUSINESS_NAME"));
				dto.setBusiOpening(rs.getString("BUSINESS_OPENING"));
				dto.setBusiPrice(rs.getString("BUSINESS_PRICE"));
				dto.setBusiPercent(rs.getString("BUSINESS_PERCENT"));
				dto.setBusiWay(rs.getString("BUSINESS_WAY"));
				dto.setBusiArea(rs.getString("BUSINESS_AREA"));
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
	
	public int updateBusinessView(int BusiNum, String busiName, String busiOpening, String busiPrice, String busiPercent, String busiWay, String busiArea){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		int result = 0;

		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TB_BUSINESS															  \n");
		sql.append("SET BUSINESS_OPENING = ?, BUSINESS_PRICE = ?,			  \n");
		sql.append("BUSINESS_PERCENT = ?, BUSINESS_WAY = ?, BUSINESS_AREA = ?, 		  \n");
		sql.append("UDT_DATE = now()		  \n");
		sql.append("WHERE BUSINESS_NUM = ?												  	      \n");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, busiOpening);
			pstmt.setString(2, busiPrice);
			pstmt.setString(3, busiPercent);
			pstmt.setString(4, busiWay);
			pstmt.setString(5, busiArea);
			pstmt.setInt(6, BusiNum);

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
	
	public String selectConstructionName(int constNum){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;

		String constName="";
		
		try {
		conn = ds.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT CONSTRUCTION_NAME			 \n");
		sql.append("FROM TB_CONSTRUCTION				 \n");
		sql.append("WHERE CONSTRUCTION_NUM=? 			 \n");
	
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, constNum);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			
			
			constName=rs.getString("CONSTRUCTION_NAME");
			System.out.println("in constName=    "+constName);
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
	return constName;
	}
	
}