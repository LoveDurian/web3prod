package com.bobabrewery.job;

import com.bobabrewery.domain.CryptoPrice;
import com.bobabrewery.enums.ProductStatusEnums;
import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.repo.common.domain.model.Project;
import com.bobabrewery.service.CryptoComparePriceApi;
import com.bobabrewery.service.CurrentPriceService;
import com.bobabrewery.service.IProjectService;
import com.bobabrewery.util.ContractMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.Web3j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chenkj
 * @Date 2022/1/11 2:48 下午
 */
@Slf4j
@Component
public class ProductStatusJob {

    @Resource
    private IProjectService projectService;
    @Resource
    private CurrentPriceService currentPriceService;

    @Resource
    private CryptoComparePriceApi cryptoComparePriceApi;

    /**
     * 更新产品状态
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void updateProductStatus() {
        List<Project> projects = projectService.selectNotEndProduct();
        if (!CollectionUtils.isEmpty(projects)) {
            Date now = new Date();
            projects.forEach(product -> {
                Integer oldStatus = product.getStatus();
                Integer status = null;
                Date unlockTime = product.getUnlockTime();
                Date saleEnd = product.getSaleEnd();
                Date saleStart = product.getSaleStart();
                Date registrationTimeEnds = product.getRegistrationTimeEnds();
                Date registrationTimeStarts = product.getRegistrationTimeStarts();

                if (now.after(unlockTime)) {
                    status = ProductStatusEnums.PRODUCT_END.getCode();
                } else if (now.after(saleEnd)) {
                    status = ProductStatusEnums.SALE_END.getCode();
                } else if (now.after(saleStart)) {
                    status = ProductStatusEnums.IN_SALE.getCode();
                } else if (now.after(registrationTimeEnds)) {
                    status = ProductStatusEnums.READY_SALE.getCode();
                } else if (now.after(registrationTimeStarts)) {
                    status = ProductStatusEnums.REGISTERING.getCode();
                }
                if (status != null && oldStatus >= 0 && !oldStatus.equals(status)) {
                    this.projectService.updateStatus(status, product.getId());
                    log.info("product id:{} , status changed old status:{} , new status: {}", product.getId(), oldStatus, status);
                }
            });
        }
    }

    /**
     * 从合约上读取项目信息
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateProduct() {
        List<Project> projects = projectService.selectNotEndProduct();
        boolean empty = CollectionUtils.isEmpty(projects);
        log.info("未结束项目总数量: {}", projects.size());
        if (empty) {
            return;
        }
        CryptoPrice eth = cryptoComparePriceApi.price("ETH");
        BigDecimal price1 = eth.getPrice();
        projects.forEach(projectObj -> {
            if (projectObj.getStatus() == -1) {
                return;
            }
            try {
                Web3j build = Web3Net.MAIN_BSC.getWeb3j();
                Project project = ContractMessageUtils.pullContractInfo(build, projectObj, price1);
                BigDecimal price = currentPriceService.getPrice(project.getTokenName());
                project.setCurrentPrice(price);
                boolean b = projectService.updateById(project);
                log.info("拉取上的合约:{},{}", projectObj.getSaleContractAddress(), b);
            } catch (Exception e) {
                log.error("拉取上的合约:{} 失败", projectObj.getSaleContractAddress(), e);
            }
        });
    }


    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateCurrentPrice() throws Exception {
        List<Project> projects = projectService.selectAllStopProject();
        int i = 0;
        for (Project project : projects) {
            BigDecimal price = currentPriceService.getPrice(project.getTokenName());
            project.setCurrentPrice(price);
            i += projectService.updateCurrentPriceByID(price, project.getId());
        }
        List<Integer> collect = projects.stream().map(Project::getId).collect(Collectors.toList());
        log.info("更新项目:{}的CurrentPrice成功,{}/{}", collect, i, projects.size());
    }
}
