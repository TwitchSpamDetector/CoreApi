package com.twitchspamdetector.coreapi.repository;

import com.twitchspamdetector.coreapi.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {
}
