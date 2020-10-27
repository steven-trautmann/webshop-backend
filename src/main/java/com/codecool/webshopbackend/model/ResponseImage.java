package com.codecool.webshopbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseImage {

        private String name;
        private String url;
        private String type;
        private long size;
}
