package com.GamesAfoot.repositories;

import com.GamesAfoot.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByHuntId(Long huntId);
}
