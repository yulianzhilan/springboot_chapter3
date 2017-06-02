package cn.janescott.service;

import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by scott on 2017/6/2.
 */
@Service
public class PagesService<T> {
    @Autowired
    private Session session;

    public Page<T> findAll(Class<T> clazz, Pageable pageable, Filters filters){
        Collection data = this.session.loadAll();
    }
}
