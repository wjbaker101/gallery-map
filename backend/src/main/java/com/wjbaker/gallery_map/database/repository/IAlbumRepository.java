package com.wjbaker.gallery_map.database.repository;

import com.wjbaker.gallery_map.database.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlbumRepository extends JpaRepository<AlbumEntity, Long> {

    @Query("select album from AlbumEntity album ")
    List<AlbumEntity> search();
}