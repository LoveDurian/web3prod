package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.UserRegisterProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yunmengmeng
 * @since 2022-01-19
 */
public interface UserRegisterProductMapper {

    boolean insertOne(UserRegisterProduct entity);

    UserRegisterProduct selectByParams(@Param("accountId") String accountId, @Param("productId") Integer productId);

    List<UserRegisterProduct> selectByAccountId(@Param("accountId") String accountId);

    UserRegisterProduct findByAccountIdAndProductId(String accountId, Integer productId);

    List<UserRegisterProduct> findAllByProductId(Integer productId);

    void updateByEntity(UserRegisterProduct userRegisterProduct);

    List<UserRegisterProduct> selectByPrductId(Integer id);

    int updatePurchased(UserRegisterProduct userRegisterProduct);
}
