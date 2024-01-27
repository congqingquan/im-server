package priv.cqq.im.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * im 用户认证鉴权接口出参
 *
 * @author Qingquan.Cong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMUserLoginDTO {

    private String username;
    
    private String password;
}