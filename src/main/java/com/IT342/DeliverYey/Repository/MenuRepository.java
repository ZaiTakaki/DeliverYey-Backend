package com.IT342.DeliverYey.Repository;

import com.IT342.DeliverYey.Entity.MenuEntity;
import com.IT342.DeliverYey.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    MenuEntity saveAndFlush(MenuEntity entity);
}
