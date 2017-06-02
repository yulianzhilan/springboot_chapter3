package cn.janescott.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by scott on 2017/6/2.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
/**
 * 表明这个类是一个关系实体，
 * 并用type指定了关系的类型，
 * 其中@StartNode指定起始节点的实体
 * ,@EndNode指定终止节点的实体，
 * 这说明了图中一条有向边的起点和终点的定义。
 */
@RelationshipEntity(type = "扮演")
public class Role {
    @GraphId
    private Long id;

    private String name;

    /**
     * 指定起始节点的实体
     */
    @StartNode
    private Actor actor;

    /**
     * 指定终止节点的实体
     */
    @EndNode
    private Movie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Role() {
    }

    public Role(String name, Actor actor, Movie movie) {
        this.name = name;
        this.actor = actor;
        this.movie = movie;
    }
}
