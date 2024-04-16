package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.repo.common.domain.model.PromoCode;

import java.util.List;

/**
 * @author PailieXiangLong
 */
public interface PromoCodeMapper {

    int deleteById(Long id);

    int create(PromoCode record);

    int creates(List<PromoCode> records);

    int createHas(PromoCode record);

    Long count(PromoCode where);

    List<PromoCode> list(PageParam page, PromoCode where);

    PromoCode findById(Long id);

    List<PromoCode> findByIds(List<Long> ids);

    int updateByIdHas(PromoCode record);

    int updateById(PromoCode record);

    PromoCode findByPromoCode(String code);
}
