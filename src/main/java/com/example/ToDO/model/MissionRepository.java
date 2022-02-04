package com.example.ToDO.model;

import org.springframework.data.repository.CrudRepository;

public interface MissionRepository extends CrudRepository<Mission, Integer> {
    Mission findById(int id);

}
