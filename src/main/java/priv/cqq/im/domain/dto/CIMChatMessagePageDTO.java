package priv.cqq.im.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cqq.openlibrary.common.interfaces.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "IM消息表 分页接口入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CIMChatMessagePageDTO implements Pageable<Long> {

    @Schema(description = "页码")
    private Long pageNo;

    @Schema(description = "每页数量")
    private Long pageSize;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "业务主键")
    private Long messageId;

    @Schema(description = "发送用户主键")
    private Long fromUserId;

    @Schema(description = "群聊主键")
    private Long chatGroupId;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "回复的消息主键")
    private Long replyMsgId;

    @Schema(description = "与回复的消息间隔多少条")
    private Integer gapCount;

    @Schema(description = "消息类型(TEXT / REPLY)")
    private String type;

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