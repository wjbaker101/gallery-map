package com.wjbaker.gallery_map.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(schema = "gallery", name = "photo")
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    @Getter
    private AlbumEntity album;

    @Getter @Setter
    @Column(name = "title")
    private String title;

    @Getter @Setter
    @Column(name = "url")
    private String url;

    @Getter @Setter
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Getter @Setter
    @Column(name = "longitude")
    private Double longitude;

    @Getter @Setter
    @Column(name = "latitude")
    private Double latitude;

    @Getter
    @Column(name = "created_at")
    private Instant createdAt;

    @Getter @Setter
    @Column(name = "external_identifier")
    private String externalIdentifier;

    @Getter @Setter
    @Column(name = "list_order")
    private int listOrder;
}