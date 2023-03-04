package com.example.homeexchange_simpleversion.repositories;

import com.example.homeexchange_simpleversion.models.entities.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

    List<Home> findAllByOwner_Username(String username);

    List<Home> findAllByIsPublishedTrue();
}
