
## 🚨 Lokari – Lock and Area Intelligent Response Interface. Integrasi Sistem IoT dan AI untuk Deteksi Anomali Aktivitas Ruangan Secara Otomatis dan Efisien dengan Respons Keadaan Darurat
**Kelompok UNI051 – MasiBelajar**

---
## Anggota – UNI051: *MasiBelajar*

| Nama                      |
|---------------------------|
| Adiel Boanerge Gananputra |
| Langit Lintang Radjendra  |
| Marwah Kamila Ahmad       |
| Shyra Athaya              |
---

### Project Structure

Go to path -> [Link to AI](https://github.com/ergegananputra/UNI051_SIC_MasiBelajar-AiIoT/tree/main/UNI051_SIC_MasiBelajar-AI)

Go to path -> [Link to IoT](https://github.com/ergegananputra/UNI051_SIC_MasiBelajar-AiIoT/tree/main/UNI051_SIC_MasiBelajar-IoT)

### Artificial Intelligence (AI)

The AI code uses **Streamlit** for visualizing and testing the detection model.

```bash
python -m streamlit run app/streamlit/main.py
```

---

### IoT - Lokari Devices

### ESP32 BASE
MicroPython code that reads temperature and humidity from a DHT11 sensor, checks for changes in readings, and sends the data to Ubidots IoT platform over Wi-Fi. It also controls an LED connected to a PIR sensor.
#### Core Functionality
- Wi-Fi connectivity
- Sensor reading with debounce logic
- Data push to Ubidots
- LED hardware interface

### ESP32 CAM
Arduino (C++) code is for an ESP32-CAM board using the ESP32 Camera Web Server. This is a common setup for streaming camera footage over Wi-Fi using a web browser.
#### Core Functionality
- Initializes and configures a camera module (e.g., AI Thinker ESP32-CAM).
- Connects to Wi-Fi.
- Starts a web server to stream live video from the camera.


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

