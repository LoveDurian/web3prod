package com.bobabrewery.repo.common.mapper;

import com.bobabrewery.repo.common.domain.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoDao {

    int insertSelective(UserInfo record);

    List<UserInfo> selectByEmail(@Param("loginEmail") String loginEmail, @Param("accountId") String accountId);

    List<UserInfo> selectByEmailOnly(@Param("loginEmail") String loginEmail);

    List<UserInfo> selectByAccountId(@Param("accountId") String accountId);

    UserInfo findByAccountId(@Param("accountId") String accountId);

    void updateTgInfo(@Param("accountId") String accountId,
                      @Param("tgId") String tgId,
                      @Param("tgName") String tgName);

    void updateAddressInfo(@Param("accountId") String accountId,
                           @Param("userAddress") String userAddress);

}