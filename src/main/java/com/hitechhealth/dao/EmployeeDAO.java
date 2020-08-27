package com.hitechhealth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hitechhealth.vo.EmployeeVO;

public class EmployeeDAO extends GenericDAO {

    public EmployeeDAO(Connection conn) {
        super(conn);
    }

    public EmployeeVO getById(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM employee WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return fillObject(rs);
            }
        } finally {
            FactoryDAO.close(ps, rs);
        }
        return null;
    }

    public EmployeeVO insert(EmployeeVO employeeVO) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO employee ( "
                    + "   id, "
                    + "   name, " 
                    + "   profession, " 
                    + "   city, "
                    + "   branch "
                    + " ) VALUES ( "
                    + "   ?, "
                    + "   ?, "
                    + "   ?, "
                    + "   ?, "
                    + "   ? "
                    + " ) ");
            ps.setInt(1, employeeVO.getId());
            ps.setString(2, employeeVO.getName());
            ps.setString(3, employeeVO.getProfession());
            ps.setString(4, employeeVO.getCity());
            ps.setString(5, employeeVO.getBranch());
            ps.executeUpdate();
            
            return this.getById(employeeVO.getId());
            
        } finally {
            FactoryDAO.close(ps);
        }
    }

    public EmployeeVO update(EmployeeVO employeeVO) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE employee SET "
                    + "   name = ?, " 
                    + "   profession = ?, " 
                    + "   city = ?, "
                    + "   branch = ? where id = ?"); 
            ps.setString(1, employeeVO.getName());
            ps.setString(2, employeeVO.getProfession());
            ps.setString(3, employeeVO.getCity());
            ps.setString(4, employeeVO.getBranch());
            ps.setInt(5, employeeVO.getId());
            ps.executeUpdate();
            
            return this.getById(employeeVO.getId());
            
        } finally {
            FactoryDAO.close(ps);
        }
    }

    public void delete(int id) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM employee"
                    + " WHERE id = ? ");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            FactoryDAO.close(ps);
        }
    }

    private EmployeeVO fillObject(ResultSet rs) throws Exception {
    	EmployeeVO employeeVO = new EmployeeVO();
    	
    	employeeVO.setId(rs.getInt("id"));
    	employeeVO.setName(rs.getString("name"));
    	employeeVO.setProfession(rs.getString("profession"));
    	employeeVO.setCity(rs.getString("city"));
    	employeeVO.setBranch(rs.getString("branch"));
    	
        return employeeVO;
    }

}
