package com.dbmatos.beerstore.resources;

import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dbmatos.beerstore.model.Beer;
import com.dbmatos.beerstore.repository.BeerRepository;
import com.dbmatos.beerstore.service.BeerService;

@RestController
@RequestMapping("/beers")
public class BeerResource {
	
	@Autowired
	private BeerRepository repository;
	
	@Autowired
	private BeerService service;
	
	@GetMapping
	public List<Beer> getAll(){
		return this.repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Beer create(@Valid @RequestBody Beer beer) {
		return this.service.save(beer);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Beer update(@PathVariable Long id, @Valid @RequestBody Beer beer) {
		beer.setId(id);
		return this.service.save(beer);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id, ServletResponse res) {
		HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("id", String.valueOf(id));
		this.service.delete(id);
	}

}
