package cn.mo.blog.service;

import cn.mo.blog.po.Blog;
import cn.mo.blog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);
}
