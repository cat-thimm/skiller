package com.anderscore.skiller.project_occupancy;

import com.anderscore.skiller.coworker.Coworker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectOccupancyRepository extends JpaRepository<ProjectOccupancy, ProjectOccupancyId> {
    Optional<ProjectOccupancy> findByCoworker(Coworker coworker);


}