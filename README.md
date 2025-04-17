
## 🚨 Lokari – Lock and Area Intelligent Response Interface. Integrasi Sistem IoT dan AI untuk Deteksi Anomali Aktivitas Ruangan Secara Otomatis dan Efisien dengan Respons Keadaan Darurat
**Kelompok UNI051 – MasiBelajar**

---

### Struktur Proyek

Go to path -> [Link to AI](https://github.com/ergegananputra/UNI051_SIC_MasiBelajar-AiIoT/tree/main/UNI051_SIC_MasiBelajar-AI)

Go to path -> [Link to IoT](https://github.com/ergegananputra/UNI051_SIC_MasiBelajar-AiIoT/tree/main/UNI051_SIC_MasiBelajar-IoT)

### Artificial Intelligence (AI)

Kode AI menggunakan **Streamlit** untuk visualisasi dan pengujian model deteksi.

```bash
python -m streamlit run app/streamlit/main.py
```

---

### IoT - Lokari Devices

Repositori ini berisi kode untuk perangkat IoT yang terhubung dengan sistem **Lokari**.

#### ESP32 Base (MicroPython)

Kode MicroPython untuk ESP32 biasa yang membaca suhu dan kelembaban dari sensor **DHT11**, mendeteksi gerakan dengan **PIR sensor**, serta mengirimkan data ke platform **Ubidots**.

##### Fitur Utama:
- Koneksi ke Wi-Fi
- Pembacaan data sensor (DHT11)
- Debounce logic untuk efisiensi pengiriman data
- Integrasi ke Ubidots IoT platform
- Kontrol LED berdasarkan deteksi gerakan

---

#### ESP32-CAM (Arduino C++)

Kode untuk **ESP32-CAM** menggunakan **ESP32 Camera Web Server**. Modul ini digunakan untuk live streaming video dari ruangan yang diawasi.

##### Fitur Utama:
- Inisialisasi dan konfigurasi kamera (AI Thinker ESP32-CAM)
- Koneksi ke jaringan Wi-Fi
- Menjalankan web server untuk streaming video secara real-time

---

## Platform & Tools

| Kategori          | Tools / Framework                     |
|-------------------|----------------------------------------|
| AI Backend        | FastAPI (Python)                      |
| AI Model          | Ultralytics YOLO                      |
| Web Camera Server | ESP32-CAM                             |
| Android App       | Android Studio                        |
| Coding IDE        | VS Code, Thonny                       |
| IoT Dashboard     | Ubidots                               |
| PCB Design        | KiCad                                 |

---

## Anggota Tim – UNI051: *MasiBelajar*

| Nama                      |
|---------------------------|
| Adiel Boanerge Gananputra |
| Langit Lintang Radjendra  |
| Marwah Kamila Ahmad       |
| Shyra Athaya              |

---