
# Лабораторийн ажил №10
## Өгөгдлийн урсгалын тест — Definition-Use хосууд

**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест  
**Оюутан:** Т.Түвшинтөр  
**Оюутны код:** B190910820  
**Орчин:** Ubuntu WSL2

## 1. Ашигласан хэрэгслүүд

**Java (`java -version`):**
```text
openjdk version "17.0.20.1" 2026-08-18
OpenJDK Runtime Environment (build 17.0.20.1+1-1-26.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20.1+1-1-26.04-Ubuntu, mixed mode, sharing)
```

**Maven (`mvn -version`):**
```text
Apache Maven 3.9.12
Maven home: /usr/share/maven
Java version: 17.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.6.87.2-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```

## 2. Part A — gcd() функцийн DU анализ

### A1. Definition ба Use

| Хувьсагч | Definition | c-use | p-use |
|---|---|---|---|
| x | 13, 17 | 16, 20 | — |
| y | 13, 18 | 16, 17 | 15 |
| tmp | 16 | 18 | — |

### A2. DU хосууд ба def-clear зам

| Хувьсагч | Def→Use | Төрөл | Def-clear зам | Тест |
|---|---|---|---|---|
| x | 13→16 | c-use | 13→15→16 | gcdOneIteration |
| x | 13→20 | c-use | 13→15→20 | gcdZeroIterations |
| x | 17→16 | c-use | 17→18→15→16 | gcdMultipleIterations |
| x | 17→20 | c-use | 17→18→15→20 | gcdOneIteration |
| y | 13→15 | p-use | 13→15 | gcdZeroIterations |
| y | 13→16 | c-use | 13→15→16 | gcdOneIteration |
| y | 13→17 | c-use | 13→15→16→17 | gcdOneIteration |
| y | 18→15 | p-use | 18→15 | gcdOneIteration |
| y | 18→16 | c-use | 18→15→16 | gcdMultipleIterations |
| y | 18→17 | c-use | 18→15→16→17 | gcdMultipleIterations |
| tmp | 16→18 | c-use | 16→17→18 | gcdOneIteration |

Бүх 11 мөрийн түвшний DU хосыг тестээр хамруулсан. `y`-ийн 15-р мөрийн p-use нь давталтын үнэн болон худал салаанд хамаарна.

### A3. JUnit тестүүд

| Тест | Оролт | Хүлээгдсэн үр дүн |
|---|---|---|
| gcdZeroIterations | gcd(7,0) | 7 |
| gcdOneIteration | gcd(0,5) | 5 |
| gcdMultipleIterations | gcd(12,8) | 4 |
| gcdFourIterations | gcd(8,5) | 1 |

Эдгээр тестүүд давталтгүй, нэг болон олон давталттай замуудыг шалгана.

## 3. Part B — bonus() функцийн DU анализ

### B1. Control Flow Graph

CFG зураг: **`cfg-bonus.png`**

Зурагт 32-р мөрийн short-circuit нөхцөл, 38-р мөрийн for-each давталт, 39, 42, 50, 53-р мөрийн салааллуудыг харуулсан. Зангилаанууд эх кодын мөрийн дугаартай бөгөөд ирмэгүүдэд тухайн замыг гүйцэтгэх тестийн нэрийг тэмдэглэсэн.

### B2. Definition ба Use

| Мөр | Definition | c-use | p-use |
|---|---|---|---|
| 31 | attendance, threshold | — | — |
| 32 | — | — | attendance, threshold |
| 35 | present | — | — |
| 36 | streak | — | — |
| 37 | best | — | — |
| 38 | a | — | attendance |
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

**Def-clear зам** гэдэг нь definition-ээс use хүртэл тухайн хувьсагч дахин тодорхойлогдоогүй зам юм. Шинэ definition үүсэхэд өмнөх definition kill болно.

### B3. DU хосууд

