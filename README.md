# Лабораторийн ажил №10
## Өгөгдлийн урсгалын тест — Definition-Use хосууд

**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест  
**Оюутан:** Т.Түвшинтөр  
**Оюутны код:** B190910820  
**Орчин:** Ubuntu WSL2

## Ашигласан хэрэгслүүд

### Java хувилбар

```text
openjdk version "17.0.20.1" 2026-08-18
OpenJDK Runtime Environment (build 17.0.20.1+1-1-26.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20.1+1-1-26.04-Ubuntu, mixed mode, sharing)
```

### Maven хувилбар

```text
Apache Maven 3.9.12
Maven home: /usr/share/maven
Java version: 17.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.6.87.2-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```

## Хэсэг A — gcd() функцийн өгөгдлийн урсгалын анализ

### A1. Definition ба Use цэгүүд

| Хувьсагч | Definition | c-use | p-use |
|---|---|---|---|
| x | 13, 17 | 16, 20 | — |
| y | 13, 18 | 16, 17 | 15 |
| tmp | 16 | 18 | — |

### A2. DU хосууд

| Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|
| x | 13 | 16 | c-use | 13→15→16 | gcdOneIteration | Тийм |
| x | 13 | 20 | c-use | 13→15→20 | gcdZeroIterations | Тийм |
| x | 17 | 16 | c-use | 17→18→15→16 | gcdMultipleIterations | Тийм |
| x | 17 | 20 | c-use | 17→18→15→20 | gcdOneIteration | Тийм |
| y | 13 | 15 | p-use | 13→15 | gcdZeroIterations | Тийм |
| y | 13 | 16 | c-use | 13→15→16 | gcdOneIteration | Тийм |
| y | 13 | 17 | c-use | 13→15→16→17 | gcdOneIteration | Тийм |
| y | 18 | 15 | p-use | 18→15 | gcdOneIteration | Тийм |
| y | 18 | 16 | c-use | 18→15→16 | gcdMultipleIterations | Тийм |
| y | 18 | 17 | c-use | 18→15→16→17 | gcdMultipleIterations | Тийм |
| tmp | 16 | 18 | c-use | 16→17→18 | gcdOneIteration | Тийм |

### A3. Тестийн тайлбар

- `gcdZeroIterations`: gcd(7,0)=7. Давталтад орохгүй, x-ийн 13→20 хосыг хамарна.
- `gcdOneIteration`: gcd(0,5)=5. Нэг давталт хийж, tmp-ийн 16→18 хосыг хамарна.
- `gcdMultipleIterations`: gcd(12,8)=4. Хоёр давталт хийж, дахин тодорхойлогдсон x, y-ийн DU хосуудыг хамарна.
- `gcdFourIterations`: gcd(8,5)=1. Дөрвөн давталт хийж, давтагдсан def-clear замуудыг шалгана.

### A4. Дүгнэлт

gcd() функцийн x, y, tmp хувьсагчдын нийт 11 мөрийн түвшний DU хосыг тодорхойлсон. Дөрвөн JUnit тестээр эдгээр хосыг хамруулсан. gcd(7,0) нь x-ийн анхны definition-ээс return хүртэлх замыг давталтгүйгээр шалгадаг. gcd(12,8) нь хоёр давталт хийж, давталтын явцад дахин тодорхойлогдсон хувьсагчдын хэрэглээг шалгадаг.

## Хэсэг B — bonus() функцийн өгөгдлийн урсгалын анализ

### B1. Control Flow Graph (CFG)

`cfg-bonus.png` файлд bonus() функцийн удирдлагын урсгалыг дүрсэлсэн.

CFG-д 32-р мөрийн short-circuit нөхцөл, 38-р мөрийн for-each давталтын нөхцөл болон a хувьсагчийн definition, 39, 42, 50, 53-р мөрүүдийн салааллыг тусгасан.

CFG-ийн ирмэгүүдэд тестийн бодит нэрийг B4 тестүүдийг дуусгасны дараа нэмнэ.

### B2. Definition ба Use цэгүүд

| Мөр | Definition (def) | c-use | p-use |
|---|---|---|---|
| 31 | attendance, threshold | — | — |
| 32 | — | — | attendance, threshold |
| 35 | present | — | — |
| 36 | streak | — | — |
| 37 | best | — | — |
| 38 | a (дараагийн элемент) | — | attendance (давталтын нөхцөл) |
| 39 | — | — | a |
| 40 | present | present | — |
| 41 | streak | streak | — |
| 42 | — | — | streak, best |
| 43 | best | streak | — |
| 46 | streak | — | — |
| 49 | score | — | — |
| 50 | — | — | present, threshold |
| 51 | score | — | — |
| 53 | — | — | best |
| 54 | score | score | — |
| 56 | — | score | — |

