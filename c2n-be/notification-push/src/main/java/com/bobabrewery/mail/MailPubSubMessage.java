package com.bobabrewery.mail;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author PailieXiangLong
 */
@Data
public class MailPubSubMessage implements Serializable {
    /**
     * 要发送过去的地址
     */
    private String to;
    /**
     * 邮件模板ID
     */
    private Integer templateId;

    /**
     * 附件文件IDs
     */
    private List<Integer> attachmentFileIds;

    /**
     * 邮件模板中的可用变量
     */
    private MailPubSubMessage.Variable variable;

    /**
     * 邮件模板中的可用变量
     * eg: 在邮件模板中使用`${date}`会被替换为发信时间
     */
    @Data
    public static class Variable {
        /**
         * 钱包地址
         */
        private String walletAddress;
        /**
         * 发信时间
         */
        private String date;
    }

}
