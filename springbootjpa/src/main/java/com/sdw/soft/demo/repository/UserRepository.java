package com.sdw.soft.demo.repository;

import com.sdw.soft.demo.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shangyindong on 2017/3/1.
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
