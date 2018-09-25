package com.suyuan.poa.webapp.common;

/**
 * DB常量定义类。
 */
public class DBConstant {
    private DBConstant() {
    }

    // USER TABLE
    public static final String USER_TABLE = "USER";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String POLICE_NUMBER = "POLICE_NUMBER";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String USER_ROLE = "USER_ROLE";
    public static final String PASSWORD = "USER_PASSWORD";

    // DEPT TABLE
    public static final String DEPT_TABLE = "DEPT";
    public static final String DEPT_ID = "DEPT_ID";
    public static final String DEPT_PARENT_ID = "PARENT_ID";
    public static final String DEPT_NAME = "DEPT_NAME";
    public static final String DEPT_REMARK = "DEPT_REMARK";

    // DEPT MANAGER TABLE
    public static final String DEPT_MANAGER_TABLE = "DEPT_MANAGER";
    public static final String DEPT_MANAGER_ID = "MANAGER_ID";

    // CODE
    public static final String CODE_TABLE = "CODE";
    public static final String CODE_ID = "CODE_ID";
    public static final String CODE_TYPE_ID = "CODE_TYPE";
    public static final String CODE_TYPE_NAME = "CODE_TYPE_NAME";
    public static final String CODE_NAME = "CODE_NAME";
}
