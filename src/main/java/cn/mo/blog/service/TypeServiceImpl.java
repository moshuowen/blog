package cn.mo.blog.service;

import cn.mo.blog.NotFoundException;
import cn.mo.blog.dao.TypeResponsitory;
import cn.mo.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public List<Type> listType() {
        return typeResponsitory.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog.size");
//        排序倒序，按照size排序
//        在spring2.2.1以上使用Sort.by获取Sort对象,PageRequest.of获取PageRequest对象
        Pageable pageable =  PageRequest.of(0,size,sort);
        return typeResponsitory.findTop(pageable);
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
