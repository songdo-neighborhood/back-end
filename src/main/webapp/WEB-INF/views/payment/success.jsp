<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 승인 중...</title>
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

        .spinner {
            width: 50px;
            height: 50px;
            border: 4px solid #f3f3f3;
            border-top: 4px solid #1e88e5;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin: 0 auto 20px;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }

        .message {
            color: #6c757d;
            font-size: 15px;
            line-height: 1.6;
        }

        .success-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            display: none;
        }

        .success-icon.show {
            display: block;
        }

        .success-icon svg {
            width: 100%;
            height: 100%;
        }

        .error-container {
            display: none;
        }

        .error-container.show {
            display: block;
        }

        .error-icon {
            font-size: 60px;
            margin-bottom: 20px;
        }

        .error-message {
            color: #dc3545;
            font-size: 15px;
            margin-bottom: 20px;
        }

        .button {
            display: inline-block;
            padding: 12px 24px;
            background: #1e88e5;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 500;
            margin-top: 20px;
            transition: background 0.2s;
        }

        .button:hover {
            background: #1976d2;
        }

        .payment-info {
            text-align: left;
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-top: 20px;
            display: none;
        }

        .payment-info.show {
            display: block;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-size: 14px;
        }

        .info-row:last-child {
            margin-bottom: 0;
        }

        .label {
            color: #6c757d;
        }

        .value {
            color: #212529;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 로딩 상태 -->
        <div id="loading">
            <div class="spinner"></div>
            <h1>결제 승인 중입니다</h1>
            <p class="message">잠시만 기다려주세요...</p>
        </div>

        <!-- 성공 상태 -->
        <div id="success" style="display: none;">
            <div class="success-icon show">
                <svg viewBox="0 0 100 100">
                    <circle cx="50" cy="50" r="45" fill="#4caf50"/>
                    <path d="M30 50 L45 65 L70 35" stroke="white" stroke-width="8" fill="none" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </div>
            <h1>결제가 완료되었습니다</h1>
            <p class="message">결제가 정상적으로 처리되었습니다.</p>
            <div class="payment-info show" id="payment-info"></div>
            <a href="/" class="button">홈으로 돌아가기</a>
        </div>

        <!-- 에러 상태 -->
        <div id="error" class="error-container">
            <div class="error-icon">⚠️</div>
            <h1>결제 승인에 실패했습니다</h1>
            <p class="error-message" id="error-text"></p>
            <a href="/" class="button">홈으로 돌아가기</a>
        </div>
    </div>

    <script>
        // URL 파라미터 파싱
        const urlParams = new URLSearchParams(window.location.search);
        const paymentKey = urlParams.get('paymentKey');
        const orderId = urlParams.get('orderId');
        const amount = urlParams.get('amount');

        // 결제 승인 API 호출
        async function confirmPayment() {
            try {
                const response = await fetch('/api/payments/confirm', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        paymentKey: paymentKey,
                        orderId: orderId,
                        amount: parseInt(amount)
                    })
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || '결제 승인에 실패했습니다.');
                }

                const result = await response.json();

                // 성공 화면 표시
                showSuccess(result);
            } catch (error) {
                // 에러 화면 표시
                showError(error.message);
            }
        }

        function showSuccess(paymentData) {
            document.getElementById('loading').style.display = 'none';
            document.getElementById('success').style.display = 'block';

            // 결제 정보 표시
            const infoHtml = `
                <div class="info-row">
                    <span class="label">주문번호</span>
                    <span class="value">${orderId}</span>
                </div>
                <div class="info-row">
                    <span class="label">결제금액</span>
                    <span class="value">${parseInt(amount).toLocaleString()}원</span>
                </div>
                <div class="info-row">
                    <span class="label">결제수단</span>
                    <span class="value">${paymentData.method || '토스페이'}</span>
                </div>
            `;
            document.getElementById('payment-info').innerHTML = infoHtml;
        }

        function showError(message) {
            document.getElementById('loading').style.display = 'none';
            document.getElementById('error').classList.add('show');
            document.getElementById('error-text').textContent = message;
        }

        // 페이지 로드 시 결제 승인 시도
        confirmPayment();
    </script>
</body>
</html>
