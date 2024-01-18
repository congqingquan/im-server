package priv.cqq.im.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMUserLoginDTO {

    private String username;
    
    private String password;
}