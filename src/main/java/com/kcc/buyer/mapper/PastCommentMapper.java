package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.PastComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PastCommentMapper {

    int insertPastComment(PastComment pastComment);

    List<PastComment> selectPastCommentByOrderId(PastComment pastComment);

}
