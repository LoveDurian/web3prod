package com.bobabrewery.service.impl;

import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserInfo;
import com.bobabrewery.repo.common.mapper.UserInfoDao;
import com.bobabrewery.service.ReferralCodeService;
import com.bobabrewery.service.TelegramMessageService;
import com.bobabrewery.util.BotSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TelegramMessageServiceImpl implements TelegramMessageService {

    @Resource
    private BotSender botSender;
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private ReferralCodeService referralCodeService;

    @Override
    public void sendRegisterPlatformSuccess(UserInfo userInfo) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardButton joinGroup = new InlineKeyboardButton();
        joinGroup.setText("Telegram Community");
        joinGroup.setUrl("https://t.me/+6jNxyl-l96FhNDM5");
        InlineKeyboardButton joinChannel = new InlineKeyboardButton();
        joinChannel.setText("Official website");
        joinChannel.setUrl("https://bobabrewery.com/");
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(joinGroup);
        buttons.add(joinChannel);
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(buttons);
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup(list));
        sendMessage.setChatId(userInfo.getTgId());
        sendMessage.setText("\uD83C\uDF89Congratulations on successfully being as a Boba Brewery brewer!\uD83C\uDF89\n" + "\n" + "Dear friend, thank you for joining us as a brewer! Please join our live Telegram community for more project updates and cryptocurrency information! Let's brew together\uD83C\uDF7A\uD83C\uDF7A\uD83C\uDF7A\n" + "\n" + "\uD83D\uDCDD Introduction about Boba Brewery \uD83D\uDC49 \n" + "We are the FIRST fully decentralized fundraising platform in the Boba Network and we aim to provide a high-quality optimization on project fundraising. Fairness, safety and transparency are the top missions we seek to benefit all token holders with any size of investments.\n" + "\n" + "\uD83D\uDEA8WARNING\uD83D\uDEA8 \n" + "Please be careful of FAKE admin accounts and bots. Boba Brewery admins or bots will never DM you to ask for funds or anything.");
        botSender.execute(sendMessage);

    }

    @Override
    public void sendRegisterProjectSuccess(UserInfo userInfo, Integer projectId) throws TelegramApiException {
        if (userInfo != null && StringUtils.isNotBlank(userInfo.getTgId())) {
            String referralCode = referralCodeService.generateReferralCode(userInfo.getAccountId(), projectId);
            SendMessage sendMessage = new SendMessage();
            InlineKeyboardButton joinGroup = new InlineKeyboardButton();
            joinGroup.setText("Bobabrewery Community");
            joinGroup.setUrl("https://t.me/+6jNxyl-l96FhNDM5");
            InlineKeyboardButton joinChannel = new InlineKeyboardButton();
            joinChannel.setText("ShibuiDAO Community");
            joinChannel.setUrl("https://t.me/shibuidao");
            InlineKeyboardButton referralLink = new InlineKeyboardButton();
            referralLink.setText("Your Referral Link");
            referralLink.setUrl("https://bobabrewery.com/project?id=" + projectId + "&aff=" + referralCode);
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(joinGroup);
            buttons.add(joinChannel);
            buttons.add(referralLink);
            List<List<InlineKeyboardButton>> list = new ArrayList<>();
            list.add(buttons);
            sendMessage.setReplyMarkup(new InlineKeyboardMarkup(list));
            sendMessage.setChatId(userInfo.getTgId());
            sendMessage.setText("\uD83D\uDC4F You now successfully registered for ShibuiDAO IDO & Airdrop Campaign! \uD83D\uDC4F\n" +
                    "\n" +
                    "ShibuiDAO, a fully on-chain NFT marketplace moving trading outside of the hands of centralised platforms. In a truly user-centric fashion the marketplace features community controlled fees alongside staking-based fee sharing or staking-based fee reduction.\n" +
                    "\n" +
                    "NOTE: You can add your BRE(BOBA) amount in your staking to increase your allocation for this incredible project before the registration ends.\n" +
                    "\n" +
                    "WARNING: Please be careful of FAKE admin accounts and bots. Boba Brewery admins or bots will never DM you to ask for funds or anything");
            botSender.execute(sendMessage);
        } else {
            throw new CommonException(ReCode.USER_DID_NOT_REGISTER);
        }
    }

    @Override
    public void sendProjectParticipatedSuccess(String accountId, Integer projectId) throws TelegramApiException {
        UserInfo userInfo = userInfoDao.findByAccountId(accountId);
        if (userInfo != null && userInfo.getTgId() != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(userInfo.getTgId());
            sendMessage.setText("\uD83D\uDC4FYou now successfully participated ShibuiDAO IDO & Airdrop Campaign!\uD83D\uDC4F \n" +
                    "\nPlease kindly wait for distribution period of the campaign and claim your IDO Shibui Tokens on Boba Brewery Shibui project page \uD83C\uDF89 \n" +
                    "\nWARNING: Please be careful of FAKE admin accounts and bots. Boba Brewery admins or bots will never DM you to ask for funds or anything.");
            botSender.execute(sendMessage);
        } else {
            throw new CommonException(ReCode.USER_DID_NOT_REGISTER);
        }
    }
}
