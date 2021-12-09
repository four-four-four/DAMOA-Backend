package com.fourfourfour.damoa.controller.user;

import com.fourfourfour.damoa.model.dto.response.BasicResponseDto;
import com.fourfourfour.damoa.model.dto.user.UserDto;
import com.fourfourfour.damoa.model.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:9999/swagger-ui/index.html
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "all user find", description = "전체 회원 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 회원 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @ApiOperation(value="전체 회원 정보를 반환합니다.", response = BasicResponseDto.class)
    @GetMapping("/all")
    public ResponseEntity findAll() {
        BasicResponseDto response;

        /*
            파라미터가 있다면 확인
            서비스 메서드 호출
            파라미터 확인 or 서비스 메서드 호출 결과에 따라 응답
        */

        List<UserDto> userList = userService.findAll();

        // 회원 정보가 있는 경우
        if (userList != null) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .data(userList)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // 회원 정보가 없는 경우
        else {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .data("회원 정보가 없습니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

    }

    @Operation(summary = "all user remove", description = "전체 회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "전체 회원 정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @ApiOperation(value="전체 회원 정보를 삭제합니다.", response = BasicResponseDto.class)
    @DeleteMapping("/all")
    public ResponseEntity removeAll() {
        BasicResponseDto response;

        /*
            파라미터가 있다면 확인
            서비스 메서드 호출
            파라미터 확인 or 서비스 메서드 호출 결과에 따라 응답
        */

        try {
            // 삭제 요청이 성공한 경우
            userService.removeAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .data("잘못된 요청입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

}
