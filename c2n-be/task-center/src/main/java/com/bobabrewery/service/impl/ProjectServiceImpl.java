package com.bobabrewery.service.impl;

import com.bobabrewery.repo.common.domain.model.Project;
import com.bobabrewery.repo.common.mapper.ProductContractMapper;
import com.bobabrewery.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yunmengmeng
 * @since 2022-01-13
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProductContractMapper mapper;

    @Override
    public boolean updateById(Project entity) {
        return mapper.updateById(entity);
    }

    @Override
    public int updateCurrentPriceByID(BigDecimal currentPrice, Integer id) {
        return mapper.updateCurrentPriceByID(currentPrice, id);
    }

    @Override
    public List<Project> list() {
        return mapper.list();
    }

    @Override
    public List<Project> selectNotEndProduct() {
        return this.mapper.selectNotEndProduct();
    }

    @Override
    public List<Project> selectAllStopProject() {
        return mapper.selectAllStopProject();
    }

    @Override
    public void updateStatus(Integer status, Integer id) {
        mapper.updateStatus(status, id);
    }
}
