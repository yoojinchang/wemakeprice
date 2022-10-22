package com.test.wemakeprice.dto.req;

import com.test.wemakeprice.type.ParseType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UrlParseReqDto {

    private String url;

    private ParseType parseType;

    private int outputUnit;
}
