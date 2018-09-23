package com.suyuan.poa.webapp.setting.bean;

import com.suyuan.poa.webapp.common.CommonBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangePassword extends CommonBean {
    private String oldPassword;
    private String newPassword;
}
