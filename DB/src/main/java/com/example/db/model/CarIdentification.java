package com.example.db.model;

import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CarIdentification {

    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private String generation;
    @NonNull
    private String engineType;

}
