package com.example.GameVerse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.GameVerse.model.GamesModel;

public interface GamesRepository extends JpaRepository <GamesModel, Long> {
	
	 public List<GamesModel> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	 public List<GamesModel> findAllByCategoriaContainingIgnoreCase(@Param("categoria")String categoria);
	 

}
