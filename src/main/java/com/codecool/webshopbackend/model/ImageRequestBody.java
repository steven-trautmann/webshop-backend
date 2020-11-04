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
        private String imageType;

        public boolean fieldsAreNotNull() {
                return user_id != null && url != null && imageType != null;
        }

        public boolean imageTypeIsValid() {
                return imageType.equals("jpg") || imageType.equals("jpeg") || imageType.equals("png");
        }
}
