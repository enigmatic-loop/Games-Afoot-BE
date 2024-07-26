package com.GamesAfoot.GamesAfoot.repository;

import com.GamesAfoot.GamesAfoot.model.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuntRepository extends JpaRepository<Hunt, Long> {
}
