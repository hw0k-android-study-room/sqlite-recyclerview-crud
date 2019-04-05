package kr.hs.dgsw.databaseapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
    private int sequenceNumber;
    private String name;

    public UserBean(String name) {
        this.name = name;
    }
}
