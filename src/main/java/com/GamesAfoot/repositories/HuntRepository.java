package com.GamesAfoot.repositories;

import com.GamesAfoot.models.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuntRepository extends JpaRepository<Hunt, Long> {
}