| Хувьсагч | Def→Use | Төрөл | Def-clear зам | Тест |
|---|---|---|---|---|
| present | 35→40 | c-use | 35→38(T)→39(T)→40 | bonusAllDefsNoReward |
| present | 35→50 | p-use | 35→38(F)→49→50 | bonusEmptyAttendance |
| present | 40→40 | c-use | 40→41→42→38(T)→39(T)→40 | bonusAllDefsFullReward |
| present | 40→50 | p-use | 40→41→42→38(F)→49→50 | bonusAllDefsNoReward |
| attendance | 31→32 | p-use | 31→32 | bonusNullAttendance |
| attendance | 31→38 | p-use | 31→32(F)→35→36→37→38 | bonusEmptyAttendance |
| threshold | 31→32 | p-use | 31→32a(F)→32b | bonusNegativeThreshold |
| threshold | 31→50 | p-use | 31→32(F)→35→36→37→38(F)→49→50 | bonusAllDefsNoReward |
| a | 38→39 | p-use | 38→39 | bonusAllDefsNoReward |
| streak | 36→41 | c-use | 36→38(T)→39(T)→40→41 | bonusAllDefsNoReward |
| streak | 41→41 | c-use | 41→42→38(T)→39(T)→40→41 | bonusAllDefsFullReward |
| streak | 46→41 | c-use | 46→38(T)→39(T)→40→41 | bonusAllDefsNoReward |
| streak | 41→42 | p-use | 41→42 | bonusAllDefsFullReward |
| streak | 41→43 | c-use | 41→42(T)→43 | bonusAllDefsFullReward |
| best | 37→42 | p-use | 37→38(T)→39(T)→40→41→42 | bonusAllDefsFullReward |
| best | 37→53 | p-use | 37→38(F)→49→50→53 | bonusEmptyAttendance |
| best | 43→42 | p-use | 43→38(T)→39(T)→40→41→42 | bonusAllDefsFullReward |
| best | 43→53 | p-use | 43→38(F)→49→50→53 | bonusAllDefsFullReward |
| score | 49→54 | c-use | 49→50(F)→53(T)→54 | bonusLongStreakBelowThreshold |
| score | 49→56 | c-use | 49→50(F)→53(F)→56 | bonusAllDefsNoReward |
| score | 51→54 | c-use | 51→53(T)→54 | bonusAllDefsFullReward |
| score | 51→56 | c-use | 51→53(F)→56 | bonusAttendanceThresholdOnly |
| score | 54→56 | c-use | 54→56 | bonusAllDefsFullReward |

**Infeasible хосууд:** `streak`-ийн 36→42 болон 46→42 хосууд def-clear замгүй. Учир нь 42-р мөрөнд хүрэхээс өмнө 41-р мөрөнд `streak` дахин тодорхойлогдоно.

**Kill анализ:** 41-р мөр `streak`-ийг нэмэгдүүлж, 46-р мөр тэглэнэ. 43-р мөр `best`-ийг шинэчилнэ. `present`-ийн 40→40 болон `streak`-ийн 41→41 хосууд давталтын өөр өөр гүйцэтгэлийн хооронд үүснэ.

### B4. Predicate-use салааны хамралт

| Мөр | Нөхцөл | True тест | False тест |
|---|---|---|---|
| 32a | attendance == null | bonusNullAttendance | bonusNegativeThreshold |
| 32b | threshold < 0 | bonusNegativeThreshold | bonusAllDefsNoReward |
| 38 | for-each | bonusAllDefsFullReward | bonusEmptyAttendance |
| 39 | a > 0 | bonusAllDefsFullReward | bonusNoAttendance |
| 42 | streak > best | bonusAllDefsFullReward | bonusAllDefsNoReward |
| 50 | present >= threshold | bonusAllDefsFullReward | bonusAllDefsNoReward |
| 53 | best >= 5 | bonusAllDefsFullReward | bonusAllDefsNoReward |

32-р мөрийн OR нөхцөл short-circuit үнэлгээтэй. `attendance == null` үед `threshold < 0` нөхцөл үнэлэгдэхгүй.

### B5. Тестийн үе шат ба coverage

| Үе шат | Bonus тест | Нийт тест | Branch coverage |
|---|---:|---:|---|
| All-defs | 2 | 6 | 14/16 |
| All-DU-pairs | 8 | 12 | 16/16 |

**All-defs нотлох файлууд:**
- `results/mvn-verify-alldefs.txt`
- `results/jacoco-alldefs.csv`

**All-DU-pairs нотлох файлууд:**
- `results/mvn-verify-alldupairs.txt`
- `results/jacoco-alldupairs.csv`

