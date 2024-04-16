package com.bobabrewery.util;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

/**
 * @author PailieXiangLong
 */
public class MailTemplateUtil {

    public static String loadTemplate(String code) throws Exception {
        ClassPathResource resource = new ClassPathResource("code.html");
        InputStream inputStream = resource.getInputStream();
        StringBuilder builder = new StringBuilder();
        int i;
        while ((i = inputStream.read()) != -1) {
            builder.append((char) i);
        }
        inputStream.close();
        return builder.toString().replace("${mailCode}", code);
    }

    public static String loadTemplate(Integer id) throws Exception {
        ClassPathResource resource = new ClassPathResource("mail" + id + ".html");
        InputStream inputStream = resource.getInputStream();
        StringBuilder builder = new StringBuilder();
        int i;
        while ((i = inputStream.read()) != -1) {
            builder.append((char) i);
        }
        inputStream.close();
        return builder.toString();
    }

}
