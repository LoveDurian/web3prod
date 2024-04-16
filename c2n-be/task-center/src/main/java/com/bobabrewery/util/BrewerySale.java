package com.bobabrewery.util;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple14;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class BrewerySale extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_ALLOCATIONSTAKINGCONTRACT = "allocationStakingContract";

    public static final String FUNC_CHECKPARTICIPATIONSIGNATURE = "checkParticipationSignature";

    public static final String FUNC_CHECKREGISTRATIONSIGNATURE = "checkRegistrationSignature";

    public static final String FUNC_DEPOSITTOKENS = "depositTokens";

    public static final String FUNC_EXTENDREGISTRATIONPERIOD = "extendRegistrationPeriod";

    public static final String FUNC_FACTORY = "factory";

    public static final String FUNC_GETNUMBEROFREGISTEREDUSERS = "getNumberOfRegisteredUsers";

    public static final String FUNC_GETPARTICIPATION = "getParticipation";

    public static final String FUNC_GETPARTICIPATIONSIGNER = "getParticipationSigner";

    public static final String FUNC_GETVESTINGINFO = "getVestingInfo";

    public static final String FUNC_ISPARTICIPATED = "isParticipated";

    public static final String FUNC_ISREGISTERED = "isRegistered";

    public static final String FUNC_MAXVESTINGTIMESHIFT = "maxVestingTimeShift";

    public static final String FUNC_NUMBEROFPARTICIPANTS = "numberOfParticipants";

    public static final String FUNC_PARTICIPATE = "participate";

    public static final String FUNC_PORTIONVESTINGPRECISION = "portionVestingPrecision";

    public static final String FUNC_POSTPONESALE = "postponeSale";

    public static final String FUNC_REGISTERFORSALE = "registerForSale";

    public static final String FUNC_REGISTRATION = "registration";

    public static final String FUNC_SALE = "sale";

    public static final String FUNC_SETCAP = "setCap";

    public static final String FUNC_SETREGISTRATIONTIME = "setRegistrationTime";

    public static final String FUNC_SETSALEPARAMS = "setSaleParams";

    public static final String FUNC_SETSALESTART = "setSaleStart";

    public static final String FUNC_SETSALETOKEN = "setSaleToken";

    public static final String FUNC_SETVESTINGPARAMS = "setVestingParams";

    public static final String FUNC_SHIFTVESTINGUNLOCKINGTIMES = "shiftVestingUnlockingTimes";

    public static final String FUNC_UPDATETOKENPRICEINETH = "updateTokenPriceInETH";

    public static final String FUNC_USERTOPARTICIPATION = "userToParticipation";

    public static final String FUNC_VESTINGPERCENTPERPORTION = "vestingPercentPerPortion";

    public static final String FUNC_VESTINGPORTIONSUNLOCKTIME = "vestingPortionsUnlockTime";

    public static final String FUNC_WITHDRAWEARNINGS = "withdrawEarnings";

    public static final String FUNC_WITHDRAWEARNINGSANDLEFTOVER = "withdrawEarningsAndLeftover";

    public static final String FUNC_WITHDRAWLEFTOVER = "withdrawLeftover";

    public static final String FUNC_WITHDRAWMULTIPLEPORTIONS = "withdrawMultiplePortions";

    public static final String FUNC_WITHDRAWTOKENS = "withdrawTokens";

    public static final Event MAXPARTICIPATIONSET_EVENT = new Event("MaxParticipationSet",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }));
    ;

    public static final Event REGISTRATIONTIMESET_EVENT = new Event("RegistrationTimeSet",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event SALECREATED_EVENT = new Event("SaleCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event STARTTIMESET_EVENT = new Event("StartTimeSet",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }));
    ;

    public static final Event TOKENPRICESET_EVENT = new Event("TokenPriceSet",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }));
    ;

    public static final Event TOKENSSOLD_EVENT = new Event("TokensSold",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event TOKENSWITHDRAWN_EVENT = new Event("TokensWithdrawn",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    public static final Event USERREGISTERED_EVENT = new Event("UserRegistered",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }));
    ;

    @Deprecated
    protected BrewerySale(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BrewerySale(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BrewerySale(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BrewerySale(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<MaxParticipationSetEventResponse> getMaxParticipationSetEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(MAXPARTICIPATIONSET_EVENT, transactionReceipt);
        ArrayList<MaxParticipationSetEventResponse> responses = new ArrayList<MaxParticipationSetEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            MaxParticipationSetEventResponse typedResponse = new MaxParticipationSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.maxParticipation = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MaxParticipationSetEventResponse> maxParticipationSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MaxParticipationSetEventResponse>() {
            @Override
            public MaxParticipationSetEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(MAXPARTICIPATIONSET_EVENT, log);
                MaxParticipationSetEventResponse typedResponse = new MaxParticipationSetEventResponse();
                typedResponse.log = log;
                typedResponse.maxParticipation = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MaxParticipationSetEventResponse> maxParticipationSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MAXPARTICIPATIONSET_EVENT));
        return maxParticipationSetEventFlowable(filter);
    }

    public List<RegistrationTimeSetEventResponse> getRegistrationTimeSetEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTRATIONTIMESET_EVENT, transactionReceipt);
        ArrayList<RegistrationTimeSetEventResponse> responses = new ArrayList<RegistrationTimeSetEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RegistrationTimeSetEventResponse typedResponse = new RegistrationTimeSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.registrationTimeStarts = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.registrationTimeEnds = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegistrationTimeSetEventResponse> registrationTimeSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RegistrationTimeSetEventResponse>() {
            @Override
            public RegistrationTimeSetEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTRATIONTIMESET_EVENT, log);
                RegistrationTimeSetEventResponse typedResponse = new RegistrationTimeSetEventResponse();
                typedResponse.log = log;
                typedResponse.registrationTimeStarts = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.registrationTimeEnds = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegistrationTimeSetEventResponse> registrationTimeSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTRATIONTIMESET_EVENT));
        return registrationTimeSetEventFlowable(filter);
    }

    public List<SaleCreatedEventResponse> getSaleCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SALECREATED_EVENT, transactionReceipt);
        ArrayList<SaleCreatedEventResponse> responses = new ArrayList<SaleCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SaleCreatedEventResponse typedResponse = new SaleCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.saleOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokenPriceInETH = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amountOfTokensToSell = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.saleEnd = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SaleCreatedEventResponse> saleCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SaleCreatedEventResponse>() {
            @Override
            public SaleCreatedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SALECREATED_EVENT, log);
                SaleCreatedEventResponse typedResponse = new SaleCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.saleOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tokenPriceInETH = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amountOfTokensToSell = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.saleEnd = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SaleCreatedEventResponse> saleCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SALECREATED_EVENT));
        return saleCreatedEventFlowable(filter);
    }

    public List<StartTimeSetEventResponse> getStartTimeSetEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(STARTTIMESET_EVENT, transactionReceipt);
        ArrayList<StartTimeSetEventResponse> responses = new ArrayList<StartTimeSetEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            StartTimeSetEventResponse typedResponse = new StartTimeSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StartTimeSetEventResponse> startTimeSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StartTimeSetEventResponse>() {
            @Override
            public StartTimeSetEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(STARTTIMESET_EVENT, log);
                StartTimeSetEventResponse typedResponse = new StartTimeSetEventResponse();
                typedResponse.log = log;
                typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StartTimeSetEventResponse> startTimeSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STARTTIMESET_EVENT));
        return startTimeSetEventFlowable(filter);
    }

    public List<TokenPriceSetEventResponse> getTokenPriceSetEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENPRICESET_EVENT, transactionReceipt);
        ArrayList<TokenPriceSetEventResponse> responses = new ArrayList<TokenPriceSetEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TokenPriceSetEventResponse typedResponse = new TokenPriceSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newPrice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenPriceSetEventResponse> tokenPriceSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokenPriceSetEventResponse>() {
            @Override
            public TokenPriceSetEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENPRICESET_EVENT, log);
                TokenPriceSetEventResponse typedResponse = new TokenPriceSetEventResponse();
                typedResponse.log = log;
                typedResponse.newPrice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenPriceSetEventResponse> tokenPriceSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENPRICESET_EVENT));
        return tokenPriceSetEventFlowable(filter);
    }

    public List<TokensSoldEventResponse> getTokensSoldEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENSSOLD_EVENT, transactionReceipt);
        ArrayList<TokensSoldEventResponse> responses = new ArrayList<TokensSoldEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TokensSoldEventResponse typedResponse = new TokensSoldEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokensSoldEventResponse> tokensSoldEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokensSoldEventResponse>() {
            @Override
            public TokensSoldEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENSSOLD_EVENT, log);
                TokensSoldEventResponse typedResponse = new TokensSoldEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokensSoldEventResponse> tokensSoldEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENSSOLD_EVENT));
        return tokensSoldEventFlowable(filter);
    }

    public List<TokensWithdrawnEventResponse> getTokensWithdrawnEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENSWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<TokensWithdrawnEventResponse> responses = new ArrayList<TokensWithdrawnEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TokensWithdrawnEventResponse typedResponse = new TokensWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokensWithdrawnEventResponse> tokensWithdrawnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokensWithdrawnEventResponse>() {
            @Override
            public TokensWithdrawnEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENSWITHDRAWN_EVENT, log);
                TokensWithdrawnEventResponse typedResponse = new TokensWithdrawnEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokensWithdrawnEventResponse> tokensWithdrawnEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENSWITHDRAWN_EVENT));
        return tokensWithdrawnEventFlowable(filter);
    }

    public List<UserRegisteredEventResponse> getUserRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(USERREGISTERED_EVENT, transactionReceipt);
        ArrayList<UserRegisteredEventResponse> responses = new ArrayList<UserRegisteredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UserRegisteredEventResponse typedResponse = new UserRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserRegisteredEventResponse> userRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UserRegisteredEventResponse>() {
            @Override
            public UserRegisteredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(USERREGISTERED_EVENT, log);
                UserRegisteredEventResponse typedResponse = new UserRegisteredEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserRegisteredEventResponse> userRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERREGISTERED_EVENT));
        return userRegisteredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> admin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> allocationStakingContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOCATIONSTAKINGCONTRACT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> checkParticipationSignature(byte[] signature, String user, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHECKPARTICIPATIONSIGNATURE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Address(160, user),
                        new Uint256(amount)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> checkRegistrationSignature(byte[] signature, String user) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHECKREGISTRATIONSIGNATURE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Address(160, user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> depositTokens() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITTOKENS,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> extendRegistrationPeriod(BigInteger timeToAdd) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXTENDREGISTRATIONPERIOD,
                Arrays.<Type>asList(new Uint256(timeToAdd)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> factory() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FACTORY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getNumberOfRegisteredUsers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMBEROFREGISTEREDUSERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, List<Boolean>>> getParticipation(String _user) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPARTICIPATION,
                Arrays.<Type>asList(new Address(160, _user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<DynamicArray<Bool>>() {
                }));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, List<Boolean>>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, List<Boolean>>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, List<Boolean>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, List<Boolean>>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                convertToNative((List<Bool>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<String> getParticipationSigner(byte[] signature, String user, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPARTICIPATIONSIGNER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Address(160, user),
                        new Uint256(amount)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple2<List<BigInteger>, List<BigInteger>>> getVestingInfo() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVESTINGINFO,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {
                }, new TypeReference<DynamicArray<Uint256>>() {
                }));
        return new RemoteFunctionCall<Tuple2<List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple2<List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple2<List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()),
                                convertToNative((List<Uint256>) results.get(1).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isParticipated(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISPARTICIPATED,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isRegistered(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISREGISTERED,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> maxVestingTimeShift() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAXVESTINGTIMESHIFT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> numberOfParticipants() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMBEROFPARTICIPANTS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> participate(byte[] signature, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PARTICIPATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> portionVestingPrecision() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PORTIONVESTINGPRECISION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> postponeSale(BigInteger timeToShift) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_POSTPONESALE,
                Arrays.<Type>asList(new Uint256(timeToShift)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerForSale(byte[] signature, BigInteger pid) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERFORSALE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(signature),
                        new Uint256(pid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> registration() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REGISTRATION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple14<String, Boolean, Boolean, Boolean, Boolean, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> sale() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SALE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Bool>() {
                }, new TypeReference<Bool>() {
                }, new TypeReference<Bool>() {
                }, new TypeReference<Bool>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteFunctionCall<Tuple14<String, Boolean, Boolean, Boolean, Boolean, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple14<String, Boolean, Boolean, Boolean, Boolean, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple14<String, Boolean, Boolean, Boolean, Boolean, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple14<String, Boolean, Boolean, Boolean, Boolean, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(),
                                (Boolean) results.get(1).getValue(),
                                (Boolean) results.get(2).getValue(),
                                (Boolean) results.get(3).getValue(),
                                (Boolean) results.get(4).getValue(),
                                (String) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue(),
                                (BigInteger) results.get(7).getValue(),
                                (BigInteger) results.get(8).getValue(),
                                (BigInteger) results.get(9).getValue(),
                                (BigInteger) results.get(10).getValue(),
                                (BigInteger) results.get(11).getValue(),
                                (BigInteger) results.get(12).getValue(),
                                (BigInteger) results.get(13).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setCap(BigInteger cap) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCAP,
                Arrays.<Type>asList(new Uint256(cap)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setRegistrationTime(BigInteger _registrationTimeStarts, BigInteger _registrationTimeEnds) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETREGISTRATIONTIME,
                Arrays.<Type>asList(new Uint256(_registrationTimeStarts),
                        new Uint256(_registrationTimeEnds)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSaleParams(String _token, String _saleOwner, BigInteger _tokenPriceInETH, BigInteger _amountOfTokensToSell, BigInteger _saleEnd, BigInteger _tokensUnlockTime, BigInteger _portionVestingPrecision, BigInteger _maxParticipation) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSALEPARAMS,
                Arrays.<Type>asList(new Address(160, _token),
                        new Address(160, _saleOwner),
                        new Uint256(_tokenPriceInETH),
                        new Uint256(_amountOfTokensToSell),
                        new Uint256(_saleEnd),
                        new Uint256(_tokensUnlockTime),
                        new Uint256(_portionVestingPrecision),
                        new Uint256(_maxParticipation)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSaleStart(BigInteger starTime) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSALESTART,
                Arrays.<Type>asList(new Uint256(starTime)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSaleToken(String saleToken) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSALETOKEN,
                Arrays.<Type>asList(new Address(160, saleToken)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVestingParams(List<BigInteger> _unlockingTimes, List<BigInteger> _percents, BigInteger _maxVestingTimeShift) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETVESTINGPARAMS,
                Arrays.<Type>asList(new DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(_unlockingTimes, Uint256.class)),
                        new DynamicArray<Uint256>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(_percents, Uint256.class)),
                        new Uint256(_maxVestingTimeShift)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> shiftVestingUnlockingTimes(BigInteger timeToShift) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SHIFTVESTINGUNLOCKINGTIMES,
                Arrays.<Type>asList(new Uint256(timeToShift)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateTokenPriceInETH(BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATETOKENPRICEINETH,
                Arrays.<Type>asList(new Uint256(price)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> userToParticipation(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERTOPARTICIPATION,
                Arrays.<Type>asList(new Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> vestingPercentPerPortion(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VESTINGPERCENTPERPORTION,
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> vestingPortionsUnlockTime(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VESTINGPORTIONSUNLOCKTIME,
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawEarnings() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWEARNINGS,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawEarningsAndLeftover() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWEARNINGSANDLEFTOVER,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawLeftover() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWLEFTOVER,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawMultiplePortions(List<BigInteger> portionIds) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWMULTIPLEPORTIONS,
                Arrays.<Type>asList(new DynamicArray<Uint256>(
                        Uint256.class,
                        org.web3j.abi.Utils.typeMap(portionIds, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawTokens(BigInteger portionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWTOKENS,
                Arrays.<Type>asList(new Uint256(portionId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static BrewerySale load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BrewerySale(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BrewerySale load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BrewerySale(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BrewerySale load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BrewerySale(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BrewerySale load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BrewerySale(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class MaxParticipationSetEventResponse extends BaseEventResponse {
        public BigInteger maxParticipation;
    }

    public static class RegistrationTimeSetEventResponse extends BaseEventResponse {
        public BigInteger registrationTimeStarts;

        public BigInteger registrationTimeEnds;
    }

    public static class SaleCreatedEventResponse extends BaseEventResponse {
        public String saleOwner;

        public BigInteger tokenPriceInETH;

        public BigInteger amountOfTokensToSell;

        public BigInteger saleEnd;
    }

    public static class StartTimeSetEventResponse extends BaseEventResponse {
        public BigInteger startTime;
    }

    public static class TokenPriceSetEventResponse extends BaseEventResponse {
        public BigInteger newPrice;
    }

    public static class TokensSoldEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }

    public static class TokensWithdrawnEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }

    public static class UserRegisteredEventResponse extends BaseEventResponse {
        public String user;
    }
}
