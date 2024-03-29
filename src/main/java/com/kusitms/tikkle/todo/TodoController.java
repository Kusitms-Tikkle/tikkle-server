package com.kusitms.tikkle.todo;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.todo.dto.todoRes;
import com.kusitms.tikkle.todo.dto.todoResWithMemo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/todo")
public class TodoController {

    private final TodoService todoService;
    private final ResponseService responseService;

    @GetMapping("/{date}")
    public CommonResponse getTodoByDate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "date") String date) {
        List<todoRes> list = todoService.getTodoByDate(customUserDetails, date);
        return responseService.getDataResponse(list);
    }

    @PostMapping("/check/{id}")
    public CommonResponse toggleTodoByMissionId(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        todoService.toggleTodoByMissionId(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

    @GetMapping("/{date}/unwritten")
    public CommonResponse getTodoByDateUnWritten(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "date") String date) {
        List<todoResWithMemo> list = todoService.getTodoByDateUnWritten(customUserDetails, date);
        return responseService.getDataResponse(list);
    }
}
