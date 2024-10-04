1. 주요 기능
    일정 생성 : 새로운 일정을 생성
    일정 조회 : 모든 일정을 조회 혹은 특정 일정 조회
    일정 수정 : 일정의 정보를 업데이트
    일정 삭제 : 특정 일정을 id 기반으로 삭제 

2. postman 을 사용해서 application 점검 

3. api 앤드 포인트 
     
        1. 일정 생성 : /api/schedules

        2. 전체 일정 조회  : /api/schedules
        
        3. 일정 수정 : /api/schedules/{id}
        
        4. 일정 삭제 : /api/schedules/{id}

4. 예외 처리
    일정이 존재하지 않을 경우 404 오류를 반환한다. 

5. 파일 구조 
src
┣ main
┃ ┣ java
┃ ┃ ┗ com
┃ ┃ ┃ ┗ sparta
┃ ┃ ┃ ┃ ┗ schedule
┃ ┃ ┃ ┃ ┃ ┣ controller
┃ ┃ ┃ ┃ ┃ ┃ ┗ ScheduleController.java
┃ ┃ ┃ ┃ ┃ ┣ dto
┃ ┃ ┃ ┃ ┃ ┃ ┣ ScheduleRequestDto.java
┃ ┃ ┃ ┃ ┃ ┃ ┗ ScheduleResponseDto.java
┃ ┃ ┃ ┃ ┃ ┣ entity
┃ ┃ ┃ ┃ ┃ ┃ ┗ Schedule.java
┃ ┃ ┃ ┃ ┃ ┣ repository
┃ ┃ ┃ ┃ ┃ ┃ ┗ ScheduleRepository.java
┃ ┃ ┃ ┃ ┃ ┣ service
┃ ┃ ┃ ┃ ┃ ┃ ┗ ScheduleService.java
┃ ┃ ┃ ┃ ┃ ┗ ScheduleApplication.java
┃ ┗ resources
┃ ┃ ┣ static
┃ ┃ ┣ templates
┃ ┃ ┗ application.properties
┗ test
┃ ┗ java
┃ ┃ ┗ com
┃ ┃ ┃ ┗ sparta
┃ ┃ ┃ ┃ ┗ schedule
┃ ┃ ┃ ┃ ┃ ┗ ScheduleApplicationTests.java