package com.bobabrewery.repo.common.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * outdata_user
 *
 * @author
 */
@Data
public class UserInfo implements Serializable {
    /**
     * id
     */
    private Integer id;

    private String loginEmail;

    private String accountId;

    private Date loginTime;

    private String loginIp;

    private String registerIp;

    private String tgName;

    private String tgId;

    private String userAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}