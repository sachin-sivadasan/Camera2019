package com.schn.camera2019.rx

class Event {
    class AccelerationEvent
    class NewRecord {
        var filePath: String = ""
    }
    class AccelerationChangeEvent {
        var value: Float = 0.0F
    }
}