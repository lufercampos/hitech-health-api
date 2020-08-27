package com.hitechhealth.facade;

import java.sql.Connection;

import com.hitechhealth.dao.ConfigAppDAO;
import com.hitechhealth.dao.FactoryDAO;
import com.hitechhealth.vo.ConfigAppVO;

public class ConfigAppFacade {

	public ConfigAppVO getConfigApp() throws Exception {
        Connection conn = null;
        try {
            conn = FactoryDAO.getConnection();
            ConfigAppDAO configAppDAO = new ConfigAppDAO(conn);
            return configAppDAO.getConfigApp();
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }


}
