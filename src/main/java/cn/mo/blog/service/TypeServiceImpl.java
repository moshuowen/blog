package cn.mo.blog.service;

import cn.mo.blog.NotFoundException;
import cn.mo.blog.dao.TypeResponsitory;
import cn.mo.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeResponsitory typeResponsitory;

    @Override
    public Type getTypeByName(String name) {
        return typeResponsitory.findByName(name);
//        重复名的验证
    }

    //放在事务中
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeResponsitory.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeResponsitory.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeResponsitory.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeResponsitory.getOne(id);
        if(type1 == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,type1);
        return typeResponsitory.save(type1);
    }//type1中含有id,完成更新的操作

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeResponsitory.deleteById(id);
    }


}
//        根据名字查找type
