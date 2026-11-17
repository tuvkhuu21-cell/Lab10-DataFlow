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
