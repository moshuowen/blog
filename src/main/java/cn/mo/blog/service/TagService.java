package cn.mo.blog.service;

import cn.mo.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);
//    通过名称查询type

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);
}
