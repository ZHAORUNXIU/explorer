<!--- PULL REQUEST를 생성하기 전 주석을 제거해 주세요 -->
# Tag info
<!--- Tag 버전 정보를 기입해 주세요-->
<!--- 다음과 같은 양식으로 버전 tag 바랍니다. -->
 - STG 배포버전은 r0.9.12
 - PRD 배포버전은 v0.9.12

# Related Jira Issues
<!--- 관련된 이슈를 나열해 주세요-->


<!-- BUG TEMPLATE BEGIN -->
# Bug
## Bug List
<!--- 버그 발생 원인에 대해서 분석한 내용을 기술해 주세요. 아래는 예시입니다.
- [MCBT-433] : 존재하지 않는 url 접근 시 처리가 되어있지 않았습니다.
- [MCBT-436] : download 링크가 없었습니다.
-->


## Bug 해결방법
<!--- 분석한 원인을 토대로 해결한 방법에 대해서 기술해 주세요. 아래는 예시입니다.
해결방법
[MCBT-433] : 존재하지 않는 url 접근 시 메인화면으로 redirect되게 처리
[MCBT-436] : 전달받은 새로 생성된 download 링크 적용
-->


## 해결 확인 및 Unit Test 결과
<!--- 수정된 사항을 직접 확인한 절차 및 방법에 대해서 기술해 주세요. 아래는 예시입니다.
오류 사이트로 접속 후, 파비콘 확인, 상단 링크 확인, URL 수정 변경 확인
Bridge Convert 진행 후 특이사항 없음
Unit Test 결과를 체크해 주세요.
-->
- [ ] Unit Test 검토 진행

## 고려사항(Risk, Side effect)
<!--- 
Bug fix로 인해 발생할 수 있는 Risk 및 Side effect를 기술해 주세요
밀접한 컴포넌트 및 기능이 Risk 일 수 있습니다.
마켓에 배포된 버전과의 하위 호환성 검토 결과를 기술해 주세요.
이슈를 해결하기 위해 DB 변경이 필요하다면, DB 변경에 따른 리스크 분석 사항과 검토한 Feature 및 Query를
기술해 주세요. 
-->

<!--- BUG TEMPLATE END -->

<!-- FEATURE TEMPLATE BEGIN -->
# Features
## Features List
<!--- 새로운 Feature 대해서 설명해 주세요. 아래는 예시입니다. -->


## 확인 방법
<!--- 새로운 Feature를 확인할 수 있는 방법에 대해서 설명해주세요 -->

## 관련 자료 링크
<!--- 새로운 Feature와 관련된 자료 또는 링크를 추가해 주세요 -->

## 의존성 체크 
<!--- 새로운 Feature에 대한 의존성을 작성해주세요. 아래는 예시입니다.
- ex) gnd api server v0.6.5 이상에서만 호환성이 보장됩니다.
- ex) vpn이 연결된 상황에서만 확인 가능합니다.
마켓에 배포된 버전과의 하위 호환성 검토 결과를 기술해 주세요.
DB가 업데이트 된 경우, 변경에 따른 리스크 분석 사항과 검토한 Feature 및 Query를
기술해 주세요. 
-->


<!-- FEATURE TEMPLATE END -->
