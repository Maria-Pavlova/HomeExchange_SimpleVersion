package com.example.homeexchange_simpleversion.repositories;

import com.example.homeexchange_simpleversion.models.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
