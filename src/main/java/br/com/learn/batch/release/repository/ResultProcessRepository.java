package br.com.learn.batch.release.repository;

import br.com.learn.batch.release.domain.ResultProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultProcessRepository extends JpaRepository<ResultProcess, Integer> {
				@Query("select count(r) from ResultProcess r")
				long countFirstBy();

}