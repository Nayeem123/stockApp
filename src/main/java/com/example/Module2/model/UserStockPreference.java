package com.example.Module2.model;

import com.example.Module2.Validation.ValidOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class UserStockPreference {
    @ValidOperation
    private String operation;
    @Id
    private String userId;
    private String watchList;
    private List stock;
    public UserStockPreference(String operation, String userId, String watchList, List stock) {
        this.operation = operation;
        this.userId = userId;
        this.watchList = watchList;
        this.stock = stock;
    }
}
