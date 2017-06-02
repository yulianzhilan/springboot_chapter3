package cn.janescott.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by scott on 2017/6/2.
 * 创建电影节点实体建模
 */
/**
 * 防止查询数据时引发递归访问效应
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
/**
 * 标志这个类是一个节点实体
 */
@NodeEntity
public class Movie {
    /**
     * 定义了节点的一个唯一性标识，
     * 它将在创建节点时由系统自动生成，所以它是不可缺少的。
     */
    @GraphId
    private Long id;

    private String name;

    private String photo;
    /**
     * Neo4j还没有日期格式的数据类型，
     * 所以在读取日期类型的数据时，使用注解@DateTimeFormat进行格式转换，
     * 而在保存时，使用注解@DateLong将它转换成Long类型的数据进行存储。
     */
    @DateLong
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 表示List<Role>是一个关系列表，
     * 其中type设定了关系的类型，
     * direction设定这个关系的方向，
     * Relationship.INCOMING表示以这个节点为终点。
     */
    @Relationship(type = "扮演", direction = Relationship.INCOMING)
    List<Role> roles = new ArrayList<>();

    /**
     * 定义了增加一个关系的方法。
     * @param actor
     * @param name
     * @return
     */
    public Role addRole(Actor actor, String name){
        Role role = new Role(name, actor, this);
        this.roles.add(role);
        return role;
    }
}
