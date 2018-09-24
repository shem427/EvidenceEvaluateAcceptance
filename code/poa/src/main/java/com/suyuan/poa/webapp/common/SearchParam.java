package com.suyuan.poa.webapp.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Table检索条件Class。
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchParam {
    private int offset;
    private String limit;
    private SortOrder sortType;
    private String sortField;

    public enum SortOrder {
        asc, desc
    }
}
