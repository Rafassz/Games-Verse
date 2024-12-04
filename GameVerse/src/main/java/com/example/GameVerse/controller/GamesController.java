package com.example.GameVerse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.GameVerse.model.CategoriaModel;
import com.example.GameVerse.model.GamesModel;
import com.example.GameVerse.repository.CategoriaRepository;
import com.example.GameVerse.repository.GamesRepository;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/gameverse")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GamesController {

	@Autowired
	private GamesRepository gamesRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<GamesModel>> getAll() {
		return ResponseEntity.ok(gamesRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<GamesModel> getById(@PathVariable Long id) {
		return gamesRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/jogos/{nome}")
    public ResponseEntity<List<GamesModel>> getByNome(@PathVariable String nome){
        return ResponseEntity.ok(gamesRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<GamesModel> post(@Valid @RequestBody GamesModel gamesModel){
		if (categoriaRepository.existsById(gamesModel.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(gamesRepository.save(gamesModel));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);

	}
	@PutMapping
	public ResponseEntity<GamesModel> put(@Valid @RequestBody GamesModel gamesModel) {
		if (gamesRepository.existsById(gamesModel.getId())) {
			
			if (gamesRepository.existsById(gamesModel.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(gamesRepository.save(gamesModel));

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não Existe", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional <GamesModel> gamesModel = gamesRepository.findById(id);
		
		if(gamesModel.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		gamesRepository.deleteById(id);
	}
	
}
