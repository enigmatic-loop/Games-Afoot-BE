package com.GamesAfoot.repositories;

import com.GamesAfoot.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
}
