package com.bobabrewery.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

import javax.annotation.PostConstruct;

/**
 * @Author chenkj
 * @Date 2021/12/30 4:41 下午
 */
@Component
public class CredentialsUtils {

    private static Credentials credentials;
    @Value("${owner.private.key}")
    private String privateKey;

    @PostConstruct
    public void initCredentials() {
        credentials = Credentials.create(privateKey);
    }

    public static Credentials getCredentials() {
        return credentials;
    }

}
