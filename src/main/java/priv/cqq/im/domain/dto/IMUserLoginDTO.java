package priv.cqq.im.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "im 用户登陆鉴权接口入参")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMUserLoginDTO {

    private String username;
    
    private String password;
}