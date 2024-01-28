package priv.cqq.im.controller.im;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.persistence.entity.MPPageParam;
import org.cqq.oplibrary.web.entity.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import priv.cqq.im.domain.dto.CChatMessageAddDTO;
import priv.cqq.im.domain.dto.CChatMessagePageDTO;
import priv.cqq.im.domain.dto.CChatMessageUpdateDTO;
import priv.cqq.im.domain.po.ChatMessage;
import priv.cqq.im.domain.vo.CChatMessagePageVO;
import priv.cqq.im.domain.vo.CChatMessageViewVO;
import priv.cqq.im.service.im.CChatMessageService;

import javax.validation.Valid;

/**
 * C端 IM消息表 接口
 *
 * @author CongQingquan
 */
@Api(value = "C端 IM消息表", tags = {"模块: C端 IM消息表"})
@Validated
@RestController
@AllArgsConstructor
public class CChatMessageController {

    private final CChatMessageService cChatMessageService;

    @ApiOperation(value = "分页")
    @PostMapping("/user/c/message/chatMessage/page")
    public R<IPage<CChatMessagePageVO>> page(MPPageParam<ChatMessage> pageParam, CChatMessagePageDTO pageDTP) {
        return R.success(cChatMessageService.page(pageParam.iPage(), pageDTP));
    }

    @ApiOperation(value = "详情")
    @GetMapping("/user/c/message/chatMessage/{messageId}")
    public R<CChatMessageViewVO> view(@ApiParam(value = "业务主键", required = true, type = "Long") @PathVariable(name = "messageId") Long messageId) {
        return R.success(cChatMessageService.view(messageId));
    }

    @ApiOperation(value = "新增")
    @PostMapping("/user/c/message/add")
    public R<Boolean> add(@RequestBody @Valid CChatMessageAddDTO addDTO) {
        return R.success(cChatMessageService.add(addDTO));
    }

    @ApiOperation(value = "修改")
    @PutMapping("/user/c/message/chatMessage")
    public R<Boolean> edit(@RequestBody @Valid CChatMessageUpdateDTO updateDTO) {
        return R.success(cChatMessageService.edit(updateDTO));
    }
}