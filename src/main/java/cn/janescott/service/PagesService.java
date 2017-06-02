package cn.janescott.service;

import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by scott on 2017/6/2.
 * 使用Class<T>传入调用的实体对象，
 * 使用Pageable传入页数设定和排序字段设定的参数，
 * 使用Filters传入查询的一些节点属性设定的参数。
 */
@Service
public class PagesService<T> {
    @Autowired
    private Session session;

    public Page<T> findAll(Class<T> clazz, Pageable pageable, Filters filters){
        Collection data = this.session.loadAll(clazz, filters, convert(pageable.getSort()), new Pagination(pageable.getPageNumber(), pageable.getPageSize()), 1);
        return updatePage(pageable, new ArrayList<>(data));
    }

    /**
     * 更新分页信息
     * @param pageable
     * @param results
     * @return
     */
    private Page<T> updatePage(Pageable pageable, List<T> results){
        int pageSize = pageable.getPageSize();
        int pageOffset = pageable.getOffset();
        int total = pageOffset + results.size() + (results.size() == pageSize?pageSize:0);
        return new PageImpl<T>(results, pageable, total);
    }

    /**
     * 转换
     * @param sort
     * @return
     */
    private SortOrder convert(Sort sort){
        SortOrder sortOrder = new SortOrder();
        if(sort != null){
            Iterator var = sort.iterator();
            while(var.hasNext()){
                Sort.Order order = (Sort.Order)var.next();
                if(order.isAscending()){
                    sortOrder.add(order.getProperty());
                } else{
                    sortOrder.add(SortOrder.Direction.DESC, order.getProperty());
                }
            }
        }
        return sortOrder;
    }
}
