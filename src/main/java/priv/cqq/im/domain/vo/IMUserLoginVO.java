package priv.cqq.im.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "im 用户登陆鉴权接口出参")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMUserLoginVO {

    private String token;
}