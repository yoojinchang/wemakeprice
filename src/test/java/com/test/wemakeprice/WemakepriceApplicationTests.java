package com.test.wemakeprice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.wemakeprice.dto.req.UrlParseReqDto;
import com.test.wemakeprice.type.ParseType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
class WemakepriceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void jsoupTest() {
        try {
            Document document = Jsoup.connect("https://front.wemakeprice.com/main").get();
            log.info("Text========================");
            log.info(document.text());
            log.info("Html========================");
            log.info(document.html());
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void jsoupTestThrow() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Jsoup.connect("invalid").get();
        });
    }

    @Test
    void getUrlParseResultTest() throws Exception {
        String url = "/url-parse";

        UrlParseReqDto reqDto = new UrlParseReqDto();
        reqDto.setUrl("https://front.wemakeprice.com/main");
        reqDto.setParseType(ParseType.ALL);
        reqDto.setOutputUnit(100);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(objectMapper.writeValueAsString(reqDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    log.info(response.getContentAsString());
                });
    }

}
