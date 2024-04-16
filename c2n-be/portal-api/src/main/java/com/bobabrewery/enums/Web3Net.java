package com.bobabrewery.enums;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author orange
 */
public enum Web3Net {
    /**
     * Boba主网
     */
    MAIN_BOBA(288, "https://mainnet.boba.network"),
    /**
     * BSC主网
     */
    MAIN_BSC(56, "https://bsc-dataseed1.ninicoin.io"),
    /**
     * ETH_主网
     */
    MAIN_ETH(1, "https://mainnet.infura.io/v3/"),

    TEST_RINKEBY(4, "https://rinkeby.infura.io/v3/eaa5fb64cc5d4f43aa01d12ead1602f3"),
    /**
     * Boba测试网
     */
    TEST_BOBA(28, "https://rinkeby.boba.network"),
    /**
     * BSC测试网
     */
    TEST_BSC(97, "https://data-seed-prebsc-1-s1.binance.org:8545");
    private Integer chainId;
    private String url;
    private Web3j web3j;

    Web3Net(Integer chainId, String url) {
        this.chainId = chainId;
        this.url = url;
        this.web3j = Web3j.build(new HttpService(this.getUrl()));
    }

    public Integer getChainId() {
        return chainId;
    }

    public String getUrl() {
        return url;
    }

    public Web3j getWeb3j() {
        return web3j;
    }
}
