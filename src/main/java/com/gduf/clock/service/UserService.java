package com.gduf.clock.service;

import com.gduf.clock.entity.UserInfo;
import com.gduf.clock.vo.DetailVO;

public interface UserService {
    /**
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    UserInfo userLogin(String encryptedData, String iv, String code);
    DetailVO userDetail(String openId);
}
