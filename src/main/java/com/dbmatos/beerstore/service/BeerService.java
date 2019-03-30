package com.dbmatos.beerstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dbmatos.beerstore.model.Beer;
import com.dbmatos.beerstore.repository.BeerRepository;
import com.dbmatos.beerstore.service.exceptions.BeerAlreadyExistException;
import com.dbmatos.beerstore.service.exceptions.EntityNotFoundException;

@Service
public class BeerService {
	
	
	private BeerRepository repository;
	
	public BeerService(BeerRepository repository) {
		this.repository = repository;
	}
	
	public Beer save(final Beer beer) {
		verifyIfBeerExists(beer);
		return this.repository.save(beer);
	}
	
	public void delete(Long id) {
		if(!this.repository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		this.repository.deleteById(id);
	}
	
	private void verifyIfBeerExists(final Beer beer) {
        Optional<Beer> beerByNameAndType = this.repository.findByNameAndType(beer.getName(), beer.getType());

        if (beerByNameAndType.isPresent() && (beer.isNew() || isUpdatingToADifferentBeer(beer, beerByNameAndType))) {
            throw new BeerAlreadyExistException();
        }
    }

    private boolean isUpdatingToADifferentBeer(Beer beer, Optional<Beer> beerByNameAndType) {
        return beer.alreadyExist() && !beerByNameAndType.get().equals(beer);
    }

}
