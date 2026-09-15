package com.twitchspamdetector.coreapi.repository;

import com.twitchspamdetector.coreapi.entity.VerdictRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerdictRecordRepository extends JpaRepository<VerdictRecordEntity, UUID> {

    Page<VerdictRecordEntity> findByChannelIdOrderByCreatedAtDesc(String channelId, Pageable pageable);
}
