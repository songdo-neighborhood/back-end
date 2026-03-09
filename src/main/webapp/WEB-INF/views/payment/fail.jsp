<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 실패</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 500px;
            width: 100%;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 40px;
            text-align: center;
        }

        .error-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }

        .error-message {
            color: #dc3545;
            font-size: 15px;
            line-height: 1.6;
            margin-bottom: 20px;
            padding: 15px;
            background: #fff5f5;
            border-radius: 8px;
            border: 1px solid #ffebee;
        }

        .error-code {
            color: #6c757d;
            font-size: 13px;
            margin-top: 10px;
        }

        .button-group {
            display: flex;
            gap: 10px;
            justify-content: center;
            margin-top: 30px;
        }

        .button {
            padding: 12px 24px;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.2s;
            border: none;
            cursor: pointer;
        }

        .button-primary {
            background: #1e88e5;
            color: white;
        }

        .button-primary:hover {
            background: #1976d2;
        }

        .button-secondary {
            background: #f8f9fa;
            color: #333;
            border: 1px solid #dee2e6;
        }

        .button-secondary:hover {
            background: #e9ecef;
        }

        .support-info {
            margin-top: 30px;
            padding-top: 30px;
            border-top: 1px solid #dee2e6;
            text-align: left;
        }

        .support-title {
            font-size: 14px;
            font-weight: 600;
            color: #333;
            margin-bottom: 10px;
        }

        .support-list {
            list-style: none;
            font-size: 14px;
            color: #6c757d;
            line-height: 1.8;
        }

        .support-list li:before {
            content: "• ";
            color: #1e88e5;
            font-weight: bold;
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-icon">❌</div>
        <h1>결제에 실패했습니다</h1>

        <div class="error-message">
            <strong id="error-message"></strong>
            <div class="error-code" id="error-code"></div>
        </div>

        <div class="button-group">
            <button class="button button-primary" onclick="history.back()">다시 시도</button>
            <a href="/" class="button button-secondary">홈으로 돌아가기</a>
        </div>

        <div class="support-info">
            <div class="support-title">결제 실패 주요 원인</div>
            <ul class="support-list">
                <li>카드 한도 초과 또는 잔액 부족</li>
                <li>카드 정보 입력 오류</li>
                <li>결제 취소 또는 시간 초과</li>
                <li>은행 또는 카드사 시스템 오류</li>
            </ul>
        </div>
    </div>

    <script>
        // URL 파라미터에서 에러 정보 추출
        const urlParams = new URLSearchParams(window.location.search);
        const errorCode = urlParams.get('code');
        const errorMessage = urlParams.get('message');
        const orderId = urlParams.get('orderId');

        // 에러 메시지 표시
        if (errorMessage) {
            document.getElementById('error-message').textContent = decodeURIComponent(errorMessage);
        } else {
            document.getElementById('error-message').textContent = '알 수 없는 오류가 발생했습니다.';
        }

        // 에러 코드 표시
        if (errorCode) {
            document.getElementById('error-code').textContent = `에러 코드: ${errorCode}`;
        }

        // 주문 ID가 있으면 표시
        if (orderId) {
            const codeElement = document.getElementById('error-code');
            const currentText = codeElement.textContent;
            codeElement.textContent = currentText ? `${currentText} | 주문번호: ${orderId}` : `주문번호: ${orderId}`;
        }
    </script>
</body>
</html>