#### Тайлбар

- **Definition (def):** Хувьсагчид анх эсвэл дахин утга оноох.
- **Computational use (c-use):** Хувьсагчийн утгыг тооцоолол, оноолт, return-д ашиглах.
- **Predicate use (p-use):** Нөхцөл шалгахад хувьсагчийн утгыг ашиглах.
- **Kill:** Нэг хувьсагчид шинэ утга онооход өмнөх definition-ээс үргэлжлэх def-clear зам тасарна.
- **Def-clear path:** Тухайн definition-ээс use хүртэлх замд ижил хувьсагч дахин тодорхойлогдоогүй байх зам.

46-р мөрийн `streak = 0` нь өмнөх streak-ийн definition-ийг kill хийнэ. 41-р мөрийн `streak = streak + 1` нь өмнөх утгыг c-use хийсний дараа шинэ definition үүсгэнэ.

32-р мөрийн `attendance == null || threshold < 0` нөхцөл short-circuit үнэлгээтэй. attendance null бол threshold-ийн p-use-д хүрэхгүй.

38-р мөрийн for-each давталтын нөхцөл attendance-д дараагийн элемент байгаа эсэхийг шалгах p-use, дараагийн элементийг a-д оноох нь def юм.

### B3. Definition-Use (DU) хосууд

DU хосыг тодорхойлохдоо тухайн definition-ээс use хүртэл ижил хувьсагч дахин тодорхойлогдоогүй (def-clear) замыг авч үзсэн. Давталтын өөр өөр гүйцэтгэлийн хооронд нэг мөрөөс өөрт нь очих DU хос үүсэж болно.

#### B3.1. present, attendance, threshold, a

| Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|
| present | 35 | 40 | c-use | 35→38(T)→39(T)→40 | bonusAllDefsNoReward | Тийм |
| present | 35 | 50 | p-use | 35→38(F)→49→50 | bonusEmptyAttendance | Тийм |
| present | 40 | 40 | c-use | 40→41→42→38(T)→39(T)→40 | bonusAllDefsFullReward | Тийм |
| present | 40 | 50 | p-use | 40→41→42→38(F)→49→50 | bonusAllDefsNoReward | Тийм |
| attendance | 31 | 32 | p-use | 31→32 | bonusNullAttendance | Тийм |
| attendance | 31 | 38 | p-use | 31→32(F)→35→36→37→38 | bonusEmptyAttendance | Тийм |
| threshold | 31 | 32 | p-use | 31→32a(F)→32b | bonusNegativeThreshold | Тийм |
| threshold | 31 | 50 | p-use | 31→32(F)→35→36→37→38(F)→49→50 | bonusAllDefsNoReward | Тийм |
| a | 38 | 39 | p-use | 38(элемент авах)→39 | bonusAllDefsNoReward | Тийм |

**Тайлбар:**

- `present`-ийн 40→40 хос нь хоёр өөр давталтын хооронд үүснэ. Хоёр ирсэн өдөр заавал дараалсан байх шаардлагагүй.
- `attendance`-ийн 31→38 хос нь массив null биш үед үүснэ. Давталтын нөхцөлийн T/F хоёр салааг тусад нь шалгана.
- `threshold`-ийн 31→32 хос нь `attendance != null` үед л үнэлэгдэнэ. Учир нь 32-р мөр short-circuit нөхцөлтэй.
- `a` хувьсагч давталт бүрийн эхэнд дахин тодорхойлогдож, 39-р мөрөнд p-use хийгдэнэ.
- `present`-ийн 35→50 зам нь зөвхөн хоосон массив биш, бүх элемент нь тасалсан массивын үед ч биелнэ.


#### B3.2. streak хувьсагч

| Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|
| streak | 36 | 41 | c-use | 36→38(T)→39(T)→40→41 | bonusAllDefsNoReward | Тийм |
| streak | 41 | 41 | c-use | 41→42→38(T)→39(T)→40→41 | bonusAllDefsFullReward | Тийм |
| streak | 46 | 41 | c-use | 46→38(T)→39(T)→40→41 | bonusAllDefsNoReward | Тийм |
| streak | 41 | 42 | p-use | 41→42 | bonusAllDefsFullReward | Тийм |
| streak | 36 | 42 | p-use | Def-clear замгүй | — | Биелэхгүй |
| streak | 46 | 42 | p-use | Def-clear замгүй | — | Биелэхгүй |
| streak | 41 | 43 | c-use | 41→42(T)→43 | bonusAllDefsFullReward | Тийм |

