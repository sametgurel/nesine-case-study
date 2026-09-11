# Nesine.com Web UI Test Automation

Bu modül, Nesine.com web uygulamasının Popüler Kuponlar, Sepet (Betslip) ve ana navigasyon akışlarını uçtan uca (E2E) test eden otomasyon projesidir.

---

## Önkoşullar

- **Java Development Kit (JDK):** 21 veya üzeri
- **Apache Maven:** 3.9+
- **Google Chrome:** Güncel sürüm (Firefox ve Edge tarayıcıları da desteklenmektedir)
- **Docker:** (Opsiyonel, container tabanlı koşum için)

---

## Teknoloji Yığını

- **Dil & Derleme:** Java 21 (LTS), Maven
- **Otomasyon Motoru:** Selenium WebDriver 4.29.0, WebDriverManager 5.9.3 (Harici `.exe` bağımlılığı bulunmaz)
- **BDD & Test Motoru:** Cucumber 7.20.1, JUnit 5.11.3 (Platform Suite)
- **Bağımlılık Enjeksiyonu (DI):** Cucumber PicoContainer 7.20.1 (Step sınıfları arası durum paylaşımı)
- **Raporlama:** Allure Report 2.29.0, Logback & SLF4J
- **Konteyner:** Docker & Docker Compose (Headless Chrome + OpenJDK 21)

---

## Mimari Özellikler

1. **Katı Page Object Model (POM):** Sayfa elementleri (`pages/`), senaryo adımları (`stepdefinitions/`) ve test senaryoları (`features/`) kesin sınırlarla ayrıştırılmıştır.
2. **Component Deseni:** Sayfa geçişlerinde durumu korunan Sepet yapısı `BetslipComponent` adı altında bağımsız bir bileşen olarak modellenmiştir.
3. **PicoContainer State Sharing:** Adımlar arasında seçilen kupon bilgileri (maç isimleri, maç tarihleri) statik değişkenler yerine `TestContext` sınıfı üzerinden dependency injection ile aktarılır.
4. **ThreadLocal & Paralel Koşum:** `DriverFactory` sınıfında `ThreadLocal<WebDriver>` kullanılarak paralel koşumlarda session çakışmaları engellenmiştir. `Hooks` sınıfı test bitiminde `driver.quit()` ve `driver.remove()` çağrılarını icra eder.
5. **Senkronizasyon (Sıfır Thread.sleep):** Tüm bekleme ve etkileşimler `WaitUtils` yardımcı sınıfı içerisindeki `WebDriverWait` (Explicit Wait) metotlarıyla yönetilmiştir.

---

## Testleri Çalıştırma

### Yerel Koşum

```bash
# Normal (ekranda tarayıcı görünür) modda çalıştırma:
mvn clean test

# Headless modda çalıştırma:
mvn clean test -Dheadless=true

# Farklı tarayıcı seçimi (firefox / edge):
mvn clean test -Dbrowser=firefox -Dheadless=true
mvn clean test -Dbrowser=edge -Dheadless=true

# Belirli bir Cucumber tag'ini çalıştırma:
mvn clean test -Dcucumber.filter.tags="@case_study"
mvn clean test -Dcucumber.filter.tags="@extended"
```

### Allure Raporu

```bash
# Raporu yerel web sunucusunda canlı açma:
mvn allure:serve

# Statik HTML raporu derleme:
mvn allure:report
```

### Docker ile Çalıştırma

```bash
# Headless Chrome yüklü container içinde testleri koşturma:
docker compose up --build
```
