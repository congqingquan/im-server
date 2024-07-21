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
import priv.cqq.im.service.CIMChatSessionService;
import priv.cqq.im.domain.vo.CImChatSessionPageVO;
import priv.cqq.im.domain.vo.CImChatSessionViewVO;
import priv.cqq.im.domain.dto.CImChatSessionPageDTO;
import priv.cqq.im.domain.dto.CImChatSessionAddDTO;
import priv.cqq.im.domain.dto.CImChatSessionUpdateDTO;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "IM聊天会话列表 接口")
@Validated
@RestController
@AllArgsConstructor
public class CIMChatSessionController {

    private final CIMChatSessionService cImChatSessionService;

    @Operation(summary = "分页")
    @PostMapping("/im/c/chatSession/page")
    public R<IPage<CImChatSessionPageVO>> page(@RequestBody CImChatSessionPageDTO pageDTO) {
        return R.success(cImChatSessionService.page(new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize()), pageDTO));
    }

    @Operation(summary = "详情")
    @Parameter(name = "sessionId", description = "业务主键", in = ParameterIn.PATH)
    @GetMapping("/im/c/chatSession/{sessionId}")
    public R<CImChatSessionViewVO> view(@PathVariable(name = "sessionId") Long sessionId) {
        return R.success(cImChatSessionService.view(sessionId));
    }

    @Operation(summary = "新增")
    @PostMapping("/im/c/chatSession")
    public R<Boolean> add(@RequestBody @Valid CImChatSessionAddDTO addDTO) {
        return R.success(cImChatSessionService.add(addDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("/im/c/chatSession")
    public R<Boolean> edit(@RequestBody @Valid CImChatSessionUpdateDTO updateDTO) {
        return R.success(cImChatSessionService.edit(updateDTO));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/im/c/chatSession")
    public R<Boolean> delete(@RequestBody List<Long> sessionIdList) {
        return R.success(cImChatSessionService.delete(sessionIdList));
    }
}