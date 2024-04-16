package com.bobabrewery.listener;

import com.bobabrewery.common.constant.EnvConstant;
import com.bobabrewery.enums.Web3Net;
import com.bobabrewery.repo.common.mapper.SaleTokenIdMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.DefaultFunctionReturnDecoder;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class SacredRealmListener {

    private SaleTokenIdMapper saleTokenIdMapper;


    @Value("${spring.profiles.active}")
    private String env;

    public SacredRealmListener(SaleTokenIdMapper saleTokenIdMapper) {
        this.saleTokenIdMapper = saleTokenIdMapper;
    }

    @PostConstruct
    public void init() {
        Web3j web3j = Web3Net.TEST_RINKEBY.getWeb3j();
        String contract = "0xAf9039b72053Ac79E932C7D3a21942164e562609";
        if (EnvConstant.PROD.equals(env)) {
            // bsc
            web3j = Web3Net.MAIN_BSC.getWeb3j();
            contract = "0x22c7F84310D133E8B4e1FB0EE653ce786ea70089";
        }
        log.info("SacredRealmListener inited {}: contract: {}", env, contract);
        EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, "0xAf9039b72053Ac79E932C7D3a21942164e562609");

        Event eventHash = new Event("Minted", Arrays.asList(
                new TypeReference<Address>() {
                },
                new TypeReference<DynamicArray<Uint256>>() {
                },
                new TypeReference<Uint256>() {
                }
        ));

        String event = EventEncoder.encode(eventHash);
        filter.addSingleTopic(event);
        Web3j finalWeb3j = web3j;
        finalWeb3j.ethLogFlowable(filter).subscribe(
                web3log -> {
                    String data = web3log.getData();
                    //decode info
                    DefaultFunctionReturnDecoder decoder = new DefaultFunctionReturnDecoder();
                    String eventData = Numeric.cleanHexPrefix(data);
                    int len = eventData.length()/64;
                    List<Long> tokenIds = new ArrayList<>();
                    BigInteger price = BigInteger.ZERO;
                    BigInteger number = BigInteger.ZERO;
                    for (int i = 0; i < len; i++) {
                        String substring = eventData.substring(64*i, 64*(i+1));
                        Uint256 type = (Uint256) decoder.decodeEventParameter(substring, new TypeReference<Uint256>() {});
                        if (i == 1){
                            price = type.getValue();
                        }else if (i == 2 ){
                            number = type.getValue();
                        }

                        if(i>=3){
                            tokenIds.add(type.getValue().longValue());
                        }
                    }
                    // decode user
                    Address address = (Address) decoder.decodeEventParameter(web3log.getTopics().get(1), new TypeReference<Address>() {});
                    List<Integer> updates = new ArrayList<>();
                    for (Long tokenId : tokenIds) {
                        int update = saleTokenIdMapper.update(tokenId, web3log.getTransactionHash(), address.getValue(), 3);
                        updates.add(update);
                    }
                    log.info("[SacredRealm] success user:{},price:{},num:{},tokenIds:{},updates:{}",address.getValue(),price,number,tokenIds,updates);
                },
                error -> {
                    log.error(error.getMessage());
                }
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // get txId Info
        Web3j web3j = Web3Net.MAIN_BSC.getWeb3j();
        String txId = "0x111";
        EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(txId).sendAsync().get();
        TransactionReceipt result = ethGetTransactionReceipt.getResult();
        String from = result.getFrom();
        String status = result.getStatus();


        // filter event
        Event eventHash = new Event("Minted", Arrays.asList(
                new TypeReference<Address>() {
                },
                new TypeReference<DynamicArray<Uint256>>() {
                },
                new TypeReference<Uint256>() {
                }
        ));
        String event = EventEncoder.encode(eventHash);
        System.out.println(event);


        // decode data
        DefaultFunctionReturnDecoder decoder = new DefaultFunctionReturnDecoder();

        String s = "0x00000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000056bc75e2d6310000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000006";

        String eventData = Numeric.cleanHexPrefix(s);
        Integer len = eventData.length()/64;
        for (int i = 0; i < len; i++) {
            String substring = eventData.substring(64*i, 64*(i+1));
            System.out.println(substring);
            Type type = decoder.decodeEventParameter(substring, new TypeReference<Uint256>() {
            });
            System.out.println(type.getValue());
        }

        System.out.println("------");

        // decode user
        Address address = (Address) decoder.decodeEventParameter("0x000000000000000000000000615fdc569f5ff6fc832d5968f73f917f13471200", new TypeReference<Address>() {});
        System.out.println(address.getValue());
    }

}
