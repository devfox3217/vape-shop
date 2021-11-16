package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Setting;

public interface SettingService {
    Setting saveSetting(Setting setting);

    Setting loadSetting();
}
