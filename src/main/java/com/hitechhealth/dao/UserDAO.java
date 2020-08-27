package com.hitechhealth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.UUID;

import com.hitechhealth.util.SendMail;
import com.hitechhealth.vo.UserVO;

public class UserDAO extends GenericDAO {

	
	public UserDAO(Connection conn) {
		super(conn);
	}
	
	public void save(UserVO userVO, String url) throws Exception {
		if (userVO.getId() == 0) {
			this.insertUser(userVO, url);
		} else {
			this.updatetUser(userVO);
		}
	}
	
	private void insertUser(UserVO userVO, String url) throws Exception {
        PreparedStatement ps = null;
        try {
        	
        	
			UUID uuid = UUID.randomUUID();
			String keyValidation = uuid.toString();
			
			ps = conn.prepareStatement("INSERT INTO user ( "
					+ "   email, " 
					+ "   name, " 
					+ "   password, "
					+ "   keyValidation, "
					+ "   activeAccount "
					+ " ) VALUES ( "
					+ "   ?, "
					+ "   ?, "
					+ "   ?, "
					+ "   ?, "
					+ "   ? "
					+ " ) ");
			ps.setString(1, userVO.getEmail());
			ps.setString(2, userVO.getName());
			ps.setString(3, userVO.getPassword());
			ps.setString(4, keyValidation);
			ps.setString(5, "N" );
			ps.executeUpdate();
			
			SendMail.sendEmail(userVO.getEmail(), keyValidation, "HiTech - Test Crud Luiz - Confirm email", url);
            

        } finally {
            if(ps != null) {
            	ps.close();
            }
        }
    }
	
	private void updatetUser(UserVO userVO) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update user set "
                    + "   email = ?, " 
                    + "   name = ?, " 
                    + "   password = ? where id = ? and email = ?");
            ps.setString(1, userVO.getEmail());
            ps.setString(2, userVO.getName());
            ps.setString(3, userVO.getPassword());
            ps.setInt(4, userVO.getId());
            ps.setString(5, userVO.getEmail());
            ps.executeUpdate();

        } finally {
            if(ps != null) {
            	ps.close();
            }
        }
    }
	
	public int validateUser(String key) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	
        	ps = conn.prepareStatement("select id, email, activeAccount from user where keyValidation = ?");
			ps.setString(1, key);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				
				if("Y".equals(rs.getString("activeAccount").toUpperCase())) {
					return 0;
				}else {
					
					ps = conn.prepareStatement("update user set "
							+ "   activeAccount = ? " 
							+ "   where id = ? and email = ?");
					ps.setString(1, "Y");
					ps.setInt(2, rs.getInt("id"));
					ps.setString(3, rs.getString("email"));
					ps.executeUpdate();
					
				}
			}
			return 1;
        }  catch (Exception e) {
        	throw e;
		}
        
        finally {

			if (rs != null) {
				rs.close();
			}
			
            if(ps != null) {
            	ps.close();
            }
        }
    }



	public String authenticatesUser(String email, String password) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select * from user" + "  where email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			UUID uuid = UUID.randomUUID();
			String token = uuid.toString();

			if (rs.next()) {

				UserVO userVO = setUser(rs);
				
				if(userVO.isActiveAccount()) {
					
					ps.close();
					ps = conn
							.prepareStatement("update user set lastAcess = ?, token = ? " + "where id = ? and email = ?");
					ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					ps.setString(2, uuid.toString());
					ps.setInt(3, userVO.getId());
					ps.setString(4, userVO.getEmail());
					ps.executeUpdate();
					
					return token;
				}else {
					return "0";
				}
			}else {
				throw new Exception("User not found!");
			}

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

		}
	}

	public void finishSession(String token) throws Exception {
		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement("update user set lastAcess = ?, token = ? where token = ?");
			ps.setTimestamp(1, null);
			ps.setString(2, null);
			ps.setString(3, token);
			ps.executeUpdate();

		} finally {

			if (ps != null) {
				ps.close();
			}

		}
	}

	public void setLastAcess(UserVO userVO) throws Exception {
		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement("update user set lastAcess = ? where id = ? and email = ?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, userVO.getId());
			ps.setString(3, userVO.getEmail());
			ps.executeUpdate();

		} finally {

			if (ps != null) {
				ps.close();
			}

		}
	}

	public UserVO getUserByToken(String token) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select * from user where token = ?");
			ps.setString(1, token);
			rs = ps.executeQuery();

			if (rs.next()) {
				return this.setUser(rs);
			}

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

		}
		return null;
	}

	private UserVO setUser(ResultSet rs) throws Exception {
		UserVO user =  new UserVO();
		user.setEmail(rs.getString("email"));
		user.setId(rs.getInt("id"));
		user.setIdProfile(rs.getInt("idProfile"));
		user.setName(rs.getString("name"));
		user.setToken(rs.getString("token"));
		user.setLastAcess(rs.getTimestamp("lastAcess"));
		user.setActiveAccount(rs.getString("activeAccount") != null && "Y".equals(rs.getString("activeAccount").toUpperCase()));

		return user;
	}

}
