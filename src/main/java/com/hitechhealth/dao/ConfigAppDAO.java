package com.hitechhealth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hitechhealth.vo.ConfigAppVO;

public class ConfigAppDAO extends GenericDAO {

    public ConfigAppDAO(Connection conn) {
        super(conn);
    }

    public ConfigAppVO getConfigApp() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM configapp");
            rs = ps.executeQuery();
            if (rs.next()) {
                return fillObject(rs);
            }
        } finally {
            FactoryDAO.close(ps, rs);
        }
        return null;
    }

    
    private ConfigAppVO fillObject(ResultSet rs) throws Exception {
    	ConfigAppVO configAppVO = new ConfigAppVO();
    	
    	configAppVO.setSessionTime(rs.getInt("sessionTime"));
    	configAppVO.setUrlClient(rs.getString("urlClient"));
        return configAppVO;
    }

    
}
