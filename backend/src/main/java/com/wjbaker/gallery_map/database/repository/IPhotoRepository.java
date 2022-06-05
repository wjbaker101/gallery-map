package com.wjbaker.gallery_map.database.repository;

import com.wjbaker.gallery_map.database.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhotoRepository extends JpaRepository<PhotoEntity, Long> {

}