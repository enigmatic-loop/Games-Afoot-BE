package com.GamesAfoot.GamesAfoot.repository;

import com.GamesAfoot.GamesAfoot.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByHuntId(Long huntId);
}
