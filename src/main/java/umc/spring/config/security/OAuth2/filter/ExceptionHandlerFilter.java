package umc.spring.config.security.OAuth2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import umc.spring.apiPayload.code.ErrorReasonDTO;
import umc.spring.apiPayload.exception.GeneralException;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (GeneralException e) {
            handleException(response, e);
        }
    }

    private void handleException (HttpServletResponse response, GeneralException e) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json; charset=UTF-8");

        ErrorReasonDTO errorReasonDTO = ErrorReasonDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .isSuccess(false)
                .code(String.valueOf(e.getCode()))
                .message(e.getMessage())
                .build();

        response.getWriter().write(mapper.writeValueAsString(errorReasonDTO));
    }
}
