package com.GamesAfoot.GamesAfoot.repository;

import com.GamesAfoot.GamesAfoot.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
