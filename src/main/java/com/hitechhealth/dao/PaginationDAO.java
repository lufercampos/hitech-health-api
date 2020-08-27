package com.hitechhealth.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hitechhealth.annotation.PaginationAnnotation;
import com.hitechhealth.vo.PaginationFilterVO;
import com.hitechhealth.vo.PaginationFilterVO.TypeSearchFilterPagination;
import com.hitechhealth.vo.PaginationVO;


public class PaginationDAO<T> extends GenericDAO {
    private String tableOrViewName = "";
    private final Class<T> classe;
    
    public PaginationDAO(Class<T> classe, Connection conn, String tableOrViewName) {
        super(conn);
        this.tableOrViewName = tableOrViewName;
        this.classe = classe;
    }
    
    @SuppressWarnings("resource")
	public PaginationVO<T> getAll(
            int page, 
            int pageSize, 
            List<PaginationFilterVO> whereList,
            String orderField, 
            String ordination
    ) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PaginationVO<T> retorno = new PaginationVO<T>();
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String vSQL = "";
            String where = "";
            
            if (whereList != null) {            
                for (int idx = 0; idx < whereList.size(); idx++) {
                    if (idx == 0) {
                        where = " where ";
                    } else {
                        where += " and ";
                    }
                    
                    if(whereList.get(idx).getSearchType().equals(TypeSearchFilterPagination.EQUALS)){
                    	where += whereList.get(idx).getField() + " = '" + whereList.get(idx).getValue() + "' ";           
                    }else if(whereList.get(idx).getSearchType().equals(TypeSearchFilterPagination.LIKE)){
                    	where += whereList.get(idx).getField() + " like '%" + whereList.get(idx).getValue()+ "%' ";
                    }else if(whereList.get(idx).getSearchType().equals(TypeSearchFilterPagination.SMALLER_EQUAL)){
                    	where += whereList.get(idx).getField() + " <= '" + whereList.get(idx).getValue()+ "' ";
                    }else if(whereList.get(idx).getSearchType().equals(TypeSearchFilterPagination.GREATER_EQUAL)){
                    	where += whereList.get(idx).getField() + " >= '" + whereList.get(idx).getValue()+ "' ";
                    }
                    
                }
            }
            
