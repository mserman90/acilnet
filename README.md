# MeshLine — Afet Durumlarında Mesh Tabanlı Acil Haberleşme Ağı

GSM, Wi-Fi ve internet altyapısı çöktüğünde çalışan; BLE ve Wi-Fi üzerinden cihazdan cihaza kurulan mesh ağı ile deprem sonrası anlık iletişim sağlayan Android uygulaması.

TOBB Ekonomi ve Teknoloji Üniversitesi, Bilgisayar Mühendisliği Bölümü **BIL495 Bitirme Projesi**.

**[Proje sitesini görüntüle →](https://ubbx05.github.io/Afet-Durumlar-nda-Mesh-Tabanl-Acil-Haberle-me-A-/)**

## Proje Hakkında

Deprem gibi afet durumlarında baz istasyonları ve internet omurgası devre dışı kaldığında klasik iletişim yöntemleri tamamen kesilir. MeshLine, herhangi bir sunucuya veya ek donanıma ihtiyaç duymadan, cihazları birbirinin altyapısı haline getirerek bu sorunu çözmeyi hedefler.

- **Taşıma katmanı:** BLE (IEEE 802.15.1) + Wi-Fi üzerinden cihazdan cihaza keşif ve bağlantı
- **Yönlendirme:** AODV tabanlı (IETF RFC 3561) özel mesh routing
- **Güvenlik:** X.509 / PKI tabanlı uçtan uca şifreleme (RFC 5280, X25519 / RFC 7748)
- **Platform:** Android (iOS'un arka planda BLE kısıtlamaları nedeniyle kapsam dışı bırakıldı)

## Raporlar

Tüm proje dokümantasyonuna (PDF ve DOCX) [proje sitesindeki Raporlar bölümünden](https://ubbx05.github.io/Afet-Durumlar-nda-Mesh-Tabanl-Acil-Haberle-me-A-/#raporlar) erişilebilir:

- Proje Önerisi
- Proje Özellikleri Raporu
- Analiz Raporu
- Üst Seviye Tasarım Raporu
- Kısıt ve Etkiler Planı (PKE)

## Takım

| İsim | Sorumluluk |
|---|---|
| Umut Baran Boztaş | BLE Protokolü |
| Kaan Behzetoğlu | Yönlendirme (AODV) |
| Mert Dönmez | Güvenlik / PKI |
| Mehmet Fatih Akay | Android Geliştirme |
| Ahmet Taha Özcan | Test & Entegrasyon |

**Danışman:** Dr. Shadi Bikas — TOBB ETÜ

## Lisans

Bu proje açık kaynaklıdır. Ticari kapalı kaynak alternatiflerin (ör. Bridgefy) aksine, kod tabanı ve güvenlik yaklaşımı herkese açıktır.