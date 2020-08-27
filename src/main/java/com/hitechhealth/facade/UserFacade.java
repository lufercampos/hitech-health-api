package com.hitechhealth.facade;

import java.sql.Connection;
import java.sql.Timestamp;

import com.hitechhealth.dao.ConfigAppDAO;
import com.hitechhealth.dao.FactoryDAO;
import com.hitechhealth.dao.UserDAO;
import com.hitechhealth.util.Util;
import com.hitechhealth.vo.ConfigAppVO;
import com.hitechhealth.vo.UserVO;


public class UserFacade {
	
	public void save(UserVO userVO, String url) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			
			conn.setAutoCommit(false);
			
			UserDAO userDAO = new UserDAO(conn);

			userDAO.save(userVO, url);
			
			conn.commit();

		} catch (Exception e) {
			if (conn != null) {
				conn.rollback();
			}
			
			throw e;
		}
		finally {

			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public int validateUser(String key) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			return userDAO.validateUser(key);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}
		
	}

	public String authenticatesUser(String email, String password) throws Exception {

		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			return userDAO.authenticatesUser(email, password);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}

	public UserVO getUserByToken(String token) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			return userDAO.getUserByToken(token);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}

	public void finishSession(String token) throws Exception {

		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			userDAO.finishSession(token);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}
	}

	public void setLastAcess(UserVO userVO) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			userDAO.setLastAcess(userVO);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}

	public boolean isTokenValido(String token) throws Exception {
		Connection conn = null;
		try {
			conn = FactoryDAO.getConnection();
			UserVO userVO = getUserByToken(token);
			
			if (userVO != null) {
				
				ConfigAppDAO configAppDAO = new ConfigAppDAO(conn);
				ConfigAppVO configAppVO = configAppDAO.getConfigApp();
				
				if (Util.getMinutesBetweenTimeStamps(new Timestamp(System.currentTimeMillis()),
						userVO.getLastAcess()) <= configAppVO.getSessionTime()) {

					setLastAcess(userVO);

					return true;
				}
			}
		} finally {

			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}

}
