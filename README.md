# 알콩달콩-BackEnd

## 🖥️ DB 다이어그램
![image](https://github.com/alkong-dalkong/BackEnd/assets/123547179/99e0be92-bc28-44aa-b772-946af79b1b1d)

## 🤝 Branch Convetion

### Git-Flow 전략

- `main` 브랜치
  - main 브랜치는 출시 가능한 프로덕션 코드를 모아두는 브랜치이다.

- `develop` 브랜치
  - 다음 버전 개발을 위한 코드를 모아두는 브랜치이다.
  - 개발이 완료되면, main 브랜치로 머지한다.

- `feature` 브랜치
  - 하나의 기능을 개발하기 위한 브랜치이다.
  - Develop 브랜치에서 생성하며, 기능이 개발 완료되면 다시 Develop 브랜치로 머지된다.
  - 머지할때 주의점은 Fast-Forward로 머지하지 않고, Merge Commit을 생성하며 머지를 해주어야 한다. 이렇게해야 히스토리가 특정 기능 단위로 묶이게 된다.
  - 네이밍은 `feature/branch-name` 과 같은 형태로 생성한다.

<br>

## ⚠️ Commit Convention

```
{태그}: {커밋 메시지}
```

- 💡 예시: `feat: 회원 가입 기능 구현`

### 태그

| 태그       | 설명                           |
|:---------|:-----------------------------|
| feat     | 새로운 기능, 특징 추가                |
| fix      | 수정, 버그 수정                    |
| docs     | 문서에 관련된 내용, 문서 수정            |
| style    | 코드 포맷, 세미콜론 누락, 코드 변경이 없을 경우 |
| refactor | 리팩토링                         |
| test     | 테스트 코드 수정, 누락된 테스트를 추가할 때, 리팩토링 테스트 추가                            |
| chore    | 빌드 업무 수정, 패키지 매니저 수정         |

<br>
