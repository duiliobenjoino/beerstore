package com.dbmatos.beerstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbmatos.beerstore.model.Beer;
import com.dbmatos.beerstore.model.BeerType;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long>{
	
	Optional<Beer> findByNameAndType(String name, BeerType type);

}
