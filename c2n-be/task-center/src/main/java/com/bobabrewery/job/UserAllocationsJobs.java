package com.bobabrewery.job;

import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.repo.common.domain.model.Project;
import com.bobabrewery.repo.common.domain.model.UserRegisterProduct;
import com.bobabrewery.repo.common.mapper.ProductContractMapper;
import com.bobabrewery.repo.common.mapper.UserRegisterProductMapper;
import com.bobabrewery.util.CredentialsUtils;
import com.bobabrewery.util.StakeContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class UserAllocationsJobs {

    @Resource
    private ProductContractMapper productContractMapper;

    @Resource
    private UserRegisterProductMapper userRegisterProductMapper;

    private static final ContractGasProvider gas = new DefaultGasProvider();

    @Value("${staking.bsc}")
    private String bscContract;

    @Value("${staking.boba}")
    private String bobaContract;

    private static final BigDecimal e18 = new BigDecimal(1000000000000000000L);

    @Scheduled(cron = "0 0/10 * * * ?")
    public void cron() throws Exception {
        long start = System.currentTimeMillis();
        log.info("start at {}", start);
        // 查出已经开始的项目
        List<Project> projects = productContractMapper.selectRegister();
        for (Project project : projects) {
            Web3j web3j = Web3Net.valueOfChainId(project.getChainId());
            StakeContract allocationStaking = StakeContract.load(selectStakingContract(project.getChainId()), web3j, CredentialsUtils.getCredentials(), gas);
            Integer projectId = project.getId();
            BigDecimal maxParticipation = new BigDecimal(project.getMaxParticipation());
            List<UserRegisterProduct> userRegisterProducts = userRegisterProductMapper.selectByPrductId(projectId);

            log.info("开始拉取项目{}用户的Staking值,当前项目注册人数:{}", projectId, userRegisterProducts.size());
            for (UserRegisterProduct userRegisterProduct : userRegisterProducts) {
                String accountId = userRegisterProduct.getAccountId();
                // 获得用户质押的数量
                BigInteger send = allocationStaking.deposited(BigInteger.valueOf(0), accountId).send();
                userRegisterProduct.setStakingAmount(send.toString());
                userRegisterProductMapper.updateByEntity(userRegisterProduct);
            }
            log.info("开始拉取项目{}用户的Allocation", projectId);
            // 经过权重计算后的总分配金额
            BigDecimal totalAmount = calcTotalAmount(userRegisterProducts);
            // 要出售的Token数量
            BigDecimal amountOfTokenToCell = new BigDecimal(project.getAmountOfTokensToSell());
            // Z = amountOfTokenToCell/totalAmount
            BigDecimal zValue = amountOfTokenToCell.divide(totalAmount, 18, RoundingMode.HALF_UP);
            log.info("经过权重计算后的总分配金额-A:{},要出售的Token数量-B:{},A/B:{}", totalAmount.divide(e18), amountOfTokenToCell.divide(e18), zValue);
            // 计算每个用户的分配额度
            calc(zValue, totalAmount, userRegisterProducts, maxParticipation);
            for (UserRegisterProduct userRegisterProduct : userRegisterProducts) {
                userRegisterProductMapper.updateByEntity(userRegisterProduct);
            }
//            for (UserRegisterProduct userRegisterProduct : userRegisterProducts) {
//                log.info("user:{},{},{}", userRegisterProduct.getAccountId(),
//                        new BigDecimal(userRegisterProduct.getStakingAmount()).divide(e18),
//                        new BigDecimal(userRegisterProduct.getWinAmount()).divide(e18));
//            }
            BigDecimal collect = userRegisterProducts.stream().map(e -> {
                return new BigDecimal(e.getWinAmount());
            }).reduce(BigDecimal.ZERO, BigDecimal::add);
            log.info("用户分配额度相加校验: {}", collect.divide(e18));
            long end = System.currentTimeMillis();
            log.info("successful at {}, speed {}", end, end - start);
        }


    }


    private void calc(BigDecimal z, BigDecimal totalAmount, List<UserRegisterProduct> userlist, BigDecimal max) {

        BigDecimal restToken = BigDecimal.ZERO;
        BigDecimal wOverFower = BigDecimal.ZERO;

        for (UserRegisterProduct user : userlist) {

            String winAmountStr = user.getWinAmount();
            if (!StringUtils.hasLength(winAmountStr)) {
                winAmountStr = "0";
            }
            BigDecimal winAmount = new BigDecimal(winAmountStr);
            // WinAmount < max
            if (winAmount.compareTo(max) < 0) {
                // 用户stakingAmount
                BigDecimal stakingAmount = new BigDecimal(user.getStakingAmount());
                // z * (stakingAmount * 权重)
                BigDecimal amount = z.multiply(stakeLevel(stakingAmount));
                // WinAmount +=  z * (stakingAmount * 权重)
                user.setWinAmount(winAmount.add(amount).toString());
                BigDecimal newAmount = winAmount.add(amount);
                //winAmount.add(amount) > max
                if (newAmount.compareTo(max) > 0) {
                    restToken = restToken.add(newAmount.subtract(max));
                    wOverFower = wOverFower.subtract(stakeLevel(stakingAmount));
                    user.setWinAmount(max.toString());

                }

            }
        }

        if (restToken.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal add = totalAmount.add(wOverFower);
            if (add.compareTo(BigDecimal.ZERO) == 0) {
                return;
            }
            BigDecimal divide = restToken.divide(add, 10, RoundingMode.HALF_UP);
            calc(divide, add, userlist, max);
        }

    }

    private BigDecimal calcTotalAmount(List<UserRegisterProduct> userlist) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (UserRegisterProduct userRegisterProduct : userlist) {
            totalAmount = totalAmount.add(stakeLevel(new BigDecimal(userRegisterProduct.getStakingAmount())));
        }
        return totalAmount;
    }

    /**
     * 获取用户stake金额的权重 分配额度
     *
     * @param stakeAmount
     * @return
     */
    private BigDecimal stakeLevel(BigDecimal stakeAmount) {
        BigDecimal allocation = null;
        BigDecimal v5000 = new BigDecimal("5000").multiply(e18);
        BigDecimal v25000 = new BigDecimal("25000").multiply(e18);
        BigDecimal v50000 = new BigDecimal("50000").multiply(e18);
        BigDecimal v100000 = new BigDecimal("100000").multiply(e18);
        if (stakeAmount.compareTo(BigDecimal.ZERO) >= 0 && stakeAmount.compareTo(v5000) <= 0) {
            allocation = stakeAmount;
        } else if (stakeAmount.compareTo(v5000) > 0 && stakeAmount.compareTo(v25000) <= 0) {
            allocation = stakeAmount.multiply(new BigDecimal("2"));
        } else if (stakeAmount.compareTo(v25000) > 0 && stakeAmount.compareTo(v50000) <= 0) {
            allocation = stakeAmount.multiply(new BigDecimal("3"));
        } else if (stakeAmount.compareTo(v50000) > 0 && stakeAmount.compareTo(v100000) <= 0) {
            allocation = stakeAmount.multiply(new BigDecimal("4"));
        } else if (stakeAmount.compareTo(v100000) > 0) {
            allocation = stakeAmount.multiply(new BigDecimal("5"));
        }
        return allocation;
    }

    /**
     * 选择网络下的Staking合约地址。
     *
     * @return
     */
    private String selectStakingContract(Integer chainId) {
        switch (chainId) {
            case 288:
            case 28:
                return bobaContract;
            case 97:
            case 56:
                return bscContract;
            default:
                throw new RuntimeException("network的值异常");
        }
    }


}
