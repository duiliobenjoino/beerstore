package com.dbmatos.beerstore.service;


import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbmatos.beerstore.model.Beer;
import com.dbmatos.beerstore.model.BeerType;
import com.dbmatos.beerstore.repository.BeerRepository;
import com.dbmatos.beerstore.service.exceptions.BeerAlreadyExistException;
import com.dbmatos.beerstore.service.exceptions.EntityNotFoundException;

public class BeerServiceTest {
	
	private BeerService service;
	
	@Mock
	private BeerRepository repositoryMocked;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		service = new BeerService(repositoryMocked);
	}
	
	@Test(expected = BeerAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exists() {
		Beer beerInDatabase = new Beer(10L, "Heineken", BeerType.LAGER, new BigDecimal("355"));
		when(this.repositoryMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(beerInDatabase));
		
		Beer newBeer = new Beer(null, "Heineken", BeerType.LAGER, new BigDecimal("355"));
		this.service.save(newBeer);
	}
	
	@Test
	public void should_create_new_beer() {
		Beer newBeer = new Beer(null, "Skol", BeerType.IPA, new BigDecimal("600"));
		
		Beer newBeerInDatabase = new Beer(11L, "Skol", BeerType.IPA, new BigDecimal("600"));
		when(repositoryMocked.save(newBeer)).thenReturn(newBeerInDatabase);
		
		Beer beerSaved = this.service.save(newBeer);
		
		assertThat(beerSaved.getId(),equalTo(11L));
		assertThat(beerSaved.getName(),equalTo("Skol"));
		assertThat(beerSaved.getType(),equalTo(BeerType.IPA));
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void should_deny_exclusion_of_beer_that_not_exist() {
		when(this.repositoryMocked.existsById(20L)).thenReturn(false);
		this.service.delete(20L);
	}

}
