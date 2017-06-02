package cn.janescott.repository;

import cn.janescott.domain.Actor;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by scott on 2017/6/2.
 */
@Repository
public interface ActorRepository extends GraphRepository<Actor> {
}
