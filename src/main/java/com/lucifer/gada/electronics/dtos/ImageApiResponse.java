package com.lucifer.gada.electronics.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageApiResponse {
        private String ImageName;
        private String path;
        private String message;
        private boolean success;
        private HttpStatus status;
    }


