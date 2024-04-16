package com.bobabrewery.service;

import com.bobabrewery.repo.common.domain.model.UserRegisterProduct;

import java.util.List;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yunmengmeng
 * @since 2022-01-19
 */
public interface IUserRegisterProductService {

    boolean save(UserRegisterProduct entity);

    boolean saveOrUpdate(UserRegisterProduct entity);

    /**
     * 查询用户当前注册项目
     *
     * @param accountId
     * @return
     */
    List<UserRegisterProduct> selectByAccountId(String accountId);

    /**
     * 查询用户的Allocations
     *
     * @param accountId
     * @param productId
     * @return
     */
    UserRegisterProduct queryUserAllocations(String accountId, Integer productId);

    /**
     * 更新用户staking_amount
     *
     * @param userRegisterProduct
     */
    void updateByEntity(UserRegisterProduct userRegisterProduct);
}
