package br.com.learn.batch.repository;

import br.com.learn.batch.domain.Registro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
}