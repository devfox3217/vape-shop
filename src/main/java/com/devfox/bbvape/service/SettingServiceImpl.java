package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Setting;
import com.devfox.bbvape.repo.SettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    public Setting saveSetting(Setting setting) {
        setting.setId(1);
        return settingRepository.save(setting);
    }

    @Override
    public Setting loadSetting() {
        return settingRepository.findById(1);
    }
}
