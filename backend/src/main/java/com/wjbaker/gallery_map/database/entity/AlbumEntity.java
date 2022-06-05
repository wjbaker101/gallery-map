package com.wjbaker.gallery_map.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(schema = "gallery", name = "album")
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @OneToMany(mappedBy = "album")
    @Getter
    private List<PhotoEntity> photos;

    @Getter @Setter
    @Column(name = "title")
    private String title;

    @Getter
    @Column(name = "created_at")
    private Instant createdAt;
}