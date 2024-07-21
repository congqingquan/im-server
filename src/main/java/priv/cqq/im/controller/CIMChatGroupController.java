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
import priv.cqq.im.service.CIMChatGroupService;
import priv.cqq.im.domain.vo.CImChatGroupPageVO;
import priv.cqq.im.domain.vo.CImChatGroupViewVO;
import priv.cqq.im.domain.dto.CImChatGroupPageDTO;
import priv.cqq.im.domain.dto.CImChatGroupAddDTO;
import priv.cqq.im.domain.dto.CImChatGroupUpdateDTO;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "IM群聊表 接口")
@Validated
@RestController
@AllArgsConstructor
public class CIMChatGroupController {

    private final CIMChatGroupService cImChatGroupService;

    @Operation(summary = "分页")
    @PostMapping("/im/c/chatGroup/page")
    public R<IPage<CImChatGroupPageVO>> page(@RequestBody CImChatGroupPageDTO pageDTO) {
        return R.success(cImChatGroupService.page(new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize()), pageDTO));
    }

    @Operation(summary = "详情")
    @Parameter(name = "groupId", description = "业务主键", in = ParameterIn.PATH)
    @GetMapping("/im/c/chatGroup/{groupId}")
    public R<CImChatGroupViewVO> view(@PathVariable(name = "groupId") Long groupId) {
        return R.success(cImChatGroupService.view(groupId));
    }

    @Operation(summary = "新增")
    @PostMapping("/im/c/chatGroup")
    public R<Boolean> add(@RequestBody @Valid CImChatGroupAddDTO addDTO) {
        return R.success(cImChatGroupService.add(addDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("/im/c/chatGroup")
    public R<Boolean> edit(@RequestBody @Valid CImChatGroupUpdateDTO updateDTO) {
        return R.success(cImChatGroupService.edit(updateDTO));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/im/c/chatGroup")
    public R<Boolean> delete(@RequestBody List<Long> groupIdList) {
        return R.success(cImChatGroupService.delete(groupIdList));
    }
}