package com.schooleERP.repository;

import com.schooleERP.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitiesRepo extends JpaRepository<Activities, Long> {

    List<Activities> findByActivityType(String activityType);

    List<Activities> findByActive(boolean active);

    List<Activities> findByActivityNameContainingIgnoreCase(String activityName);

}
