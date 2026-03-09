<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제하기</title>
    <script src="https://js.tosspayments.com/v1/payment-widget"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 30px;
        }

        h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }

        .order-info {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            font-size: 15px;
        }

        .info-row:last-child {
            margin-bottom: 0;
            padding-top: 12px;
            border-top: 1px solid #dee2e6;
            font-weight: bold;
            font-size: 18px;
        }

        .label {
            color: #6c757d;
        }

        .value {
            color: #212529;
            font-weight: 500;
        }

        #payment-method, #agreement {
            margin-bottom: 20px;
        }

        .payment-button {
            width: 100%;
            padding: 16px;
            background: #1e88e5;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.2s;
        }

        .payment-button:hover {
            background: #1976d2;
        }

        .payment-button:disabled {
            background: #ccc;
            cursor: not-allowed;
        }

        .loading {
            text-align: center;
            padding: 20px;
            color: #6c757d;
        }

        .error-message {
            color: #dc3545;
            margin-top: 10px;
            font-size: 14px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>결제하기</h1>

        <div class="order-info">
            <div class="info-row">
                <span class="label">주문번호</span>
                <span class="value" id="orderId">${orderId}</span>
            </div>
            <div class="info-row">
                <span class="label">상품명</span>
                <span class="value" id="orderName">${orderName}</span>
            </div>
            <div class="info-row">
                <span class="label">결제금액</span>
                <span class="value" id="amount">${amount}원</span>
            </div>
        </div>

        <!-- 결제 수단 선택 영역 -->
        <div id="payment-method"></div>

        <!-- 약관 동의 영역 -->
        <div id="agreement"></div>

        <!-- 결제 버튼 -->
        <button class="payment-button" id="payment-button" disabled>결제 준비 중...</button>

        <div class="error-message" id="error-message"></div>
    </div>

    <script>
        // JSP에서 전달받은 값들
        const orderId = '${orderId}';
        const orderName = '${orderName}';
        const amount = parseInt('${amount}');
        const successUrl = '${successUrl}';
        const failUrl = '${failUrl}';
        const clientKey = '${clientKey}';

        console.log('주문 정보:', { orderId, orderName, amount, successUrl, failUrl, clientKey });

        // 전역 변수로 위젯 저장
        let paymentWidget = null;
        let isWidgetReady = false;

        // Toss Payments 초기화
        async function initializePayment() {
            const button = document.getElementById('payment-button');

            try {
                // PaymentWidget 초기화
                paymentWidget = PaymentWidget(clientKey, PaymentWidget.ANONYMOUS);

                // 결제금액 설정 및 UI 렌더링 - Promise 반환값을 제대로 처리
                const paymentMethodsPromise = paymentWidget.renderPaymentMethods(
                    '#payment-method',
                    { value: amount, currency: 'KRW', country: 'KR' },
                    { variantKey: 'DEFAULT' }
                );

                // 약관 UI 렌더링 - Promise 반환값을 제대로 처리
                const agreementPromise = paymentWidget.renderAgreement(
                    '#agreement',
                    { variantKey: 'AGREEMENT' }
                );

                // 두 렌더링이 모두 완료될 때까지 대기
                console.log('렌더링 시작...');
                await Promise.all([paymentMethodsPromise, agreementPromise]);
                console.log('렌더링 완료!');

                // 추가로 1초 더 대기 (UI가 완전히 준비될 시간)
                await new Promise(resolve => setTimeout(resolve, 1000));

                // 위젯 준비 완료
                isWidgetReady = true;
                button.disabled = false;
                button.textContent = '결제하기';
                console.log('결제 위젯 사용 가능');

            } catch (error) {
                console.error('Toss Payments 초기화 실패:', error);
                document.getElementById('error-message').textContent = 'Toss Payments 로딩에 실패했습니다: ' + error.message;
                button.disabled = true;
                button.textContent = '결제 불가';
            }
        }

        // 결제 버튼 클릭 이벤트
        document.getElementById('payment-button').addEventListener('click', async function() {
            const button = this;

            if (!paymentWidget) {
                document.getElementById('error-message').textContent = '결제 위젯이 초기화되지 않았습니다.';
                return;
            }

            if (!isWidgetReady) {
                document.getElementById('error-message').textContent = '결제 위젯이 아직 준비중입니다. 잠시 후 다시 시도해주세요.';
                return;
            }

            button.disabled = true;
            button.textContent = '결제 진행 중...';
            document.getElementById('error-message').textContent = '';

            try {
                console.log('결제 요청 시작...');
                // 결제 요청
                await paymentWidget.requestPayment({
                    orderId: orderId,
                    orderName: orderName,
                    successUrl: successUrl,
                    failUrl: failUrl,
                    customerEmail: 'customer@example.com',
                    customerName: '고객'
                });
            } catch (error) {
                // 에러 처리
                console.error('결제 에러:', error);
                document.getElementById('error-message').textContent = error.message || '결제 요청 중 오류가 발생했습니다.';
                button.disabled = false;
                button.textContent = '결제하기';
            }
        });

        // 페이지 로드 시 초기화
        initializePayment();
    </script>
</body>
</html>
