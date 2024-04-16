import { useAppSelector } from "@src/redux/hooks";
import { Contract, providers } from "ethers";
import AirdropAbi from '@src/util/abi/Airdrop.json'

const AirdropAddress = '0x3ac7ae3C6D56fd49A6B2cD74C69C9B0c24819681'

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