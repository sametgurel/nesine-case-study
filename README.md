# Nesine.com Test Automation Case Study - Monorepo

Bu depo, Nesine.com için geliştirilen REST API ve Web UI test otomasyon projelerini tek bir çatı altında toplayan monorepo yapısıdır. Projeler birbirinden bağımsız olarak derlenebilir, çalıştırılabilir ve raporlanabilir şekilde yapılandırılmıştır.

---

## Proje Modülleri

| Modül | Kapsam | Teknolojiler | Durum |
|---|---|---|---|
| [`nesine-api-automation`](./nesine-api-automation) | Popüler Kuponlar REST API test senaryoları | Java 21, REST Assured, Cucumber BDD, JUnit 5, Allure, Docker | Tamamlandı |
| [`nesine-ui-automation`](./nesine-ui-automation) | Web UI E2E test senaryoları (Popüler Kuponlar, Sepet ve Sayfa Navigasyonu) | Java 21, Selenium WebDriver 4, Cucumber BDD, JUnit 5, PicoContainer, Allure, Docker | Tamamlandı |

---

## Monorepo Dizin Düzeni

```
nesine-case-study/
├── nesine-api-automation/       # REST API test otomasyon modülü
│   ├── src/test/java/           # POJO'lar, Step tanımları, Runner ve Yardımcı sınıflar
│   ├── src/test/resources/      # Gherkin feature dosyaları, JSON şemaları ve konfigürasyon
│   ├── pom.xml                  # API modülü bağımlılıkları ve plugin yapılandırması
│   ├── Dockerfile               # API modülü container tanımı
│   ├── docker-compose.yml       # API test koşumu compose dosyası
│   └── README.md                # Modüle özel çalıştırma rehberi
│
├── nesine-ui-automation/        # Web UI test otomasyon modülü
│   ├── src/test/java/           # Page Objects, Component'ler, Step tanımları ve Driver Factory
│   ├── src/test/resources/      # Gherkin feature dosyaları ve konfigürasyon
│   ├── pom.xml                  # UI modülü bağımlılıkları ve plugin yapılandırması
│   ├── Dockerfile               # UI modülü container tanımı (Headless Chrome + OpenJDK 21)
│   ├── docker-compose.yml       # UI test koşumu compose dosyası
│   └── README.md                # Modüle özel çalıştırma rehberi
│
├── .gitignore                   # Monorepo geneli git yoksayma kuralları
└── README.md                    # Monorepo genel dokümantasyonu
```

---

## Genel Çalıştırma Mantığı

Monorepo içerisindeki her modül kendi dizini altından bağımsız Maven veya Docker komutlarıyla çalıştırılır.

### 1. API Test Modülü

```bash
cd nesine-api-automation

# Testleri çalıştırma:
mvn clean test

# Allure raporunu görüntüleme:
mvn allure:serve

# Docker ile çalıştırma:
docker compose up --build
```

### 2. UI Test Modülü

```bash
cd nesine-ui-automation

# Normal (ekranda tarayıcı açık) modda çalıştırma:
mvn clean test

# Headless modda çalıştırma:
mvn clean test -Dheadless=true

# Allure raporunu görüntüleme:
mvn allure:serve

# Docker ile çalıştırma:
docker compose up --build
```

---

Detaylı teknik parametreler, önkoşullar ve mimari kararlar için ilgili modülün `README.md` dosyasını inceleyebilirsiniz:
- [nesine-api-automation/README.md](./nesine-api-automation/README.md)
- [nesine-ui-automation/README.md](./nesine-ui-automation/README.md)
