package com.codecool.webshopbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ImageDB {

    @Id
    @GeneratedValue
    Long id;

    @OneToOne
    @MapsId
    @JoinColumn
    private AppUser appUser;

    @Column(nullable = false, unique = true)
    private String url;

}
