package com.test.wemakeprice.controller;

import com.test.wemakeprice.dto.req.UrlParseReqDto;
import com.test.wemakeprice.dto.res.UrlParseResultResDto;
import com.test.wemakeprice.service.UrlParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UrlParserRestController {

    private final UrlParserService urlParserService;

    @PostMapping("/url-parse")
    @ResponseBody
    public UrlParseResultResDto getUrlParseResult(@RequestBody UrlParseReqDto reqDto) throws IOException {
        return urlParserService.getUrlParseResult(reqDto);
    }
}
