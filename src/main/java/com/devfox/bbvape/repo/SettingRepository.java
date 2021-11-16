package com.devfox.bbvape.repo;

import com.devfox.bbvape.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Setting findById(long id);
}
