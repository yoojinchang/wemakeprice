package com.test.wemakeprice.service;

import com.test.wemakeprice.dto.req.UrlParseReqDto;
import com.test.wemakeprice.dto.res.UrlParseResultResDto;
import com.test.wemakeprice.type.ParseType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UrlParserService {
    public UrlParseResultResDto getUrlParseResult(UrlParseReqDto reqDto) throws IOException {

        try {
            Document document = Jsoup.connect(reqDto.getUrl()).get();

            String parseData;
            if (ParseType.EXCEPT_HTML == reqDto.getParseType()) {
                parseData = document.text();
            } else {
                parseData = document.html();
            }

            String engRegex = "[^A-Za-z]";

            String[] engArray = parseData.replaceAll(engRegex, "").split("");
            String reversedSortedEngStr = Arrays.stream(engArray)
                    .sorted(String.CASE_INSENSITIVE_ORDER
                            .thenComparingInt(o -> o.charAt(0))
                    )
                    .collect(Collectors.joining());

            String numRegex = "[^0-9]";

            String[] numArray = parseData.replaceAll(numRegex, "").split("");
            String sortedNumStr = Arrays.stream(numArray)
                    .sorted()
                    .collect(Collectors.joining());

            int loopCnt = Math.min(reversedSortedEngStr.length(), sortedNumStr.length());

            StringBuilder mixedStr = new StringBuilder();

            while (loopCnt > 0) {
                mixedStr.append(reversedSortedEngStr.charAt(0));
                reversedSortedEngStr = reversedSortedEngStr.substring(1);

                mixedStr.append(sortedNumStr.charAt(0));
                sortedNumStr = sortedNumStr.substring(1);

                if (reversedSortedEngStr.length() == 0 || sortedNumStr.length() == 0) {
                    mixedStr.append(reversedSortedEngStr.length() == 0 ? sortedNumStr : reversedSortedEngStr);

                    break;
                }

                loopCnt--;
            }

            int quotient = mixedStr.length() / reqDto.getOutputUnit();

            UrlParseResultResDto urlParseResultResDto = new UrlParseResultResDto();

            urlParseResultResDto.setResultCode("200");
            urlParseResultResDto.setQuotientStr(mixedStr.substring(0, reqDto.getOutputUnit() * quotient));
            urlParseResultResDto.setRemainderStr(mixedStr.substring(reqDto.getOutputUnit() * quotient));

            return urlParseResultResDto;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            UrlParseResultResDto urlParseResultResDto = new UrlParseResultResDto();

            urlParseResultResDto.setResultCode("1001");
            return urlParseResultResDto;
        }
    }
}
