package com.hitechhealth.facade;

import java.sql.Connection;

import com.hitechhealth.dao.EmployeeDAO;
import com.hitechhealth.dao.FactoryDAO;
import com.hitechhealth.vo.EmployeeVO;

public class EmployeeFacade {

	public EmployeeVO getById(int id) throws Exception {
        Connection conn = null;
        try {
            conn = FactoryDAO.getConnection();
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            return employeeDAO.getById(id);
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }

	public EmployeeVO insert(EmployeeVO employeeVO) throws Exception {
        Connection conn = null;
        try {
        	conn = FactoryDAO.getConnection();
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            
            return employeeDAO.insert(employeeVO);
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }

	public EmployeeVO update(EmployeeVO employeeVO) throws Exception {
        Connection conn = null;
        try {
        	conn = FactoryDAO.getConnection();
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            
            return employeeDAO.update(employeeVO);
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }

	public void delete(int id) throws Exception {
        Connection conn = null;
        try {
        	conn = FactoryDAO.getConnection();
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            employeeDAO.delete(id);
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }


}
