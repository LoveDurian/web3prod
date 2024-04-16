package com.bobabrewery.repo.user.mapper;

import com.bobabrewery.repo.user.domain.User;

/**
* @author orange
*/
public interface UserMapper {

    /**
     * 根据钱包地址查询用户信息
     * @param walletAddress
     * @return
     */
    User selectUserByWalletAddress(String walletAddress);
}




