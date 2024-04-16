package com.bobabrewery.repo.mail.mapper;

import com.bobabrewery.repo.mail.domain.MailHistory;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface MailHistoryMapper {

    int create(MailHistory record);

    int creates(List<MailHistory> records);

}
