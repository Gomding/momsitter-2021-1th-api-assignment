package com.momsitter.repository;

import com.momsitter.domain.ParentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentInfoRepository extends JpaRepository<ParentInfo, Long> {
}