            ps = conn.prepareStatement(" SELECT COUNT(1) REGS FROM " + tableOrViewName + " " + where);
            rs = ps.executeQuery();
            if (rs.next()) {            
                
                retorno.setTotalCount(rs.getInt(1));
                retorno.setPageSize(pageSize);
                
                if (page > retorno.getTotalPages()) {
                    page = retorno.getTotalPages();
                }
                
                if (page <= 0) {
                    page = 1;
                }
                
                retorno.setPage(page);
                
                if (FactoryDAO.getDataBase() == FactoryDAO.SQLSERVER) {
                    if (orderField.length() == 0) {
                        // Tem que buscar um campo para a ordenação, pois o SQLServer obriga ter uma ordenação para paginar
                        vSQL = " select * from " + tableOrViewName + " where 1 is null ";
                        ps = conn.prepareStatement(vSQL);
                        rs = ps.executeQuery();
                        ResultSetMetaData mdOrder = rs.getMetaData();
                        orderField = " order by " + mdOrder.getColumnName(1);
                    } else {
                        orderField = " order by " + orderField;
                    }
                    if (pageSize > 0) {
                        vSQL = " SELECT TOP " + pageSize + " * FROM "
                                + "      ( "
                                + "           SELECT *, "
                                + "           ROW_NUMBER() OVER (" + orderField + " " + ordination + ") AS rownum "
                                + "           FROM " + tableOrViewName + " " + where
                                + "      ) AS a "
                                + "      WHERE rownum >= " + ((pageSize * (page - 1) + 1));
                    } else {
                        vSQL = " SELECT * FROM " + tableOrViewName + " " + where + " " + orderField + " " + ordination;
                    }
                } else if (FactoryDAO.getDataBase() == FactoryDAO.MYSQL) {
                    if (orderField.length() == 0) {
                        orderField = " order by 1 "; // ordernar pela primeira coluna
                    } else {
                        orderField = " order by " + orderField;
                    }
                    if (pageSize > 0) {
                        vSQL = " SELECT * FROM " + tableOrViewName + " " + where + " " + orderField + " " + ordination + " limit " + (pageSize * (page - 1)) + ", " + pageSize;
                    } else {
                        vSQL = " SELECT * FROM " + tableOrViewName + " " + where + " " + orderField + " " + ordination;
                    }
                } else {
                    throw new Exception("Banco de dados não suportado para paginação");
                }
                
                
                ps = conn.prepareStatement(vSQL);
                rs = ps.executeQuery();
                
                
                List<Field> listaFields = new ArrayList<Field>();
                
                if (classe.getSuperclass() != null) {
                    Field f[] = classe.getSuperclass().getDeclaredFields();
                    for (Field f1 : f) {
                        listaFields.add(f1);
                    }
                }
                Field f[] = classe.getDeclaredFields();
                for (Field f1 : f) {
                    listaFields.add(f1);
                }
                
                while (rs.next()) {
                    T objeto = classe.newInstance();
                    for (int idx = 0; idx < listaFields.size(); idx++) {
                    //for (Field f1 : f) {
                        Field f1 = listaFields.get(idx);
                        Field field = null;
                        try {
                            field = objeto.getClass().getDeclaredField(f1.getName());
                        } catch (Exception e) {
                            field = objeto.getClass().getSuperclass().getDeclaredField(f1.getName());
                        }
                        f1.setAccessible(true);
                        field.setAccessible(true);
                        
                        Annotation oneToOneAnnotation = field.getAnnotation(PaginationAnnotation.OneToOne.class);
                        Annotation transientAnnotation = field.getAnnotation(PaginationAnnotation.Transient.class);
                        
                        Annotation formatDatePicker = field.getAnnotation(PaginationAnnotation.FormatDatePicker.class);
                        Annotation formatDateTimePicker = field.getAnnotation(PaginationAnnotation.FormatDateTimePicker.class);
                        
                        
                        if(transientAnnotation == null) {
                        	if (oneToOneAnnotation != null) {
                        		PaginationAnnotation.OneToOne ant = (PaginationAnnotation.OneToOne) oneToOneAnnotation;
                        		Object subObjeto = Class.forName(ant.classe().getName()).newInstance();
                        		Field subF[] = ant.classe().getDeclaredFields();
                        		for (Field f2 : subF) {
                        			f2.setAccessible(true);
                  			
                    				if(rs.getObject(f2.getName())  instanceof Timestamp) {
                        				if(formatDatePicker != null) {
                        					f2.set(subObjeto, sdfDate.format(rs.getObject(f2.getName())));
                        				}else if(formatDateTimePicker != null) {
                        					f2.set(subObjeto, sdfDateTime.format(rs.getObject(f2.getName())).trim().replace(" ", "T"));
                        				}else {
                        					f2.set(subObjeto, rs.getObject(f2.getName()));
                        				}
                        			}else if(rs.getObject(f2.getName())  instanceof BigDecimal) {
                        				f2.set(objeto, rs.getBigDecimal(f2.getName()).doubleValue());
                        			}else {
                        				f2.set(subObjeto, rs.getObject(f2.getName()));
                        			}
                        			
                        		}
                        		field.set(objeto, subObjeto);
                        	} else {
                        		
                        		
                        		
                        		if(rs.getObject(f1.getName()) != null) {
                        			
                        			if(rs.getObject(f1.getName()) instanceof Timestamp) {
                        				
                        				if(formatDatePicker != null) {
                        					field.set(objeto, sdfDate.format(rs.getTimestamp(f1.getName())));
                        				}else if(formatDateTimePicker != null) {
                        					field.set(objeto, sdfDateTime.format(rs.getTimestamp(f1.getName())).trim().replace(" ", "T"));
                        				}else {
                        					field.set(objeto, rs.getObject(f1.getName()) );
                        				}
                        			}else if(rs.getObject(f1.getName()) instanceof BigDecimal) {
                        				field.set(objeto, rs.getBigDecimal(f1.getName()).doubleValue());
                        			}else {
                        				field.set(objeto, rs.getObject(f1.getName()) );
                        			}
                        			
                        		}else {
                        			
                        			//Alteração para tratar setar null em atributo do tipo primitivo. Neste caso, esta considerando que usuamos apenas tipos numericos como primitivos.
                        			//Caso venhamos a usar byte, char ou short, esta condição deve ser alterada.
                        			if(field.getType().isPrimitive()) {
                        				field.set(objeto, 0);
                        			}else {
                        				field.set(objeto, null);
                        			}
                        			
                        		}
                        		
                        	}
                        }
                        
                    }
                    retorno.getItems().add(objeto);
                }
            }
        } finally {
            FactoryDAO.close(ps);
            FactoryDAO.close(rs);
        }
        return retorno;
    }
    
}
