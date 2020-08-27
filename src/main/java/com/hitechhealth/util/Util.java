package com.hitechhealth.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Util {

    public static String FORMATO_DATAHORA_WEB = "yyyy-MM-dd'T'hh:mm:ss";
    public static String FORMATO_DATAHORA_DBMYSQL = "yyyy-MM-dd hh:mm:ss";


    public static java.sql.Timestamp getDataSistema() throws Exception {
        try {
            Date dateNow = new Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
            String now = dateformat.format(dateNow);
            String datahora = now;

            DateFormat fmt = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
            java.sql.Timestamp data = new java.sql.Timestamp(fmt.parse(datahora).getTime());
            return data;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static java.sql.Timestamp getData() throws Exception {
        try {
            Date dateNow = new Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyyy");
            String now = dateformat.format(dateNow);
            String datahora = now;

            DateFormat fmt = new SimpleDateFormat("ddMMyyyy");
            java.sql.Timestamp data = new java.sql.Timestamp(fmt.parse(datahora).getTime());
            return data;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static java.sql.Timestamp getData(Timestamp dataHora) throws Exception {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyyy");
            String now = dateformat.format(dataHora);
            String datahora = now;

            DateFormat fmt = new SimpleDateFormat("ddMMyyyy");
            java.sql.Timestamp data = new java.sql.Timestamp(fmt.parse(datahora).getTime());
            return data;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static java.sql.Timestamp getDataHora(String datahora) throws Exception {
        datahora = datahora.replace("/", "");

        if (datahora == null || datahora.equals("")) {
            Date dateNow = new Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyyyHHmmss");
            String now = dateformat.format(dateNow);
            datahora = now;
        }

        try {
            DateFormat fmt = new SimpleDateFormat("ddMMyyyyHHmmss");
            java.sql.Timestamp data = new java.sql.Timestamp(fmt.parse(datahora).getTime());
            if (getAjusteDataHora(getDataSistema(), 1).before(data)) {
                throw new Exception("");
            }
            return data;
        } catch (Exception e) {
            return getDataSistema();
        }
    }

    public static String getParamDataHoraServidor(int ajusteHora) {
        Date dateNow;
        try {
            dateNow = getAjusteMinutos(getDataSistema(), 60 * ajusteHora);
        } catch (Exception e) {
            dateNow = new Date();
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyyyHHmmss");
        String now = dateformat.format(dateNow);
        return "DataHora=" + now;
    }

    public static java.sql.Timestamp getAjusteMinutos(java.sql.Timestamp data, int minutos) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MINUTE, minutos);
        String newdate = dateformat.format(cal.getTime());
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.sql.Timestamp dataNova = new java.sql.Timestamp(fmt.parse(newdate).getTime());
        return dataNova;
    }

    public static java.sql.Timestamp getAjusteSegundos(java.sql.Timestamp data, int segundos) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.SECOND, segundos);
        String newdate = dateformat.format(cal.getTime());
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.sql.Timestamp dataNova = new java.sql.Timestamp(fmt.parse(newdate).getTime());
        return dataNova;
    }

    public static int getMinutesBetweenTimeStamps(Timestamp data1, Timestamp data2) throws Exception {
        try {
            return (int) ((data1.getTime() - data2.getTime()) / 1000 / 60);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getLocale(HttpServletRequest request) throws Exception {
        Locale locale = request.getLocale();
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static String getString(String valor) {
        try {
            if (valor == null) {
                return "";
            } else {
                return valor;
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public static String getParametro(String nomeParametro, HttpServletRequest request) {
        String retorno = "";
        try {
            if (request.getParameter(nomeParametro) != null) {
                retorno = request.getParameter(nomeParametro);
            }
        } catch (Exception e) {
            retorno = "";
        }
        return retorno;
    }

    public static void GravaLogWeb(HttpServletRequest request) {
        if (request != null) {
            Enumeration paraNames = request.getParameterNames();
            String pname;
            String pvalue;
            String resultado = "";

            while (paraNames.hasMoreElements()) {
                pname = (String) paraNames.nextElement();
                pvalue = request.getParameter(pname);
                resultado = "&" + pname + "=" + pvalue + resultado;
            }
            //Logger.getLogger(request.getRequestURI()).debug(request.getServletPath() + "?" + resultado);
        }
    }

    public static int getInteiro(String valor) {
        try {
            double fValor = Double.parseDouble(valor.replace(",", "."));
            return (int) fValor;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long getLong(String valor) {
        try {
            double fValor = Double.parseDouble(valor.replace(",", "."));
            return (long) fValor;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static java.sql.Timestamp getTimestamp(String valor, String formato) {
        try {
            // "yyyy-MM-dd hh:mm:ss.SSS"
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            Date parsedDate = dateFormat.parse(valor);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            try {
                return getDataSistema();
            } catch (Exception ex) {
            }
        }
        return null;
    }

    public static double getNumero(String valor) {
        try {
            double fValor = Double.parseDouble(valor.replace(",", "."));
            return fValor;
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public static String getNumeroFormat(Double valor, int casasDecimais) {
        String temp = "";
        temp = temp.format("%." + casasDecimais + "f", valor);
        temp = temp.replace(',', '.');
        return temp;
    }


    public static java.sql.Timestamp getAjusteDataHora(java.sql.Timestamp data, int Horas) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.HOUR, Horas);
        String newdate = dateformat.format(cal.getTime());
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.sql.Timestamp dataNova = new java.sql.Timestamp(fmt.parse(newdate).getTime());
        return dataNova;
    }


    
    /**
     * Converter uma String Json em ArrayList.
     * @param json
     * @return
     * @throws Exception
     */
    public static ArrayList<?> jsonToArrayList(String json) throws Exception{
     	ObjectMapper mapper = new ObjectMapper();
     	return mapper.readValue(json, new TypeReference<ArrayList<?>>() {});
    }
    
    public static boolean isValidEmailAddressRegex(String email) {
        boolean isEmailIdValid  = false;

        if (email != null && email.length() > 0) {
            String  expression  = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern     = Pattern.compile( expression, Pattern.CASE_INSENSITIVE );
            Matcher matcher     = pattern.matcher( email );

            if (matcher.matches()) {
                isEmailIdValid   = true;
            }
        }

        return isEmailIdValid;
    }
}
