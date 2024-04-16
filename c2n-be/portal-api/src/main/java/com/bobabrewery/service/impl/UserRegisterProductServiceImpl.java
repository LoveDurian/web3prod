package com.bobabrewery.service.impl;

import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserRegisterProduct;
import com.bobabrewery.repo.common.mapper.UserRegisterProductMapper;
import com.bobabrewery.service.IUserRegisterProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yunmengmeng
 * @since 2022-01-19
 */
@Service
@RequiredArgsConstructor
public class UserRegisterProductServiceImpl implements IUserRegisterProductService {

    private final UserRegisterProductMapper mapper;

    @Override
    public boolean save(UserRegisterProduct entity) {
        return mapper.insertOne(entity);
    }

    @Override
    public boolean saveOrUpdate(UserRegisterProduct entity) {
        UserRegisterProduct oldEntity = this.mapper.selectByParams(entity.getAccountId(), entity.getProductId());
        if (oldEntity != null) {
            throw new CommonException(ReCode.USER_HAS_REGISTERED);
        }
        return save(entity);
    }

    @Override
    public List<UserRegisterProduct> selectByAccountId(String accountId) {
        return this.mapper.selectByAccountId(accountId);
    }

    @Override
    public UserRegisterProduct queryUserAllocations(String accountId, Integer productId) {
        return mapper.findByAccountIdAndProductId(accountId, productId);
    }

    @Override
    public void updateByEntity(UserRegisterProduct userRegisterProduct) {
        this.mapper.updateByEntity(userRegisterProduct);
    }
}
