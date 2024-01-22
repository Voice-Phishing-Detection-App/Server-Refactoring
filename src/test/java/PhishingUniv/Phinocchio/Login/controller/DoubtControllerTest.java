package PhishingUniv.Phinocchio.Login.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import PhishingUniv.Phinocchio.domain.Doubt.controller.DoubtController;
import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.service.DoubtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = DoubtController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class DoubtControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  private DoubtService doubtService;

  @BeforeEach
  private void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .alwaysDo(print())
        .build();
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 성공")
  @Test
  void doubtSuccess() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = new DoubtRequestDto();
    doubtRequestDto.setPhoneNumber("01012341234");
    doubtRequestDto.setText("안녕하십니까 서울중앙지방검찰청 김피싱 검사입니다 현재 김피해님의 통장이 범죄에 연루되어 있어 검찰청으로 출석해주셔야 합니다");

    MLResponseDto mlResponseDto = new MLResponseDto();
    mlResponseDto.setLevel(2);
    mlResponseDto.setPhishing(true);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willReturn(mlResponseDto);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.level").exists())
        .andExpect(jsonPath("$.level").value(2))
        .andExpect(jsonPath("$.phishing").exists())
        .andExpect(jsonPath("$.phishing").value(true))
        .andDo(print());

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

}
