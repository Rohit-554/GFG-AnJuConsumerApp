package in.jadu.anjuconsumerapp.contract.contract_artifacts.javaWrappers.in.jadu.anjuconsumerapp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class OrderProductContract_sol_ProductOrderContract extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b506112438061001d5f395ff3fe608060405260043610610079575f3560e01c80637acc0b201161004c5780637acc0b2014610102578063ac511e2e14610136578063d6d0be9f14610162578063e0f6ef8714610195575f80fd5b80631c96fb1f1461007d5780632b3fec4a1461009e5780633a0d1033146100bd5780635e77e192146100d0575b5f80fd5b348015610088575f80fd5b5061009c610097366004610b06565b6101aa565b005b3480156100a9575f80fd5b5061009c6100b8366004610b38565b610354565b61009c6100cb366004610de0565b61043b565b3480156100db575f80fd5b506100ef6100ea366004610ecb565b610738565b6040519081526020015b60405180910390f35b34801561010d575f80fd5b5061012161011c366004610b06565b610763565b6040516100f999989796959493929190610f36565b348015610141575f80fd5b50610155610150366004610faa565b6108cf565b6040516100f99190610fca565b34801561016d575f80fd5b5061018161017c366004610b06565b610938565b6040516100f998979695949392919061100d565b3480156101a0575f80fd5b506100ef60015481565b5f818152602081905260409020600301546001600160a01b031633146102315760405162461bcd60e51b815260206004820152603160248201527f4f6e6c79207468652062757965722063616e206d61726b207468652070726f646044820152703ab1ba1030b9903232b634bb32b932b21760791b60648201526084015b60405180910390fd5b5f8181526020819052604090206004810154600160a01b900460ff16156102aa5760405162461bcd60e51b815260206004820152602760248201527f5468652070726f647563742068617320616c7265616479206265656e2064656c60448201526634bb32b932b21760c91b6064820152608401610228565b60048101805460ff60a01b198116600160a01b1790915560028201546040516001600160a01b0390921691829082156108fc029083905f818181858888f193505050501580156102fc573d5f803e3d5ffd5b506004830154600160a01b900460ff1661034e5760038301546040516001600160a01b0390911690819083156108fc029084905f818181858888f1935050505015801561034b573d5f803e3d5ffd5b50505b50505050565b60015482111561039b5760405162461bcd60e51b815260206004820152601260248201527124b73b30b634b210383937b23ab1ba24b21760711b6044820152606401610228565b5f82815260208190526040902060038101546001600160a01b031633146104175760405162461bcd60e51b815260206004820152602a60248201527f4f6e6c79207468652062757965722063616e2061646420746865206661726d65604482015269391030b2323932b9b99760b11b6064820152608401610228565b60040180546001600160a01b0319166001600160a01b039290921691909117905550565b8451865114801561044d575083518551145b801561045a575082518451145b61049f5760405162461bcd60e51b815260206004820152601660248201527524b73b30b634b21034b7383aba103632b733ba34399760511b6044820152606401610228565b5f34116104e25760405162461bcd60e51b81526020600482015260116024820152702830bcb6b2b73a103932b8bab4b932b21760791b6044820152606401610228565b5f42815b88518110156106e55760018054905f6104fe8361108a565b91905055506040518061012001604052806001548152602001898381518110610529576105296110a2565b60200260200101518152602001888381518110610548576105486110a2565b60200260200101518152602001336001600160a01b03168152602001878381518110610576576105766110a2565b60200260200101516001600160a01b031681526020015f151581526020018681526020018381526020018583815181106105b2576105b26110a2565b60209081029190910181015160ff16909152600180545f908152808352604090208351815591830151908201906105e99082611138565b506040820151600282015560608201516003820180546001600160a01b0319166001600160a01b03928316179055608083015160048301805460a0860151929093166001600160a81b031990931692909217600160a01b9115159190910217905560c0820151600582019061065e9082611138565b5060e08201516006820155610100909101516007909101805460ff191660ff909216919091179055335f9081526002602090815260408220600180548254918201835591845291909220015586518790829081106106be576106be6110a2565b6020026020010151836106d191906111f4565b9250806106dd8161108a565b9150506104e6565b508134101561072e5760405162461bcd60e51b815260206004820152601560248201527424b739bab33334b1b4b2b73a103830bcb6b2b73a1760591b6044820152606401610228565b5050505050505050565b6002602052815f5260405f208181548110610751575f80fd5b905f5260205f20015f91509150505481565b5f6020819052908152604090208054600182018054919291610784906110b6565b80601f01602080910402602001604051908101604052809291908181526020018280546107b0906110b6565b80156107fb5780601f106107d2576101008083540402835291602001916107fb565b820191905f5260205f20905b8154815290600101906020018083116107de57829003601f168201915b50505050600283015460038401546004850154600586018054959693956001600160a01b03938416955092821693600160a01b90920460ff169261083e906110b6565b80601f016020809104026020016040519081016040528092919081815260200182805461086a906110b6565b80156108b55780601f1061088c576101008083540402835291602001916108b5565b820191905f5260205f20905b81548152906001019060200180831161089857829003601f168201915b50505050600683015460079093015491929160ff16905089565b6001600160a01b0381165f9081526002602090815260409182902080548351818402810184019094528084526060939283018282801561092c57602002820191905f5260205f20905b815481526020019060010190808311610918575b50505050509050919050565b5f60605f805f8060605f6001548911156109895760405162461bcd60e51b815260206004820152601260248201527124b73b30b634b210383937b23ab1ba24b21760711b6044820152606401610228565b5f898152602081905260409020805460028201546003830154600484015460068501546001860180549094936001600160a01b039081169390811692600160a01b90910460ff169160058901919087906109e2906110b6565b80601f0160208091040260200160405190810160405280929190818152602001828054610a0e906110b6565b8015610a595780601f10610a3057610100808354040283529160200191610a59565b820191905f5260205f20905b815481529060010190602001808311610a3c57829003601f168201915b50505050509650818054610a6c906110b6565b80601f0160208091040260200160405190810160405280929190818152602001828054610a98906110b6565b8015610ae35780601f10610aba57610100808354040283529160200191610ae3565b820191905f5260205f20905b815481529060010190602001808311610ac657829003601f168201915b505050505091509850985098509850985098509850985050919395975091939597565b5f60208284031215610b16575f80fd5b5035919050565b80356001600160a01b0381168114610b33575f80fd5b919050565b5f8060408385031215610b49575f80fd5b82359150610b5960208401610b1d565b90509250929050565b634e487b7160e01b5f52604160045260245ffd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610b9f57610b9f610b62565b604052919050565b5f67ffffffffffffffff821115610bc057610bc0610b62565b5060051b60200190565b5f82601f830112610bd9575f80fd5b81356020610bee610be983610ba7565b610b76565b82815260059290921b84018101918181019086841115610c0c575f80fd5b8286015b84811015610c275780358352918301918301610c10565b509695505050505050565b5f82601f830112610c41575f80fd5b813567ffffffffffffffff811115610c5b57610c5b610b62565b610c6e601f8201601f1916602001610b76565b818152846020838601011115610c82575f80fd5b816020850160208301375f918101602001919091529392505050565b5f82601f830112610cad575f80fd5b81356020610cbd610be983610ba7565b82815260059290921b84018101918181019086841115610cdb575f80fd5b8286015b84811015610c2757803567ffffffffffffffff811115610cfe575f8081fd5b610d0c8986838b0101610c32565b845250918301918301610cdf565b5f82601f830112610d29575f80fd5b81356020610d39610be983610ba7565b82815260059290921b84018101918181019086841115610d57575f80fd5b8286015b84811015610c2757610d6c81610b1d565b8352918301918301610d5b565b5f82601f830112610d88575f80fd5b81356020610d98610be983610ba7565b82815260059290921b84018101918181019086841115610db6575f80fd5b8286015b84811015610c2757803560ff81168114610dd3575f8081fd5b8352918301918301610dba565b5f805f805f8060c08789031215610df5575f80fd5b863567ffffffffffffffff80821115610e0c575f80fd5b610e188a838b01610bca565b97506020890135915080821115610e2d575f80fd5b610e398a838b01610c9e565b96506040890135915080821115610e4e575f80fd5b610e5a8a838b01610bca565b95506060890135915080821115610e6f575f80fd5b610e7b8a838b01610d1a565b94506080890135915080821115610e90575f80fd5b610e9c8a838b01610c32565b935060a0890135915080821115610eb1575f80fd5b50610ebe89828a01610d79565b9150509295509295509295565b5f8060408385031215610edc575f80fd5b610ee583610b1d565b946020939093013593505050565b5f81518084525f5b81811015610f1757602081850181015186830182015201610efb565b505f602082860101526020601f19601f83011685010191505092915050565b5f6101208b8352806020840152610f4f8184018c610ef3565b604084018b90526001600160a01b038a811660608601528916608085015287151560a085015283810360c08501529050610f898187610ef3565b9150508360e083015260ff83166101008301529a9950505050505050505050565b5f60208284031215610fba575f80fd5b610fc382610b1d565b9392505050565b602080825282518282018190525f9190848201906040850190845b8181101561100157835183529284019291840191600101610fe5565b50909695505050505050565b5f6101008a83528060208401526110268184018b610ef3565b604084018a90526001600160a01b0389811660608601528816608085015286151560a085015283810360c085015290506110608186610ef3565b9150508260e08301529998505050505050505050565b634e487b7160e01b5f52601160045260245ffd5b5f6001820161109b5761109b611076565b5060010190565b634e487b7160e01b5f52603260045260245ffd5b600181811c908216806110ca57607f821691505b6020821081036110e857634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115611133575f81815260208120601f850160051c810160208610156111145750805b601f850160051c820191505b8181101561034b57828155600101611120565b505050565b815167ffffffffffffffff81111561115257611152610b62565b6111668161116084546110b6565b846110ee565b602080601f831160018114611199575f84156111825750858301515b5f19600386901b1c1916600185901b17855561034b565b5f85815260208120601f198616915b828110156111c7578886015182559484019460019091019084016111a8565b50858210156111e457878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b8082018082111561120757611207611076565b9291505056fea264697066735822122026d4239851709082ed9fb3e0b1c863c561a87f76971d4866b5915c5dc163e70b64736f6c63430008140033";

    public static final String FUNC_ADDFARMERADDRESS = "addFarmerAddress";

    public static final String FUNC_DELIVERPRODUCT = "deliverProduct";

    public static final String FUNC_GETPRODUCTDATA = "getProductData";

    public static final String FUNC_GETSHOPPINGCART = "getShoppingCart";

    public static final String FUNC_ORDERPRODUCT = "orderProduct";

    public static final String FUNC_PRODUCTCOUNT = "productCount";

    public static final String FUNC_PRODUCTS = "products";

    public static final String FUNC_SHOPPINGCART = "shoppingCart";

    @Deprecated
    protected OrderProductContract_sol_ProductOrderContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OrderProductContract_sol_ProductOrderContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OrderProductContract_sol_ProductOrderContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OrderProductContract_sol_ProductOrderContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addFarmerAddress(BigInteger _productId, String _farmer) {
        final Function function = new Function(
                FUNC_ADDFARMERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_productId), 
                new org.web3j.abi.datatypes.Address(160, _farmer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deliverProduct(BigInteger _productId) {
        final Function function = new Function(
                FUNC_DELIVERPRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_productId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple8<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger>> getProductData(BigInteger _productId) {
        final Function function = new Function(FUNC_GETPRODUCTDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_productId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple8<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger>>(function,
                new Callable<Tuple8<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger>>() {
                    @Override
                    public Tuple8<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getShoppingCart(String _buyer) {
        final Function function = new Function(FUNC_GETSHOPPINGCART, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _buyer)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> orderProduct(List<BigInteger> _productIds, List<String> _names, List<BigInteger> _quantities, List<String> _farmers, String _deliveryLocation, List<BigInteger> _prices, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ORDERPRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_productIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_names, org.web3j.abi.datatypes.Utf8String.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_quantities, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_farmers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.Utf8String(_deliveryLocation), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(_prices, org.web3j.abi.datatypes.generated.Uint8.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> productCount() {
        final Function function = new Function(FUNC_PRODUCTCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger, BigInteger>> products(BigInteger param0) {
        final Function function = new Function(FUNC_PRODUCTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
        return new RemoteFunctionCall<Tuple9<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple9<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple9<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, String, BigInteger, String, String, Boolean, String, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> shoppingCart(String param0, BigInteger param1) {
        final Function function = new Function(FUNC_SHOPPINGCART, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static OrderProductContract_sol_ProductOrderContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OrderProductContract_sol_ProductOrderContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OrderProductContract_sol_ProductOrderContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OrderProductContract_sol_ProductOrderContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OrderProductContract_sol_ProductOrderContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OrderProductContract_sol_ProductOrderContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OrderProductContract_sol_ProductOrderContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OrderProductContract_sol_ProductOrderContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OrderProductContract_sol_ProductOrderContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OrderProductContract_sol_ProductOrderContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OrderProductContract_sol_ProductOrderContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OrderProductContract_sol_ProductOrderContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<OrderProductContract_sol_ProductOrderContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OrderProductContract_sol_ProductOrderContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OrderProductContract_sol_ProductOrderContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OrderProductContract_sol_ProductOrderContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
