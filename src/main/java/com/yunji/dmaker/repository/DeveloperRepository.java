package com.yunji.dmaker.repository;

import com.yunji.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 18.
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer,Long> {
}