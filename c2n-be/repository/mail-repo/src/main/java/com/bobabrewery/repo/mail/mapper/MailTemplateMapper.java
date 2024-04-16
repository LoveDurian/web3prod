package com.bobabrewery.repo.mail.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.mail.domain.MailTemplate;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface MailTemplateMapper {

    int deleteById(Long id);

    int create(MailTemplate record);

    int creates(List<MailTemplate> records);

    int createHas(MailTemplate record);

    Long count(MailTemplate where);

    List<MailTemplate> list(PageParam page, MailTemplate where);

    MailTemplate findById(Long id);

    List<MailTemplate> findByIds(List<Long> ids);

    int updateByIdHas(MailTemplate record);

    int updateById(MailTemplate record);
}
