package com.geidea.ams.services;

import com.geidea.ams.utils.GeideaCodes;

public interface IntegrityVerifyService {
    GeideaCodes verifyToken(String token, String packageName, String deviceId);
}