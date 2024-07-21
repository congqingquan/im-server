package priv.cqq.im.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "im 用户登陆接口入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class IMUserAuthDTO {

    @Schema(description = "业务主键")
    private Long userId;

    @Schema(description = "帐号")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别: 1男性, 2女性")
    private Integer gender;
}