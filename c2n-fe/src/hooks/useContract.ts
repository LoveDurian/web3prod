import { useAppSelector } from "@src/redux/hooks";
import { Contract, providers } from "ethers";
import AirdropAbi from '@src/util/abi/Airdrop.json'

const AirdropAddress = '0x0F789Ab9b01DDDfFd44b408BCa4910a93907F119'

export const useAirdropContract = () => {
  const chain = useAppSelector(state => state.wallet.chain);
  const signer = useAppSelector(state => state.contract.signer);
  const walletAddress = useAppSelector(state => state.contract.walletAddress);

  const viewProvider = new providers.JsonRpcProvider(chain?.rpc[0]);
  if (!signer || !walletAddress) {
    console.log('no signer')
  }
  const airdropContract = new Contract(AirdropAddress, AirdropAbi.abi, signer);
  return airdropContract
}