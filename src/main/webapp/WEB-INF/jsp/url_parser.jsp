<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <title>URL Parser</title>
    <link href="/resources/css/style.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
    <script>
        function submit() {
            const url = $('#urlInput').val();
            const type = $('#typeSelect').val();
            const unit = $('#numberInput').val();

            if ('' === url || '' === type || '' === unit) {
                alert('URL, Type, 출력 단위 묶음을 입력해주세요.')
            } else if (parseInt(unit) === 0) {
                alert('출력 단위 묶음은 최소 1입니다.')
            } else {
                $.ajax({
                    url: '${pageContext.request.contextPath}/url-parse',
                    type: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        "url": url,
                        "parseType": type,
                        "outputUnit": unit,
                    }),
                    success: function (data) {
                        if (data.resultCode === "200") {
                            $('#quotientDiv').html(data.quotientStr);
                            $('#remainderDiv').html(data.remainderStr);
                        } else if (data.resultCode === "1001") {
                            alert('유효하지 않은 URL입니다.')

                            $('#urlInput').focus();
                        }
                    },
                    error: function (err) {
                        alert('시스템 관리자에게 문의하세요.');

                        return;
                    }
                });

            }


        }
    </script>

    <div class="box-border">
        <div>
            <div class="form-box">
                [입력]
                <label for="urlInput">
                    <div>URL</div>
                    <input id="urlInput" type="text" name="url" value=""/>
                </label>
                <label for="typeSelect">
                    <div>Type</div>
                    <select id="typeSelect" name="parseType">
                        <option value="EXCEPT_HTML">HTML 태그 제외</option>
                        <option value="ALL">Text 전체</option>
                    </select>
                </label>
                <label for="numberInput">
                    <div>출력 단위 묶음</div>
                    <input id="numberInput" min="1" type="number" name="outputUnit"/>
                </label>
                <div class="submit-button-box">
                    <button onclick="submit()">출력</button>
                </div>
            </div>
        </div>
        <div>
            <div class="result-box">
                [출력]
                <div class="result-box-inner">
                    <div class="result-box-label">
                        몫
                    </div>
                    <div class="result-box-content" id="quotientDiv"></div>
                </div>
                <div class="result-box-inner">
                    <div class="result-box-label">
                        나머지
                    </div>
                    <div class="result-box-content" id="remainderDiv"></div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
