package com.example.securityoauth.utils;


import com.example.securityoauth.enum_package.type.SocialLoginType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialTypeConverter implements Converter<String, SocialLoginType> {
    @Override
    public SocialLoginType convert(String value) {
        return SocialLoginType.valueOf(value.toUpperCase());
    }
}
