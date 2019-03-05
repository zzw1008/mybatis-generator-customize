package com.zzw.dao.problem;

import com.zzw.entity.problem.Problem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemDao {
    int deleteByPrimaryKey(Long id);

    int insert(Problem record);

    Problem selectByPrimaryKey(Long id);

    List<Problem> selectAll();

    int updateByPrimaryKey(Problem record);
}