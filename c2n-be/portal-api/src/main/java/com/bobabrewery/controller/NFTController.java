package com.bobabrewery.controller;

import com.bobabrewery.common.PageParam;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.util.Page;
import com.bobabrewery.repo.common.domain.model.Nft;
import com.bobabrewery.service.NFTSerivce;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * NFT相关功能
 *
 * @author PailieXiangLong
 */
@RestController
@RequestMapping("/nft")
public class NFTController {

    @Resource
    private NFTSerivce nftSerivce;


    /**
     * 检查Move持有人是否在退款名单中
     *
     * @param walletAddress
     * @return
     * @apiNote 200 在 503 不在
     */
    @PostMapping("/move/can")
    Result<String> moveResult(@RequestParam String walletAddress) {
        return nftSerivce.moveCanRefund(walletAddress);
    }

    /**
     * 项目列表
     *
     * @return
     */
    @GetMapping("/list")
    Result<Page<Nft>> list(@Validated PageParam pageParam) {
        return Result.ok(nftSerivce.list(pageParam));
    }


    /**
     * 项目详情接口
     *
     * @param id 项目ID
     * @return
     */
    @GetMapping("/{id}")
    Result<Nft> base(@PathVariable Integer id) {
        return Result.ok(nftSerivce.findById(id));
    }

}
