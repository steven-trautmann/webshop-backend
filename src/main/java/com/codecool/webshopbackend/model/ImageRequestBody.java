package com.codecool.webshopbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageRequestBody {

        private Long user_id;
        private String url;

        public boolean fieldsAreNotNull() {
                return user_id != null && url != null;
        }
}
