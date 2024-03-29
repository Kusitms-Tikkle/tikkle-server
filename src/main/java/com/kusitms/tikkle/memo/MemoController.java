package com.kusitms.tikkle.memo;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.memo.dto.MemoAllDto;
import com.kusitms.tikkle.memo.dto.MemoRequestDto;
import com.kusitms.tikkle.memo.dto.MemoWithTodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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

    @GetMapping("/{date}")
    public CommonResponse getMyMemosByDate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("date") String date) {
        List<MemoWithTodoResponseDto> dtoList = memoService.getMyMemosByDate(customUserDetails, date);
        return responseService.getDataResponse(dtoList);
    }

    @DeleteMapping("/{id}/image")
    public CommonResponse deleteMemoImage(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        memoService.deleteMemoImage(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

    @GetMapping()
    public CommonResponse getPublicMemo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<MemoAllDto> dtoList = memoService.getPublicMemo(customUserDetails);
        return responseService.getDataResponse(dtoList);

    }
}
