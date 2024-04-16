package com.bobabrewery.service;

import com.bobabrewery.repo.common.domain.model.Project;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yunmengmeng
 * @since 2022-01-13
 */
public interface IProjectService {

    boolean updateById(Project entity);

    int updateCurrentPriceByID(BigDecimal currentPrice, Integer id);

    List<Project> list();

    List<Project> selectNotEndProduct();

    List<Project> selectAllStopProject();


    void updateStatus(Integer status, Integer id);
}
