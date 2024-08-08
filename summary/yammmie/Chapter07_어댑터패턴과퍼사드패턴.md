# 어댑터 패턴과 퍼사드 패턴 Adapter Pattern & Facade Pattern 

## 어댑터 패턴

<img width="695" alt="image" src="https://github.com/user-attachments/assets/95039305-3eac-41a8-a80c-2603a4621b16">

- 특정 클래스 인스턴스를 클라이언트에서 요구하는 다른 인터페이스로 변환
- 인터페이스가 호환되지 않아 같이 쓸 수 없었던 클래스를 사용할 수 있게 도와줌
- 두 인터페이스를 모두 지원하는 다중 어댑터 가능
- 종류
    - 객체 어댑터: 구성으로 어댑터에 요청 전달
    - 클래스 어댑터: 다중 상속 필요 + 타깃, 어댑티 모두 서브클래스로 만들어 사용

<br>

### 작동 원리

<img width="791" alt="image" src="https://github.com/user-attachments/assets/afb94f19-4eba-462a-8af6-e8a34aef01db">

- 클라이언트는 타깃 인터페이스에 맞게 구현되어 있음
- 어댑터는 클라이언트로부터 요청을 받아 새로운 업체에서 제공하는 클래스를 클라이언트가 받아들일 수 있는 형태의 요청으로 변환해 주는 중개인 역할
- 어댑터는 타깃 인터페이스를 구현하며, 어댑티 인스턴스가 들어 있음
- 어댑티를 새로 바뀐 인터페이스로 감쌀 때 객체 구성(composition) 사용
    - 장점: 어댑티의 모든 서브클래스에 어댑터 사용 가능
- 클라이언트를 특정 구현이 아닌 인터페이스에 연결 → 서로 다른 백엔드 클래스로 변환시키는 여러 어댑터 사용 + 추후 다른 구현 추가 가능

<br>

### 예시: `Enumeration`과 `Iterator`

```java
public class EnumerationIterator implements Iterator<Object> {
		Enumeration<?> enumeration;
		
		public EnumerationIterator(Enumeration<?> enumeration) {
				this.enumeration = enumeration;
		}
		
		public boolean hasNext() {
				return enumeration.hasMoreElements();
		}
		
		public Object next() {
				return enumeration.nextElement();
		}
		
		public void remove() {
				throw new UnsupportedOperationException();
		}
}
```

- 각 인터페이스의 메소드가 서로 어떻게 대응되는지 확인
- 타깃 인터페이스에는 있지만 어댑티에는 없는 메소드
    - 가장 좋은 방법: 런타임 예외 던질 것
    - `Iterator`의 `remove()`의 경우 `UnsupportedOperationException`을 지원

<br><br>

## 퍼사드 패턴

![image](https://github.com/user-attachments/assets/57af0be6-2781-4d69-804d-93653ef4e93c)


- 어떤 서브시스템에 속한 일련의 복잡한 인터페이스를 단순하게 바꿔서 통합 인터페이스로 묶어줌
- 복잡한 추상화 필요 없음
- 클라이언트와 서브시스템 분리 가능
- 고수준 인터페이스도 정의하므로 서브시스템 더 편리하게 사용 가능
- 서브시스템으로 퍼사드를 만들고 진짜 작업은 서브클래스에게 맡김

<br>

### 작동 원리

<img width="745" alt="image" src="https://github.com/user-attachments/assets/c92b5080-976e-4d04-bedd-4aeeff6478f6">


1. 홈시어터 시스템용 퍼사드 생성
    1. `watchMovie()`와 같이 몇 가지 간단한 메소드만 들어있는 `HomeTheaterFacade` 클래스 생성
2. 퍼사드 클래스는 홈시어터 구성 요소를 하나의 서브시스템으로 간주하고, `watchMovie()` 메소드는 서브시스템의 메소드를 호출해 필요한 작업 처리
3. 클라이언트 코드는 서브시스템 대신 홈시어터 퍼사드의 메소드 호출
    1. `watchMovie()`만 호출하면 조명, 스트리밍 플레이어, 프로젝터, 앰프, 스크린, 팝콘 기계 등 알아서 준비됨
4. **퍼사드를 사용해도 여전히 서브시스템에 접근 가능!**

<br>

### 특징

- 서브시스템 클래스를 캡슐화하지 않고, 간단한 인터페이스를 제공할 뿐
- 특정 서브시스템에 대해 만들 수 있는 퍼사드의 개수 제한 없음

<br><br>

## 어댑터 패턴과 퍼사드 패턴

- **둘의 차이점은 감싸는 클래스의 개수가 아니라 그 용도에 있음**
- 어댑터 패턴은 인터페이스를 ‘변환’해서 클라이언트에서 필요로 하는 인터페이스로 적응시키는 용도
- 퍼사드 패턴은 어떤 서브시스템에 대한 ‘간단’한 인터페이스를 제공하는 용도

<br><br>

## 최소 지식 원칙(Principle of Least Knowledge) = 데메테르 법칙

> **📍 디자인 원칙

진짜 절친에게만 이야기해야 한다.**
> 
- 어떤 객체든 그 객체와 상호작용하는 클래스의 개수와 상호작용 방식에 주의를 기울여야 함
- 이 원칙을 잘 따르면 여러 클래스가 복잡하게 얽혀 있어서 시스템의 한 부분을 변경했을 때 다른 부분까지 수정해야 하는 상황 방지 가능
- 단점: 메소드 호출을 처리하는 ‘래퍼’ 클래스를 많이 만들어야 할 수 있음 → 개발 시간 증가, 성능 저하

<br>

### 호출 가능한 가이드라인

- 객체 자체
- 메소드에 매개변수로 전달된 객체
- 메소드를 생성하거나 인스턴스를 만든 객체
- 객체에 속하는 구성 요소

<br>

### 메소드 호출한 결과로 리턴받은 객체에 들어있는 메소드 호출 시 단점

- 다른 객체의 일부분에 요청하게 되고, 직접적으로 알고 지내는 객체의 수 증가

⇒ 객체가 대신 요청하도록 만들어야 함

- 원칙 따르지 않은 경우
    - `station`으로부터 `thermometer` 객체를 받은 다음 그 객체의 `getTemperature()` 호출

```java
public float getTemp() {
		Thermometer thermometer = station.getThermometer();
		return thermometer.getTemperature();
}
```

- 원칙 따른 경우
    - `thermometer`에게 요청 전달하는 메소드를 `Station` 클래스에 추가

```java
public float getTemp() {
		return station.getTemperature();
}
```

<br>

### 예시

```java
public class Car {
		Engine engine;  // 1
		// 기타 인스턴스 변수
		
		public Car() {
				// 엔진 초기화 등 처리
		}
		
		public void start(Key key) {  // 2
				Doors doors = new Doors();  // 3
				boolean authorized = key.turns();  // 4
				if(authorized) {
						engine.start();  // 5
						updateDashboardDisplay();  // 6
						doors.lock();  // 7
				}
		}
		
		public void updateDashboardDisplay() {
				// 디스플레이 갱신
		}
}
```

- 1, 5: 이 클래스의 구성 요소 → 구성 요소의 메소드 호출 가능
- 2, 4: 매개변수로 전달된 객체 → 매개변수의 메소드 호출 가능
- 3, 7: 새 객체 생성 → 새 객체의 메소드 호출 가능
- 6: 객체 내에 있는 메소드 호출 가능
