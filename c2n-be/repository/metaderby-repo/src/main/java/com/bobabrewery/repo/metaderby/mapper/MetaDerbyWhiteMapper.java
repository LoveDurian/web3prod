package com.bobabrewery.repo.metaderby.mapper;

/**
 * @author orange
 * @Entity com.bobabrewery.repo.metaderby.domain.model.MetaDerbyWhite
 */
public interface MetaDerbyWhiteMapper {
    /**
     * 查使用10U的马匹ID
     *
     * @param walletAddress
     * @return
     */
    Integer findByWalletAddress(String walletAddress);

    int countByHorsesId(Integer horsesId);

    int update(String walletAddress, String horses);

    int count();
}




