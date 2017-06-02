package cn.janescott.repository;

import cn.janescott.domain.Movie;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by scott on 2017/6/2.
 */
@Repository
public interface MovieRepository extends GraphRepository<Movie> {
    Movie findByTitle(@Param("title")String title);
}
