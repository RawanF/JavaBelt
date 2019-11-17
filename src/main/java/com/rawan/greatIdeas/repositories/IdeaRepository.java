package com.rawan.greatIdeas.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rawan.greatIdeas.models.Idea;
@Repository

public interface IdeaRepository extends CrudRepository<Idea, Long>{

	Iterable<Idea> findAllByOrderByLikesAsc();
	Iterable<Idea> findAllByOrderByLikesDesc();

//	List<Idea> findTopByOrderByNumberDesc();

}
