package com.GamesAfoot.repository;

import com.GamesAfoot.model.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuntRepository extends JpaRepository<Hunt, Long> {
}
