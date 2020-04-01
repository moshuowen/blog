package cn.mo.blog.service;

import cn.mo.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

public interface TypeService {
    Type saveType(Type type );

    Type getType(Long id);

    Type getTypeByName(String name);
//    通过名称查询type

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    Type updateType(Long id, Type type);

    void deleteType(Long id);
}
