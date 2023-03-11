# DIDA (개발중)

### 브런치 관리
- feature/~~ (기능개발)
- feature -> develop -> main(release version)
- release/버젼 (출시 후 버젼에 대한 관리)

### 사용기술
- Clean Architecture (Data & Presentation: Android 모듈, Domain: Kotlin & Java 모듈)
- MVVM with AAC ViewModel
- Multi Module With Micro Architecture
- Data binding & View Binding & Extension Function -> Compose(추후 마이그레이션)
- Dagger Hilt
- Coroutine
- LiveData -> Flow(StateFlow, SharedFlow, No Compare StateFlow 마이그레이션 완료)
- Retrofit2 + OkHttp
- List Adapter + DiffUtil
- Glide
- Gson -> Moshi (비교후 도입 예정)
- Paging3
- Fragment Result API
- Jetpack Navigation & Safe args
- DataStore
- Version Catalog (build-logic convention을 활용한 라이브러리 버젼 관리)
- Micro Architecture (Resource 관리 측면)
- Firebase Crashlytics

#### Ui 측면
- Collapsing Toolbar
- Skeleton Ui(Facebook Shimmer)
- Lottie

#### 추후 적용기술
- Unit Test (JUnit등)
- CI/CD (GitAction || Fastline & Firebase || Bitrise & Firebase)
- Compose (순차적으로 업데이트 마이그레이션 예정)
- Firebase Analytics (이벤트 명시 확정 후 도입 예정)
