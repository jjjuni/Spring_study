package umc.spring.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.service.StoreService.StoreCommandService;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.web.dto.StoreDTO.StoreRequestDTO;
import umc.spring.web.dto.StoreDTO.StoreResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/store")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    // 추후에 path 및 body 수정 필요
    @PostMapping("")
    public ApiResponse<StoreResponseDTO.AddStoreResultDTO> join(@RequestBody @Valid StoreRequestDTO.AddStoreDTO request){
        Store store = storeCommandService.addStore(request);
        return ApiResponse.onSuccess(StoreConverter.toAddStoreResultDTO(store));
    }



    private final StoreQueryService storeQueryService;

    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API", description = "특정 가게의 리뷰 목록을 조회하는 API로 페이징을 포함. query String으로 page 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰 필요", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 이상", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게 아이디, path variable")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreviewListDTO> getReviewList(@ExistStore @PathVariable(name = "storeId") Long storeId, @CheckPage @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = storeQueryService.getReviewList(storeId, page - 1);
        return ApiResponse.onSuccess(StoreConverter.reviewPreviewListDTO(reviewList));
    }
}
