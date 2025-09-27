package com.silvics.garden.repository;

import com.silvics.garden.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {

  Optional<Crop> findByNameIgnoreCase(String name);

  @Query("SELECT DISTINCT c FROM Crop c LEFT JOIN FETCH c.companions")
  List<Crop> findAllWithCompanions();

  @Query("SELECT DISTINCT c FROM Crop c LEFT JOIN FETCH c.antagonists")
  List<Crop> findAllWithAntagonists();
}