package priv.cqq.im.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cqq.openlibrary.common.domain.R;
import priv.cqq.im.service.CIMChatMessageService;
import priv.cqq.im.domain.vo.CImChatMessagePageVO;
import priv.cqq.im.domain.vo.CImChatMessageViewVO;
import priv.cqq.im.domain.dto.CIMChatMessagePageDTO;
import priv.cqq.im.domain.dto.CIMChatMessageAddDTO;
import priv.cqq.im.domain.dto.CIMChatMessageUpdateDTO;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "C端 IM消息表 接口")
@Validated
@RestController
@AllArgsConstructor
public class CIMChatMessageController {

    private final CIMChatMessageService cImChatMessageService;

    @Operation(summary = "分页")
    @PostMapping("/im/c/chatMessage/page")
    public R<IPage<CImChatMessagePageVO>> page(@RequestBody CIMChatMessagePageDTO pageDTO) {
        return R.success(cImChatMessageService.page(new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize()), pageDTO));
    }

    @Operation(summary = "详情")
    @Parameter(name = "messageId", description = "业务主键", in = ParameterIn.PATH)
    @GetMapping("/im/c/chatMessage/{messageId}")
    public R<CImChatMessageViewVO> view(@PathVariable(name = "messageId") Long messageId) {
        return R.success(cImChatMessageService.view(messageId));
    }

    @Operation(summary = "新增")
    @PostMapping("/im/c/chatMessage")
    public R<Boolean> add(@RequestBody @Valid CIMChatMessageAddDTO addDTO) {
        return R.success(cImChatMessageService.add(addDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("/im/c/chatMessage")
    public R<Boolean> edit(@RequestBody @Valid CIMChatMessageUpdateDTO updateDTO) {
        return R.success(cImChatMessageService.edit(updateDTO));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/im/c/chatMessage")
    public R<Boolean> delete(@RequestBody List<Long> messageIdList) {
        return R.success(cImChatMessageService.delete(messageIdList));
    }
}