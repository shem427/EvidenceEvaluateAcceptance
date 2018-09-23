package com.suyuan.poa.webapp.common;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

@Component
public class MessageService {
    @Resource
    private MessageSource messageSource;

    public String getMessage(String code){
        return getMessage(code, null);
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, code);
    }

    public String getMessage(String code, Object[] args, String defaultValue) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultValue, locale);
    }
}