**Kill анализ:**

- 41-р мөрийн `streak = streak + 1` нь өмнөх утгыг c-use хийсний дараа шинэ definition үүсгэнэ.
- 46-р мөрийн `streak = 0` нь өмнөх 36 эсвэл 41-р мөрийн definition-ийг kill хийнэ.
- 46→41 хос нь тасалсан өдрийн дараа ирсэн өдөр тохиолдоход биелнэ.
- 41→41 хос нь хоёр дараалсан ирсэн өдөр тохиолдоход биелнэ.
- 36→42 болон 46→42 нь DU хос биш. Учир нь 42-р мөрөнд хүрэхээс өмнө 41-р мөр streak-ийг дахин тодорхойлдог.

#### B3.3. best хувьсагч

| Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|
| best | 37 | 42 | p-use | 37→38(T)→39(T)→40→41→42 | bonusAllDefsFullReward | Тийм |
| best | 37 | 53 | p-use | 37→38(F)→49→50→53 | bonusEmptyAttendance | Тийм |
| best | 43 | 42 | p-use | 43→38(T)→39(T)→40→41→42 | bonusAllDefsFullReward | Тийм |
| best | 43 | 53 | p-use | 43→38(F)→49→50→53 | bonusAllDefsFullReward | Тийм |

**Тайлбар:**

- 43-р мөрөнд best шинэчлэгдэхэд 37 эсвэл өмнөх 43-р мөрийн definition kill болно.
- 43→42 нь дараагийн ирсэн өдрийн үед биелнэ.
- 37→53 нь хоосон массив эсвэл бүх өдөр тасалсан үед биелнэ.
- 43→53 нь хамгийн урт дараалсан ирц тогтоогдсоны дараа биелнэ.

#### B3.4. score хувьсагч

| Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|
| score | 49 | 54 | c-use | 49→50(F)→53(T)→54 | bonusLongStreakBelowThreshold | Тийм |
| score | 49 | 56 | c-use | 49→50(F)→53(F)→56 | bonusAllDefsNoReward | Тийм |
| score | 51 | 54 | c-use | 51→53(T)→54 | bonusAllDefsFullReward | Тийм |
| score | 51 | 56 | c-use | 51→53(F)→56 | bonusAttendanceThresholdOnly | Тийм |
| score | 54 | 56 | c-use | 54→56 | bonusAllDefsFullReward | Тийм |

**Тайлбар:**

- 49→54 хос нь `present < threshold` боловч `best >= 5` үед биелнэ.
- 49→56 хос нь хоёр урамшууллын нөхцөл хоёулаа худал үед биелнэ.
- 51→54 хос нь хоёр урамшууллын нөхцөл хоёулаа үнэн үед биелнэ.
- 51→56 хос нь зөвхөн ирцийн босго хангагдсан үед биелнэ.
- 54→56 хос нь дараалсан ирцийн нэмэлт 3 оноо олгогдсон үед биелнэ.


#### B3.5. Predicate-use салааны хамралт

| Мөр | Нөхцөл | T салаа | F салаа |
|---|---|---|---|
| 32a | attendance == null | bonusNullAttendance | bonusNegativeThreshold |
| 32b | threshold < 0 | bonusNegativeThreshold | bonusAllDefsNoReward |
| 38 | for-each давталт | bonusAllDefsFullReward | bonusEmptyAttendance |
| 39 | a > 0 | bonusAllDefsFullReward | bonusNoAttendance |
| 42 | streak > best | bonusAllDefsFullReward | bonusAllDefsNoReward |
| 50 | present >= threshold | bonusAllDefsFullReward | bonusAllDefsNoReward |
| 53 | best >= 5 | bonusAllDefsFullReward | bonusAllDefsNoReward |

32-р мөрийн OR нөхцөл short-circuit үнэлгээтэй. `attendance == null` үед `threshold < 0` нөхцөлийг шалгахгүй.

JaCoCo-ийн branch coverage 16/16 болсон. Энэ хүснэгт нь нөхцөлийн салааны хамралтыг харуулна. DU хосуудын def-clear зам болон тестийн нэрсийг B3.1–B3.4 хүснэгтүүдэд тусгасан.

