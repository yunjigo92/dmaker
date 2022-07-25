package com.yunji.dmaker.repository;

import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 18.
 */
@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper,Long> {
}
