package com.hitechhealth.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class FactoryDAO {

	 private static String connectTimeout = "";
	    public static boolean readDataDB = true;

	    public static final int ORACLE = 1;
	    public static final int SQLSERVER = 2;
	    public static final int FIREBIRD = 3;
	    public static final int SQLITE = 4;
	    public static final int MYSQL = 5;

	    public static String bd = "";
	    public static String user = "";
	    public static String pwd = "";
	    public static String classe = "";
	    public static String localConfigPropertiesPath = "";
	    public static String localResourcePath = "";

	    public static int database = MYSQL;

	    public static int getDataBase() {
	        return database;
	    }

	    public static void setResourcePath(String resourcePath) {
	        localResourcePath = resourcePath;
	        if (!localResourcePath.contains("/classes")) {
	            localResourcePath += "/classes/";
	        }
	        if (localResourcePath.charAt(localResourcePath.length() - 1) != '/') {
	            localResourcePath += "/";
	        }
	    }

	    public static String getResourcePath() {
	        return localResourcePath;
	    }

	    public static Connection getConnection(
	            String url,
	            String classe,
	            String usuario,
	            String senha
	    ) throws Exception {
	        try {
	            if (classe.contains("oracle")) {
	                database = ORACLE;
	            } else if (classe.contains("sqlserver") || classe.contains("jtds")) {
	                database = SQLSERVER;
	            } else if (classe.contains("firebird")) {
	                database = FIREBIRD;
	            } else if (classe.contains("mysql")) {
	                database = MYSQL;
	            } else {
	                database = SQLITE;
	            }

	            Class.forName(classe);
	            Connection conn = null;
	            if (database == SQLITE) {
	                conn = DriverManager.getConnection(url);
	            } else {
	                if (connectTimeout != null && !connectTimeout.equals("")) {
	                    Properties info = new Properties();
	                    info.put("user", usuario);
	                    info.put("password", senha);
	                    info.put("socketTimeout", connectTimeout);
	                    conn = DriverManager.getConnection(url, info);
	                } else {
	                    conn = DriverManager.getConnection(url, usuario, senha);
	                }
	                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	            }

	            if (database == ORACLE) {
	                PreparedStatement ps = null;
	                try {
	                    ps = conn.prepareStatement(" ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY HH24:MI:SS' ");
	                    ps.executeUpdate();
	                } finally {
	                    close(ps);
	                }
	            }

	            return conn;
	        } catch (Exception e) {
	            readDataDB = true;
	            throw new Exception(e.getMessage());
	        }
	    }

	    public static Connection getConnection() throws Exception {
	        try {

	            if (readDataDB) {
	                try {
	                    if (localConfigPropertiesPath.isEmpty()) {
	                        if (!localResourcePath.isEmpty()) {
	                            localConfigPropertiesPath = localResourcePath + "config.properties";
	                        } else {
	                            FactoryDAO fac = new FactoryDAO();
	                            localConfigPropertiesPath = fac.getPathResource("config.properties");
	                            localResourcePath = localConfigPropertiesPath.substring(0, localConfigPropertiesPath.indexOf("config.properties"));
	                        }
	                    }


	                    FileReader pin = new FileReader(localConfigPropertiesPath.replaceAll("%20", " "));
	                    Properties props = new Properties();
	                    props.load(pin);
	                    pin.close();

	                    user = props.getProperty("config.user");
	                    pwd = props.getProperty("config.pwd");
	                    bd = props.getProperty("config.url");
	                    try {
	                        if (props.getProperty("config.classe").length() > 0) {
	                            classe = props.getProperty("config.classe");
	                        }
	                    } catch (Exception e) {
	                    }
	                } catch (Exception e) {
	                    System.out.println("Erro FactoryDAO: " + e.getMessage());
	                }
	                readDataDB = false;
	            }

	            return getConnection(bd, classe, user, pwd);
	        } catch (Exception e) {
	            readDataDB = true;
	            throw new Exception(e.getMessage());
	        }
	    }

	    public static long getGeneratedID(int chave) {
	        return System.nanoTime() + ((long) chave * 1000000000000000L);
	    }

	    public static Connection getConnection(String pathConfigProperties) throws Exception {
	        localConfigPropertiesPath = pathConfigProperties;
	        readDataDB = true;
	        return getConnection();
	    }

	    public static Connection getConnectionTimeout(String _connectTimeout) throws Exception {
	        connectTimeout = _connectTimeout;
	        return getConnection();
	    }

	    public static void closeConnection(Connection conn) throws Exception {
	        if (conn != null) {
	            conn.close();
	        }
	    }

	    public static void close(PreparedStatement ps) throws Exception {
	        if (ps != null) {
	            ps.close();
	        }
	    }

	    public static void close(PreparedStatement ps, ResultSet rs) throws Exception {
	        if (ps != null) {
	            ps.close();
	        }
	        if (rs != null) {
	            rs.close();
	        }
	    }

	    public static void close(ResultSet rs) throws Exception {
	        if (rs != null) {
	            rs.close();
	        }
	    }


	    public static String getDefaultDate() throws Exception {
	        if (database == SQLSERVER) {
	            return " GETDATE() ";
	        } else if (database == ORACLE) {
	            return " CURRENT_TIMESTAMP ";
	        } else if (database == MYSQL) {
	            return " CURRENT_TIMESTAMP ";
	        } else if (database == SQLITE) {
	            return " (datetime('now', 'localtime')) ";
	        } else if (database == FIREBIRD) {
	            return " timestamp ";
	        } else {
	            throw new Exception("Banco de dados não implementado na função getDefaultDate()");
	        }
	    }

	    private String getPathResource(String resourceName) {
	        ClassLoader classLoader = getClass().getClassLoader();
	        return classLoader.getResource(resourceName).getPath();
	    }

	    public static void setConnection(
	            String url,
	            String classebd,
	            String usuario,
	            String senha
	    ) throws Exception {
	        user = usuario;
	        pwd = senha;
	        bd = url;
	        classe = classebd;
	        readDataDB = false;
	    }

}
