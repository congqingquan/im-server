package priv.cqq.im.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Web socket channel extension DTO
 *
 * @author Qingquan.Cong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WSChannelExtDTO {

    private String channelId;

    private Long userId;
}