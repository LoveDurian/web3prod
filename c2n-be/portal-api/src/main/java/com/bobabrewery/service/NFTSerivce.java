package com.bobabrewery.service;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.util.Page;
import com.bobabrewery.domain.param.CreateINOOrder;
import com.bobabrewery.domain.param.CreateMoveOrder;
import com.bobabrewery.domain.resp.SignMintResult;
import com.bobabrewery.repo.common.domain.model.Nft;

import java.util.concurrent.ExecutionException;

/**
 * @author PailieXiangLong
 */
public interface NFTSerivce {


    void callbackSR(String walletAddress, Integer[] tokenIds, String txId, Integer status);

    Result<SignMintResult> createSacredRealm(CreateINOOrder order) throws ExecutionException, InterruptedException;

    Result<SignMintResult> createEternalWorld(CreateINOOrder order) throws ExecutionException, InterruptedException;

    Page<Nft> list(PageParam pageParam);

    Nft findById(Integer id);

    Result<SignMintResult> createMoveRefund(CreateMoveOrder order) throws ExecutionException, InterruptedException;

    Result<String> moveCanRefund(String walletAddress);

}
