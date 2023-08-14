package com.kusitms.tikkle.memo;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.memo.dto.MemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/memo")
public class MemoController {

    private final MemoService memoService;
    private final ResponseService responseService;

    @PostMapping()
    public CommonResponse postMemo(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @RequestPart(value = "memoDto") MemoRequestDto memoRequestDto, @RequestPart(value = "image", required = false) MultipartFile multipartFile){
        memoService.postMemo(customUserDetails, memoRequestDto, multipartFile);
        return responseService.getSuccessResponse();
    }

    @PostMapping("/private/{id}")
    public CommonResponse toggleMemoPrivate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        memoService.toggleMemoPrivate(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteMemo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        memoService.deleteMemo(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

    @PatchMapping()
    public CommonResponse changeMemoContent(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestPart(value = "memoDto") MemoRequestDto memoRequestDto, @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        memoService.changeMemoContent(customUserDetails, memoRequestDto, multipartFile);
        return responseService.getSuccessResponse();
    }
}
