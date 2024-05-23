require('dotenv').config();
require('@nomiclabs/hardhat-waffle')
require('@nomiclabs/hardhat-ethers')
require("@nomiclabs/hardhat-web3")
require('@openzeppelin/hardhat-upgrades')


/** @type import('hardhat/config').HardhatUserConfig */
module.exports = {
    networks: {
        local: {
            url: 'http://127.0.0.1:7545',
            // accounts: [process.env.DEPLOYER_PRIVATE_KEY]
            accounts: [process.env.DEPLOYER_PRIVATE_KEY]
        },
        sepolia: {
            // url: 'https://sepolia.infura.io/v3/4c7b9b83a323478185558e5756d7ccc5',
            // accounts: [process.env.PRIVATE_KEY]
            url: 'https://sepolia.infura.io/v3/0b4fc961ee56472aa5df866d96b6724f',
            accounts: [process.env.PRIVATE_KEY]
        },
    },
    solidity: {
        version: "0.6.12",
        settings: {
            optimizer: {
                enabled: true,
                runs: 200,
            },
        },
    },
};
