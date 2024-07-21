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
import priv.cqq.im.service.CIMUserContactService;
import priv.cqq.im.domain.vo.CImUserContactPageVO;
import priv.cqq.im.domain.vo.CImUserContactViewVO;
import priv.cqq.im.domain.dto.CImUserContactPageDTO;
import priv.cqq.im.domain.dto.CImUserContactAddDTO;
import priv.cqq.im.domain.dto.CImUserContactUpdateDTO;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "IM用户联系人表 接口")
@Validated
@RestController
@AllArgsConstructor
public class CIMUserContactController {

    private final CIMUserContactService cImUserContactService;

    @Operation(summary = "分页")
    @PostMapping("/im/c/userContact/page")
    public R<IPage<CImUserContactPageVO>> page(@RequestBody CImUserContactPageDTO pageDTO) {
        return R.success(cImUserContactService.page(new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize()), pageDTO));
    }

    @Operation(summary = "详情")
    @Parameter(name = "concatId", description = "业务主键", in = ParameterIn.PATH)
    @GetMapping("/im/c/userContact/{concatId}")
    public R<CImUserContactViewVO> view(@PathVariable(name = "concatId") Long concatId) {
        return R.success(cImUserContactService.view(concatId));
    }

    @Operation(summary = "新增")
    @PostMapping("/im/c/userContact")
    public R<Boolean> add(@RequestBody @Valid CImUserContactAddDTO addDTO) {
        return R.success(cImUserContactService.add(addDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("/im/c/userContact")
    public R<Boolean> edit(@RequestBody @Valid CImUserContactUpdateDTO updateDTO) {
        return R.success(cImUserContactService.edit(updateDTO));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/im/c/userContact")
    public R<Boolean> delete(@RequestBody List<Long> concatIdList) {
        return R.success(cImUserContactService.delete(concatIdList));
    }
}