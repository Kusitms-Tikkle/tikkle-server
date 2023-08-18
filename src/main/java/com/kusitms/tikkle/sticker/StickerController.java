package com.kusitms.tikkle.sticker;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sticker")
public class StickerController {

    private final StickerService stickerService;
    private final ResponseService responseService;

    @PostMapping("/{memo_id}/{dtype}")
    public CommonResponse postStickerByDtype(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("memo_id") Long memoId, @PathVariable("dtype") String dtype) {
        stickerService.postStickerByDtype(customUserDetails, memoId, dtype);
        return responseService.getSuccessResponse();
    }
}
