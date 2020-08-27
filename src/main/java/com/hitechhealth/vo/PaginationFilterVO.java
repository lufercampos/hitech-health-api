package com.hitechhealth.vo;

public class PaginationFilterVO {
    
	public enum TypeSearchFilterPagination {
        EQUALS,
        LIKE,
        SMALLER_EQUAL,
    	GREATER_EQUAL
    }
    
    private String field = "";
    private String value = "";
    private TypeSearchFilterPagination searchType = TypeSearchFilterPagination.EQUALS;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public TypeSearchFilterPagination getSearchType() {
        return searchType;
    }

    public void setSearchType(TypeSearchFilterPagination searchType) {
        this.searchType = searchType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
