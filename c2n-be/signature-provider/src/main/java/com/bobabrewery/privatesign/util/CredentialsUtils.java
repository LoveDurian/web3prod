package com.bobabrewery.privatesign.util;

import com.bobabrewery.common.enums.ReCode;
import com.bobabrewery.common.exceptin.CommonException;
import org.web3j.crypto.Credentials;

/**
 * @author orange
 */
public class CredentialsUtils {
    public static String privateKey;
    private static Credentials credentials;

    private CredentialsUtils() {
    }

    public static synchronized Credentials getInstance() {
        if (privateKey != null) {
            credentials = Credentials.create(privateKey);
            return credentials;
        } else {
            throw new CommonException(ReCode.PRIVATE_KEY_NOT_EXIST);
        }
    }
}
