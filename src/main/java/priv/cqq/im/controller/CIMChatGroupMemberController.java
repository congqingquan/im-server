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
import priv.cqq.im.service.CIMChatGroupMemberService;
import priv.cqq.im.domain.vo.CImChatGroupMemberPageVO;
import priv.cqq.im.domain.vo.CImChatGroupMemberViewVO;
import priv.cqq.im.domain.dto.CImChatGroupMemberPageDTO;
import priv.cqq.im.domain.dto.CImChatGroupMemberAddDTO;
import priv.cqq.im.domain.dto.CImChatGroupMemberUpdateDTO;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "IM群成员表 接口")
@Validated
@RestController
@AllArgsConstructor
public class CIMChatGroupMemberController {

    private final CIMChatGroupMemberService cImChatGroupMemberService;

    @Operation(summary = "分页")
    @PostMapping("/im/c/chatGroupMember/page")
    public R<IPage<CImChatGroupMemberPageVO>> page(@RequestBody CImChatGroupMemberPageDTO pageDTO) {
        return R.success(cImChatGroupMemberService.page(new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize()), pageDTO));
    }

    @Operation(summary = "详情")
    @Parameter(name = "groupMemberId", description = "业务主键", in = ParameterIn.PATH)
    @GetMapping("/im/c/chatGroupMember/{groupMemberId}")
    public R<CImChatGroupMemberViewVO> view(@PathVariable(name = "groupMemberId") Long groupMemberId) {
        return R.success(cImChatGroupMemberService.view(groupMemberId));
    }

    @Operation(summary = "新增")
    @PostMapping("/im/c/chatGroupMember")
    public R<Boolean> add(@RequestBody @Valid CImChatGroupMemberAddDTO addDTO) {
        return R.success(cImChatGroupMemberService.add(addDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("/im/c/chatGroupMember")
    public R<Boolean> edit(@RequestBody @Valid CImChatGroupMemberUpdateDTO updateDTO) {
        return R.success(cImChatGroupMemberService.edit(updateDTO));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/im/c/chatGroupMember")
    public R<Boolean> delete(@RequestBody List<Long> groupMemberIdList) {
        return R.success(cImChatGroupMemberService.delete(groupMemberIdList));
    }
}