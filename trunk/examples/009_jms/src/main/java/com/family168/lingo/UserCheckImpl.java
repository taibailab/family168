package com.family168.lingo;

import com.family168.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserCheckImpl implements UserCheck {
    protected Log logger = LogFactory.getLog(UserCheckImpl.class);

    public void asynGetResidual(User user, UserListener userListener) {
        logger.error("asynSendResidual start");

        try {
            Thread.sleep(5000); // 模拟系统繁忙
            userListener.onResult(user.toString());
            userListener.stop();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("asynSendResidual 调用错误");
        }

        logger.error("asynSendResidual end");
    }

    public User synGetResidual(User user) {
        logger.error("synGetResidual start");

        try {
            Thread.sleep(5000); // 模拟系统繁忙
            logger.error("synGetResidual end");

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("synGetResidual 调用错误");

            return null;
        }
    }
}
