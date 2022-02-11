package com.momsitter.domain.repository;

import com.momsitter.domain.SitterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterInfoRepository extends JpaRepository<SitterInfo, Long> {
}
