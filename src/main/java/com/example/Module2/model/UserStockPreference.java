package com.example.Module2.model;

import com.example.Module2.Validation.ValidOperation;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStockPreference {
    @ValidOperation
    private String operation;

    @Id
    @NotBlank(message="userId shouldn't be null or empty")
    private String userId;
    @NotBlank(message="watchList shouldn't be null or empty")
    private String watchList;
    @NotBlank(message="stock shouldn't be null or empty")
    private String stock;


}
