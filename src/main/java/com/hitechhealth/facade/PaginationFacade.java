package com.hitechhealth.facade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.hitechhealth.dao.FactoryDAO;
import com.hitechhealth.dao.PaginationDAO;
import com.hitechhealth.vo.PaginationFilterVO;
import com.hitechhealth.vo.PaginationVO;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class PaginationFacade {


	public PaginationVO getAll(
            Class classe,
            String tableView,
            int page, 
            int pageSize, 
            List<PaginationFilterVO> whereList,
            String orderField, 
            String ordination
    ) throws Exception {
        Connection conn = null;
        try {
            conn = FactoryDAO.getConnection();
            PaginationDAO paginationDAO = new PaginationDAO(classe, conn, tableView);
            return paginationDAO.getAll(
                    page, 
                    pageSize, 
                    whereList,
                    orderField, 
                    ordination);
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }
    
    public PaginationVO getAll(
            Class classe,
            String tableView,
            int page, 
            int pageSize, 
            List<String> whereField,
            List<String> valueWhereField,
            List<String> typeWhereField,
            String orderField, 
            String ordination
    ) throws Exception {
        Connection conn = null;
        try {
            conn = FactoryDAO.getConnection();
            
            List<PaginationFilterVO> paginationFilter = null;
            if ((whereField != null) && (valueWhereField != null) && (typeWhereField != null)) {
                if ((whereField.size() == valueWhereField.size()) && (whereField.size() == typeWhereField.size())) {
                    paginationFilter = new ArrayList<PaginationFilterVO>();
                    for (int idx = 0; idx < whereField.size(); idx++) {
                        PaginationFilterVO filtro = new PaginationFilterVO();
                        filtro.setField(whereField.get(idx));
                        filtro.setValue(valueWhereField.get(idx));
                        if (typeWhereField.get(idx).equals("LIKE")) {
                            filtro.setSearchType(PaginationFilterVO.TypeSearchFilterPagination.LIKE);
                        }else if (typeWhereField.get(idx).equals("SMALLER_EQUAL")) { 
                        	filtro.setSearchType(PaginationFilterVO.TypeSearchFilterPagination.SMALLER_EQUAL);
                        }else if (typeWhereField.get(idx).equals("GREATER_EQUAL")) { 
                        	filtro.setSearchType(PaginationFilterVO.TypeSearchFilterPagination.GREATER_EQUAL);
                        }else {
                            filtro.setSearchType(PaginationFilterVO.TypeSearchFilterPagination.EQUALS);
                        }                        
                        paginationFilter.add(filtro);
                    }
                }
            }
            
            PaginationDAO paginationDAO = new PaginationDAO(classe, conn, tableView);
            return paginationDAO.getAll(
                    page, 
                    pageSize, 
                    paginationFilter,
                    orderField, 
                    ordination);
        } finally {
            FactoryDAO.closeConnection(conn);
        }
    }
    
}