All-defs үе шатанд definition-үүдийн хэрэглээг шалгасан. All-DU-pairs шатанд нэмэлт зам, нөхцөлүүдийг шалгаж, branch coverage-ийг 100%-д хүргэсэн.

## 4. Part C — Mutation Testing

### C1. Mutation-ийн үр дүн

| Mutation | Өөрчлөлт | Тестийн үр дүн | Статус |
|---|---|---|---|
| M1 | `streak = streak + 1;` хассан | 12 тестээс 2 унасан | KILLED |
| M2 | `best = streak;` нөхцөлтэй хэсгийг `best = present;` болгосон | Эхний 12 тест амжилттай, шинэ тест нэмэхэд 1 унасан | KILLED |
| M3 | `int total = present + best;` нэмсэн | 13 тест амжилттай | SURVIVED |

**M1:** Дараалсан ирцийн тооцооллыг эвдэхэд хоёр тест алдаа илрүүлсэн.

**M2:** Эхний тестүүд нийт ирц болон дараалсан ирцийн ялгааг илрүүлээгүй. `bonusSeparatedAttendanceNoStreakReward()` тест нэмсэн. Нийт 5 ирцтэй, хамгийн урт дараалсан ирц 1 байх үед зөв код 0, mutation код 3 оноо буцаадаг. Шинэ тестээр M2-г KILLED болгосон.

**M3:** Ашиглагдахгүй `total` хувьсагч буцаах утгад нөлөөлөөгүй тул тестүүд алдаа илрүүлээгүй. Ийм асуудлыг IDE-ийн анхааруулга эсвэл статик шинжилгээгээр илрүүлэх боломжтой.

**Нотлох файлууд:**
- `results/mvn-test-M1.txt`
- `results/mvn-test-M2.txt`
- `results/mvn-test-M2-kill.txt`
- `results/mvn-test-M3.txt`

Mutation бүрийн дараа `DataFlowTargets.java`-г анхны хувилбарт сэргээсэн. Mutation өөрчлөлтүүдийг Git-д commit хийгээгүй.

### C2. Эцсийн баталгаажуулалт

| Үзүүлэлт | Үр дүн |
|---|---|
| Нийт JUnit тест | 13 |
| Failures / Errors | 0 / 0 |
| Maven | BUILD SUCCESS |
| JaCoCo branch coverage | 16/16 (100%) |
| JaCoCo line coverage | 23/24 |

Эцсийн нотлох файлууд:
- `results/mvn-verify-final.txt`
- `results/jacoco-final.csv`

Хамрагдаагүй нэг мөр нь `DataFlowTargets` классын implicit constructor юм.

## 5. Дүгнэлт

Энэ лабораторийн ажлаар `gcd()` болон `bonus()` функцүүдийн өгөгдлийн урсгалыг шинжилж, definition-use хосуудыг тодорхойлсон. Part A-д 11 мөрийн түвшний DU хосыг дөрвөн JUnit тестээр шалгасан. Part B-д CFG байгуулж, all-defs болон all-DU-pairs шалгуураар тестүүдийг өргөжүүлсэн. All-defs үе шатны үр дүнг тусад нь хадгалж, дараагийн шатанд branch coverage-ийг 100%-д хүргэсэн. Part C-д M1 алдааг анхны тестүүд илрүүлсэн боловч M2 алдаанд нэмэлт тест шаардлагатай болсон. M3 нь програмын үр дүнд нөлөөлөхгүй тул динамик тестээс илүү статик шинжилгээ тохиромжтойг харуулсан. Mutation бүрийн дараа үндсэн кодыг сэргээж, өөрчлөлтүүдийг commit хийгээгүй. Эцэст нь бүх 13 тест амжилттай ажиллаж, Maven болон JaCoCo тайлангаар үр дүнг баталгаажуулсан.

## 6. AI хэрэгслийн ашиглалт

AI хэрэгслийг DU хосуудын тайлбар, тестийн хувилбарууд, терминалын команд болон README-ийн бүтэц боловсруулахад туслах зорилгоор ашигласан. Бүх тест, mutation болон coverage-ийн үр дүнг WSL Ubuntu орчинд ажиллуулж шалгасан.
