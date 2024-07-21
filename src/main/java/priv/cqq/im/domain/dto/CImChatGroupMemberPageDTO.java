package priv.cqq.im.domain.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.cqq.openlibrary.common.interfaces.Pageable;

@Schema(description = "IM群成员表 分页接口入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CImChatGroupMemberPageDTO implements Pageable<Long> {

    @Schema(description = "页码")
    private Long pageNo;

    @Schema(description = "每页数量")
    private Long pageSize;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "业务主键")
    private Long groupMemberId;

    @Schema(description = "群聊主键")
    private Long groupId;

    @Schema(description = "成员主键")
    private Long userId;

    @Schema(description = "成员角色(OWNER / ADMIN / MEMBER)")
    private String role;

    @Schema(description = "创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}