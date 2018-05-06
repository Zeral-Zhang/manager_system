package com.zeral.repository;

import com.zeral.domain.WorkPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by karen on 2018/1/30.
 * */
@Repository
public interface WorkPlanRepository extends JpaRepository<WorkPlan, Long> {
    Page<WorkPlan> findAllByProIdAndStateAndDeleteStatueIsTrue(String query, Integer state, Pageable pageable);
    Page<WorkPlan>  findAllByProIdAndStateIsNotAndDeleteStatueIsTrue(String query, Integer state, Pageable pageable);
    List<WorkPlan> findAllByProIdAndStateAndDeleteStatueIsTrue(String query, Integer state, Sort sort);
    List<WorkPlan>  findAllByProIdAndStateIsNotAndDeleteStatueIsTrue(String query, Integer state, Sort sort);
    Page<WorkPlan> findAllByDeleteStatueIsTrueAndNameLike(String query, Pageable pageable);

    //查询所有项目已完成事项
    Page<WorkPlan> findAllByDeleteStatueIsTrueAndStateAndNameLike(Integer state, String query, Pageable pageable);
    //查询所有项目未办事项
    Page<WorkPlan> findAllByDeleteStatueIsTrueAndStateIsNotAndNameLike(Integer state,String query, Pageable pageable);
    //一个项目的已完成事件
    Page<WorkPlan> findAllByDeleteStatueIsTrueAndStateAndDepIdAndProId(Integer state, String depId, String proId, Pageable pageable);
    //一个项目的代办事件
    Page<WorkPlan> findAllByDeleteStatueIsTrueAndStateIsNotAndDepIdAndProId(Integer state, String depId, String proId, Pageable pageable);

}
