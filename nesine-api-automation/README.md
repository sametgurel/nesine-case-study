# Nesine.com Popular Coupons REST API Test Automation

Bu modül, Nesine.com Popüler Kuponlar REST API (`GET https://pc.nesine.com/v1/PopularCoupons?eventCount=0`) servisinin uçtan uca fonksiyonel, şema ve iş mantığı doğrulamalarını gerçekleştiren test otomasyon projesidir.

---

## Önkoşullar

- **Java Development Kit (JDK):** 21 veya üzeri
- **Apache Maven:** 3.9+
- **Docker:** (Opsiyonel, container tabanlı koşum için)

---

## Teknoloji Yığını

- **Dil & Derleme:** Java 21 (LTS), Maven
- **Test Kütüphaneleri:** REST Assured 5.5.0, JUnit 5.11.3 (Platform Suite), Cucumber 7.20.1
- **Serileştirme & Şema:** Jackson Databind 2.18.2 (Lombok kullanılmamıştır), JSON Schema Validator
- **Raporlama:** Allure Report 2.29.0
- **Konteyner:** Docker & Docker Compose

---

## Mimari Özellikler

1. **POJO Modeli:** Lombok kullanılmadan standart Java POJO sınıfları kurgulanmıştır. Beklenmeyen dinamik alanların deserialization sürecini etkilememesi için `@JsonIgnoreProperties(ignoreUnknown = true)` kullanılmıştır.
2. **Thread-Safety & Paralel Koşum:** `ScenarioContext` sınıfı içerisinde `ThreadLocal<Map<ContextKey, Object>>` kullanılarak senaryolar arası durum izolasyonu sağlanmıştır.
3. **SpecBuilder:** Request ve Response spesifikasyonları Builder deseniyle tek noktadan yönetilir.
4. **Ortam Yönetimi:** `ConfigManager` sınıfı hem `config.properties` dosyasını okur hem de komut satırından iletilen JVM parametrelerini (`-Denv=...`) dinamik olarak işler.

---

## Testleri Çalıştırma

### Yerel Koşum

```bash
# Tüm testleri çalıştırma:
mvn clean test

# Belirli bir ortam parametresi ile çalıştırma (stage / prod):
mvn clean test -Denvironment=stage

# Belirli bir Cucumber tag'ini çalıştırma:
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Allure Raporu

```bash
# Raporu yerel sunucuda açma:
mvn allure:serve

# Statik HTML raporu derleme:
mvn allure:report
```

### Docker ile Çalıştırma

```bash
# Container içinde testleri koşturma:
docker compose up --build
```
