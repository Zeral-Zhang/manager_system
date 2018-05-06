package com.zeral.repository;

import com.zeral.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author zeral
 * @date 2018-03-07
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
