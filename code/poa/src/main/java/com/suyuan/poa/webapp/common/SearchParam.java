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
    private int limit;
    private SortOrder sortType;
    private String sortField;

    public String toSQL() {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("ORDER BY ").append(sortField);
        sbSQL.append(" ").append(sortType);
        sbSQL.append(" LIMIT ").append(offset);
        sbSQL.append(" ").append(limit);

        return sbSQL.toString();
    }

    public enum SortOrder {
        asc, desc
    }
}
